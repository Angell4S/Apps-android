package com.clase.uberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clase.uberclone.R;
import com.clase.uberclone.activities.client.MapClientActivity;
import com.clase.uberclone.activities.driver.MapDriverActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button mbtnConduc,mbtnCliente;

    //Diferenciar tipo de usuario
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Diferenciar tipo de usuario
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        mbtnConduc = findViewById(R.id.btnConductor);
        mbtnCliente = findViewById(R.id.btnCliente);

        mbtnConduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Diferenciar tipo de usuario
                editor.putString("user","driver");
                editor.apply();


                gotoSelectAuth();

            }
        });


        mbtnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Diferenciar tipo de usuario
                editor.putString("user","client");
                editor.apply();

                gotoSelectAuth();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            String user = mPref.getString("user" ,"");

            if (user.equals("client")){

                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//es para borrar las actividades existentes
                startActivity(intent);
            }else{

                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

    }

    private void gotoSelectAuth() {
        Intent intent = new Intent(MainActivity.this,SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}
