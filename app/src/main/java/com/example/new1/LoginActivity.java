package com.example.new1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    FirebaseAuth fbAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail =findViewById(R.id.etLoginEmail);
        etLoginPassword =findViewById(R.id.etLoginPassword);
        btnLogin =findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        fbAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v== btnLogin) {
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();
            if (isEmailOK(email) && isPasswordOK(password)) {
                fbAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this,
                                            ProductsActivity.class);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(LoginActivity.this,
                                            "Email or Password incorrect",
                                            Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }
    private boolean isEmailOK(String email) {
        if (TextUtils.isEmpty(email))
            return false;
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;

        return true;
    }
    private boolean isPasswordOK(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        else if (password.length() < 6)
            return false;

        return true;
    }
}