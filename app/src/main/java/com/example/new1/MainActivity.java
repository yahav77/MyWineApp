package com.example.new1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnMainLogin,btnMainRegister,btnMainUserDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainLogin = findViewById(R.id.btnMainLogin);
        btnMainRegister = findViewById(R.id.btnMainRegister);

        btnMainLogin.setOnClickListener(this);
        btnMainRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == btnMainLogin) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else if(v==btnMainRegister){
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
    }

}