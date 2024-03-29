package com.example.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnRemove {


    @Override
    public void onRemoveOne(CartItem item) {
       for(CartItem anItem : cartItems) {
           if(anItem.getItem().getName().equals(item.getItem().getName())) {
               anItem.setQuantity(anItem.getQuantity()-1);
           }
       }
       adapter.update(cartItems);
    }

    @Override
    public void onRemoveAll(CartItem item) {
        int index = cartItems.indexOf(item);
        cartItems.remove(index);
        adapter.update(cartItems);

    }



    class CheckoutDialog extends AlertDialog {

        protected CheckoutDialog(@NonNull Context context) {
            super(context);

            View checkoutView = LayoutInflater.from(context).inflate(R.layout.checkout_dialog,null,false);


            TextView totalAmount = checkoutView.findViewById(R.id.totalCheckoutTv);

            double total = 0;
            for(CartItem item : cartItems) {
                total += (item.getItem().getPrice() * item.getQuantity());
            }
            totalAmount.setText("Total : " + Math.round(total));


            EditText cardNumberEt = checkoutView.findViewById(R.id.cardNumberTV);
            EditText expirationEt = checkoutView.findViewById(R.id.expirationDateTv);
            EditText ccvEt = checkoutView.findViewById(R.id.ccvTv);
            EditText addressEt = checkoutView.findViewById(R.id.addressTv);
            final double finalTotal = total;

            setButton(BUTTON_POSITIVE, "Checkout", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String cardNumber = cardNumberEt.getText().toString();
                    String expiration = expirationEt.getText().toString();
                    String ccv = ccvEt.getText().toString();
                    String address = addressEt.getText().toString();

                    if(cardNumber.length() < 16) {
                        Toast.makeText(context, "Please enter 16 digit card number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(ccv.length() != 3) {
                        Toast.makeText(context, "Please enter 3 digit ccv", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String[] components = expiration.split("/");
                    if(components.length != 2) {
                        Toast.makeText(context, "Please enter expiration date in format MM/YYYY", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(components[0].isEmpty() || components[0].length() > 2) {
                        Toast.makeText(context, "Please enter valid month", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(components[1].isEmpty() || components[1].length() > 4) {
                        Toast.makeText(context, "Please enter valid year", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(address.length() < 2) {
                        Toast.makeText(context, "Please enter valid address", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Order order = new Order(
                            (int)finalTotal, cartItems,  cardNumber.substring(cardNumber.length()-4), expiration,  ccv, address
                    );

                    FirebaseFirestore.getInstance()
                            .collection("orders")
                            .document(FirebaseAuth.getInstance().getUid())
                            .delete();
                    FirebaseFirestore.getInstance()
                            .collection("checkouts")
                            .document(FirebaseAuth.getInstance().getUid())
                            .collection("checkouts")
                            .add(order)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(context, "Checkout successfull", Toast.LENGTH_LONG).show();
                                    dismiss();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Please try again later", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
            setButton(BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            setView(checkoutView);
        }
    }

    private List<CartItem> cartItems;
    private CartAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        rv = findViewById(R.id.rvWines);
        Button checkoutBtn = findViewById(R.id.checkoutBtn);

        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("orders")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<CartItem> cart = new ArrayList<>();
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            cart.add(doc.toObject(CartItem.class));
                        }
                        CartActivity.this.cartItems = cart;
                        adapter = new CartAdapter(cart, CartActivity.this);
                        rv.setAdapter(adapter);
                        checkoutBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new CheckoutDialog(CartActivity.this).show();
                            }
                        });
                    }
                });

    }
}
