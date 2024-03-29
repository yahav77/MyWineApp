package com.example.new1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


   public  interface OnRemove {
        void onRemoveOne(CartItem item);
        void onRemoveAll(CartItem item);

    }
    private  List<CartItem> cartItems;
    private final OnRemove onRemove;
    public CartAdapter(List<CartItem> cartItems,OnRemove onRemove) {
        this.cartItems = cartItems;
        this.onRemove = onRemove;
    }

    public void update(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.wine_item_cart, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.wineNameTv.setText(cartItem.getItem().getName());
        holder.winePriceTv.setText(cartItem.getItem().getPrice() + "₪");
        holder.wineMlTv.setText(cartItem.getItem().getMl() + " " + "מל");
        holder.quantityTv.setText(cartItem.getQuantity() + "");
        Picasso.get().load(cartItem.getItem().getImage()).into(holder.wineIv);
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance()
                        .collection("orders")
                        .document(FirebaseAuth.getInstance().getUid())
                        .collection("orders")
                        .document(cartItem.getItem().getName())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot snap) {
                                if(snap.exists())  {
                                    CartItem item = snap.toObject(CartItem.class);
                                    if(item !=null) {
                                        int newQuantity = item.getQuantity() - 1;
                                        if(newQuantity <= 0) {
                                            snap.getReference().delete();
                                            onRemove.onRemoveAll(cartItem);
                                            return;
                                        }
                                        onRemove.onRemoveOne(cartItem);
                                        snap.getReference().update("quantity",newQuantity);
                                    }
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wineNameTv, winePriceTv,wineMlTv;
        private ImageView wineIv, removeBtn;

        private TextView quantityTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wineIv = itemView.findViewById(R.id.wineImageView);
            this.wineNameTv = itemView.findViewById(R.id.wineNameTv);
            this.winePriceTv = itemView.findViewById(R.id.winePriceTv);
            this.wineMlTv = itemView.findViewById(R.id.wineMlTv);
            this.removeBtn = itemView.findViewById(R.id.removeWineBtn);
            this.quantityTv = itemView.findViewById(R.id.wineQuantityTv);
        }
    }
}
