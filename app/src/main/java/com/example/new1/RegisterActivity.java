package com.example.new1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etRegisterEmail,etRegisterPassword,etRegisterName,etRegisterAge,etRegisterPhone;
    Button btnRegister, btnCamera, btnGallery;

    ImageView profileImg;
    Bitmap imageBitmap;

    String imageUrl;
    FirebaseAuth fbAuth;

    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if (result.getResultCode() == Activity.RESULT_OK){
                    imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                    profileImg.setImageBitmap(imageBitmap);
                }
            });

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if (result.getResultCode() == Activity.RESULT_OK){
                    Uri uri = result.getData().getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {}
                    profileImg.setImageBitmap(imageBitmap);
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etRegisterEmail=findViewById(R.id.etRegisterEmail);
        etRegisterPassword=findViewById(R.id.etRegisterPassword);
        etRegisterName=findViewById(R.id.etRegisterName);
        etRegisterAge=findViewById(R.id.etRegisterAge);
        etRegisterPhone=findViewById(R.id.etRegisterPhone);
        btnRegister=findViewById(R.id.btnRegister);
        btnCamera=findViewById(R.id.camera);
        btnCamera.setOnClickListener(this);
        btnGallery=findViewById(R.id.gallery);
        btnGallery.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        profileImg = findViewById(R.id.profileImg);
        fbAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if(view==btnRegister){
            String email=etRegisterEmail.getText().toString();
            String password=etRegisterPassword.getText().toString();
            String name=etRegisterName.getText().toString();
            String age=etRegisterAge.getText().toString();
            String phone=etRegisterPhone.getText().toString();
            if(!isAgeOK(age)) {
                Snackbar.make(view, "You must be 18 to enter our website.", Snackbar.LENGTH_LONG).show();
                return;
            }
            if(isEmailOK(email) && isPasswordOK(password) && isNameOK(name)  && isPhoneOK(phone)){
                fbAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    saveUserToDataBase(email, name, Integer.parseInt(age), phone);
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Failed to Register",
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
            else{
                Toast.makeText(RegisterActivity.this,"One or more detail are incorrect",
                        Toast.LENGTH_LONG).show();
            }
        } else if (view == btnCamera){
            openCamera();
        }else if (view == btnGallery) {
            openGallery();
        }
    }

    private void saveUserToDataBase(String email, String name, int age, String phone) {
        StorageReference storage = FirebaseStorage.getInstance().getReference().child("images/" + fbAuth.getUid()+".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        storage.putBytes(array).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            saveUser(email, name, age, phone);
                        }
                    });
                } else {

                }
            }
        });
    }

    private void saveUser(String email, String name, int age, String phone) {
        FirebaseFirestore store=FirebaseFirestore.getInstance();
        User user=new User(fbAuth.getUid(), email,imageUrl, name,age,phone);

        store.collection("users").document(fbAuth.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent=new Intent(RegisterActivity.this,
                                ProductsActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed to Register",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
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
    private boolean isNameOK(String name) {
        if (TextUtils.isEmpty(name))
            return false;
        else if (name.length() < 2)
            return false;
        else if(!name.chars().allMatch(Character::isLetter))
            return false;

        return true;
    }
    private boolean isAgeOK(String age) {
        int ageNum=Integer.parseInt(age);
        if (TextUtils.isEmpty(age))
            return false;
        else if (ageNum < 18)
            return false;

        return true;
    }
    private boolean isPhoneOK(String phone) {
        if (TextUtils.isEmpty(phone))
            return false;
        else if (!phone.startsWith("05"))//058-
            return false;

        return true;
    }
}