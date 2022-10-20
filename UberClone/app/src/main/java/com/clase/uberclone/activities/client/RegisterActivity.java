package com.clase.uberclone.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clase.uberclone.R;
import com.clase.uberclone.includes.MyToolbar;
import com.clase.uberclone.models.Client;
import com.clase.uberclone.providers.AuthProvider;
import com.clase.uberclone.providers.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {


    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    Button mButtonRegister;
    TextInputEditText mInputEmail,mInputPass,mInputName;



    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show(this,"Registro de usuario",true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();



        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();

        mInputEmail=findViewById(R.id.textInputEmailR);
        mInputPass=findViewById(R.id.textInputpasswordR);
        mInputName=findViewById(R.id.textInputNameR);
        mButtonRegister=findViewById(R.id.btnRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });


    }

    private void clickRegister() {
        final String name = mInputName.getText().toString();
        final String email = mInputEmail.getText().toString();
        String password = mInputPass.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if (password.length() >=6){
                mDialog.show();

                register(name,email,password);

            }else{
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }


    }

    private void register(final String name , final String email, String password) {
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()){
                    //obtener el id del usuario que se acaba de registrar en firebaseauth
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id,name ,email);
                    create(client);


                }else{
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }

/*
    //crear usuario segun la preferencia que eligio
    private void saveUser(String id,String name,String email) {

        String selectedUser = mPref.getString("user","");

        User user = new User();
        user.setEmail(email);
        user.setName(name);

        if (selectedUser.equals("driver")){

            //mDatabse referencia al nodo principal (uberclone-802ef que esta en la base de datos realtime) y creamos subcarpetas dentro de el
            //dentro de drivers o clients nos cree un identificador unico para cada usuario creado(push())
            //pero como push nos genera un id aleatorio, lo quitaremos para que este con el id con que registramos en firebase
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }else if (selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }



    }*/
}
