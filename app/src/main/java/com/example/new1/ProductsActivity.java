package com.example.new1;

import static com.example.new1.WineActivity.TYPE_ARG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ProductsActivity extends AppCompatActivity {


    LinearLayout redWineBtn,whiteWineBtn,roseWineBtn,bubbleWineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        redWineBtn=  findViewById(R.id.redWineBtn);
        whiteWineBtn=  findViewById(R.id.whiteWineBtn);
        roseWineBtn=  findViewById(R.id.roseWineBtn);
        bubbleWineBtn=  findViewById(R.id.bubbleWineBtn);


        redWineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, WineActivity.class);
                intent.putExtra(TYPE_ARG, "red");
                startActivity(intent);

            }
        });

        findViewById(R.id.goToCart)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(
                                new Intent(ProductsActivity.this, CartActivity.class)
                        );
                    }
                });
        whiteWineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, WineActivity.class);
                intent.putExtra(TYPE_ARG, "white");
                startActivity(intent);
            }
        });
        roseWineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, WineActivity.class);
                intent.putExtra(TYPE_ARG, "rose");
                startActivity(intent);

            }
        });
        bubbleWineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, WineActivity.class);
                intent.putExtra(TYPE_ARG, "sparkling");
                startActivity(intent);
            }
        });


    }
}