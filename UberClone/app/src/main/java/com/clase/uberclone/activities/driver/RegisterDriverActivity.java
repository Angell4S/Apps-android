package com.clase.uberclone.activities.driver;

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
import com.clase.uberclone.models.Driver;
import com.clase.uberclone.providers.AuthProvider;
import com.clase.uberclone.providers.DriverProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {


    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;

    Button mButtonRegister;
    TextInputEditText mInputEmail,mInputPass,mInputName,mInputVehicleBrand,mInputVehiclePlate;



    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.show(this,"Registro de conductor",true);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();



        mDialog = new SpotsDialog.Builder().setContext(RegisterDriverActivity.this).setMessage("Espere un momento").build();

        mInputEmail=findViewById(R.id.textInputEmailRD);
        mInputPass=findViewById(R.id.textInputpasswordRD);
        mInputName=findViewById(R.id.textInputNameRD);
        mInputVehicleBrand = findViewById(R.id.textInputVehicleBrand);
        mInputVehiclePlate = findViewById(R.id.textInputVehiclePlate);

        mButtonRegister=findViewById(R.id.btnRegisterD);

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
        final String vehicleBrand = mInputVehicleBrand.getText().toString();
        final String vehiclePlate = mInputVehiclePlate.getText().toString();
        String password = mInputPass.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !vehicleBrand.isEmpty() && !vehiclePlate.isEmpty()){
            if (password.length() >=6){
                mDialog.show();

                register(name,email,password, vehicleBrand, vehiclePlate);

            }else{
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }


    }

    private void register(final String name , final String email, String password, final String vehicleBrand , final String vehiclePlate) {
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()){
                    //obtener el id del usuario que se acaba de registrar en firebaseauth
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id,name ,email,vehicleBrand,vehiclePlate);
                    create(driver);


                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    // Toast.makeText(RegisterDriverActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this,MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
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
