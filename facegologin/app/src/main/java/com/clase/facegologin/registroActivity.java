package com.clase.facegologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registroActivity extends AppCompatActivity {

    EditText rName, rEmail, rPassword;
    Button rRegister;
    FirebaseAuth rAuth;
    FirebaseFirestore rStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        rName = (EditText) findViewById(R.id.idnombrer);
        rEmail = (EditText) findViewById(R.id.idcorreor);
        rPassword = (EditText) findViewById(R.id.idcontrar);
        rRegister = (Button)findViewById(R.id.idregistrar);
        rAuth = FirebaseAuth.getInstance();
        rStore = FirebaseFirestore.getInstance();

        rRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = rName.getText().toString();
                final String email = rEmail.getText().toString().trim();
                final String password = rPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Ingrese correo electrónico");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    rPassword.setError("Ingrese contraseña");
                    return;
                }

                if(password.length()<6){
                    rPassword.setError("Minimo 6 dígitos");
                    return;
                }

                rAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(registroActivity.this, "Cuenta creada con exito", Toast.LENGTH_LONG).show();
                            //CREANDO BASE DE DATOS
                            userID = rAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = rStore.collection("Usuarios").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Email", email);
                            user.put("Contraseña", password);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "Exitoso"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "Error"+e.toString());
                                }
                            });

                            startActivity(new Intent(registroActivity.this, logeActivity.class));
                        }else{
                            Toast.makeText(registroActivity.this, "No se pudo crear la cuenta" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    public void SendToLogin(View view) {
        startActivity(new Intent(registroActivity.this, logeActivity.class));
    }
}
