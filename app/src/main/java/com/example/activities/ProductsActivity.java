package com.example.activities;

import static com.example.activities.WineActivity.TYPE_ARG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    LinearLayout redWineBtn,whiteWineBtn,roseWineBtn,bubbleWineBtn;
    DrawerLayout drawerLayout;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayoutCompat header = (LinearLayoutCompat) navigationView.getHeaderView(0);
        ImageView imageView = header.findViewById(R.id.image);
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.email);
        TextView age = header.findViewById(R.id.age);
        TextView phone = header.findViewById(R.id.phone);
        FirebaseFirestore.getInstance()
                        .collection("users")
                                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        Glide.with(ProductsActivity.this)
                                .load(user.getImageUrl())
                                .circleCrop()
                                .into(imageView);
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        age.setText(String.valueOf(user.getAge()));
                        phone.setText(user.getPhone());
                    }
                });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.sign_out) {
            drawerLayout.closeDrawer(GravityCompat.START);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return true;

    }
}