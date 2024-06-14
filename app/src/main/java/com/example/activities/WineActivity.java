package com.example.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.CartItem;
import com.example.listeners.OnItemClickListener;
import com.example.new1.R;
import com.example.models.Wine;
import com.example.adapters.WineAdapter;
import com.example.models.Wines;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WineActivity extends AppCompatActivity {


    public static final String TYPE_ARG = "wineType";
    private RecyclerView rvWines;
    private TextView typeTv, counterTV;
    private ImageView typeIv;
    List<Wine> wines = Wines.white;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        counterTV = findViewById(R.id.counter);
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("orders")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    counterTV.setText(String.valueOf(queryDocumentSnapshots.size()));
                });
        rvWines = findViewById(R.id.rvWines);
        String type = getIntent().getStringExtra(TYPE_ARG);
        counterTV = findViewById(R.id.counter);
        typeTv = findViewById(R.id.wineTypeName);
        typeIv = findViewById(R.id.wineTypeImage);
        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
        if(type == null)
            type = "red";

        typeTv.setText("יין לבן");
        typeIv.setImageResource(R.drawable.homepage_white);
        switch(type) {
            case "red":
                wines = Wines.red;
                typeTv.setText("יין אדום");
                typeIv.setImageResource(R.drawable.homepage_red);
                break;
            case "rose":
                wines = Wines.rose;
                typeTv.setText("יין רוזה");
                typeIv.setImageResource(R.drawable.homepage_rose);
                break;
            case "sparkling":
                wines = Wines.sparkling;
                typeTv.setText("יין מבעבע");
                typeIv.setImageResource(R.drawable.homepage_sparkling);
                break;
        }

        WineAdapter adapter = new WineAdapter(wines, new OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                onWineClicked(position);
            }
        });
        rvWines.setLayoutManager(new LinearLayoutManager(this));
        rvWines.setAdapter(adapter);

    }

    private void onWineClicked(int position) {
        int counter = Integer.parseInt(counterTV.getText().toString());
        counterTV.setText(String.valueOf(++counter));
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("orders")
                .document(wines.get(position).getName())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snap) {
                        if(!snap.exists()) {
                            FirebaseFirestore.getInstance()
                                    .collection("orders")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("orders")
                                    .document(wines.get(position).getName())
                                    .set(new CartItem(wines.get(position), 1));
                        }else {
                            CartItem item = snap.toObject(CartItem.class);
                            if(item !=null)
                                snap.getReference().update("quantity",item.getQuantity() + 1 );
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseFirestore.getInstance()
                                .collection("orders")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("orders")
                                .document(wines.get(position).getName())
                                .set(new CartItem(wines.get(position), 1));
                    }
                });
    }
}
