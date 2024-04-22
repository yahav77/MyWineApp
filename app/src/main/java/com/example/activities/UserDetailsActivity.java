package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new1.R;
import com.example.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvUserDetailsEmail,tvUserDetailsName,tvUserDetailsAge, tvUserDetailsPhone;
    Button btnUserDetails;
    FirebaseAuth fbAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        tvUserDetailsEmail=findViewById(R.id.tvUserDetailsEmail);
        tvUserDetailsName=findViewById(R.id.tvUserDetailsName);
        tvUserDetailsAge=findViewById(R.id.tvUserDetailsAge);
        tvUserDetailsPhone=findViewById(R.id.tvUserDetailsPhone);
        btnUserDetails=findViewById(R.id.btnUserDetails);
        btnUserDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnUserDetails){
            fbAuth=FirebaseAuth.getInstance();
            FirebaseFirestore store=FirebaseFirestore.getInstance();
            String uid= fbAuth.getUid();
            store.collection("users").document(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user=documentSnapshot.toObject(User.class);
                            tvUserDetailsEmail.setText(user.getEmail());
                            tvUserDetailsName.setText(user.getName());
                            tvUserDetailsAge.setText(String.valueOf(user.getAge()));
                            tvUserDetailsPhone.setText(user.getPhone());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserDetailsActivity.this,
                                    "Could not get user data",Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
}