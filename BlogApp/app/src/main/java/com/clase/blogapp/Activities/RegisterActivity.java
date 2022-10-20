package com.clase.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clase.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    ImageView userimg;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;
    TextView irlogin;

    private EditText rName,rEmail,rPass,rPass2;
    private ProgressBar loadingProgress;
    private Button btnRegistrar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rName=findViewById(R.id.edtname);
        rEmail=findViewById(R.id.edtmail);
        rPass=findViewById(R.id.edtpassword);
        rPass2=findViewById(R.id.edtpassconf);
        btnRegistrar=findViewById(R.id.regBtn);
        loadingProgress=findViewById(R.id.regprogressBar);
        loadingProgress.setVisibility(View.INVISIBLE);

        mAuth=FirebaseAuth.getInstance();
        
        irlogin=findViewById(R.id.irlogin);
        irlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegistrar.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String name=rName.getText().toString();
                final String email=rEmail.getText().toString();
                final String pass=rPass.getText().toString();
                final String pass2=rPass2.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || pass2.isEmpty() || !pass.equals(pass2)){

                    // algo sale mal: todos los campos deben fallar
                    // necesitamos mostrar un mensaje de error

                    showMessage("please Verify all fields");
                    btnRegistrar.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);


                }else{

                    // todo está bien y todos los campos han fallado ahora podemos comenzar a crear una cuenta de usuario
                    // CreateUserAccount  intentará crear el usuario si el correo electrónico es válido


                    CreateUserAccount(name,email,pass);

                }


            }
        });



        userimg=findViewById(R.id.reguserimg);
        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23){

                    checkAndRequestForPermission();
                }else{
                    openGallery();
                }


            }
        });

    }

    private void CreateUserAccount(final String name, String email, String pass) {

        // este método crea una cuenta de usuario con correo electrónico y contraseña específicos

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    // cuenta de usuario creada con éxito
                    showMessage("Acount created");
                    // después de crear una cuenta de usuario, necesitamos actualizar su imagen y nombre
                    updateUserInfo(name,pickedImgUri,mAuth.getCurrentUser());


                }else{
                    showMessage("account creation failed"+task.getException().getMessage());
                    btnRegistrar.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    //update user photo and name
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
        //first we need to upload user photo to firebase storage and ger url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                // imagen cargada correctamente
                // ahora podemos obtener nuestra url de imagen

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //user info update succefully
                                    showMessage("Register Complete");
                                    updateUI();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void updateUI() {
        Intent homeActi=new Intent(getApplicationContext(),Home.class);
        startActivity(homeActi);
        finish();
        
    }


    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }


    private void openGallery() {
        //Todo:open galley intent and wait for user top ick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);

    }



    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
                
            }else{
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }

        }else
            openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){


            // el usuario ha elegido con éxito una imagen
            // necesitamos guardar su referencia a una variable Uri


            pickedImgUri = data.getData();
            userimg.setImageURI(pickedImgUri);

        }

    }
}
