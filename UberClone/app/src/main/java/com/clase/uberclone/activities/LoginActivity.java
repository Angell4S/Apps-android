package com.clase.uberclone.activities;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clase.uberclone.R;
import com.clase.uberclone.activities.client.MapClientActivity;
import com.clase.uberclone.activities.driver.MapDriverActivity;
import com.clase.uberclone.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mTextInpuEmail;
    TextInputEditText mTextInpuPass;
    Button mButtonLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Para el toolbar*/
        MyToolbar.show(this,"Login de Usuario",true);

        mTextInpuEmail = findViewById(R.id.textInputEmail);
        mTextInpuPass = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Diferenciar tipo de usuario
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {

        String email = mTextInpuEmail.getText().toString();
        String password = mTextInpuPass.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()){
            if (password.length() >=6){
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     
                        if (task.isSuccessful()){
                            String user = mPref.getString("user","");
                            if (user.equals("client")){

                                Intent intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//es para borrar las actividades existentes
                                startActivity(intent);
                            }else{

                                Intent intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            
                        }else{
                            Toast.makeText(LoginActivity.this, "Error en login", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                        
                    }
                });

            }else{
                Toast.makeText(this, "La copntraseña debe de tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
        }

    }
}
