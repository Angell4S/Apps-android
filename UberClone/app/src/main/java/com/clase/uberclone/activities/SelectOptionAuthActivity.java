package com.clase.uberclone.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clase.uberclone.R;
import com.clase.uberclone.activities.client.RegisterActivity;
import com.clase.uberclone.activities.driver.RegisterDriverActivity;


public class SelectOptionAuthActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGotoLogin,mButtonGotoRegister;

    SharedPreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        /*Para el toolbar*/
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar opcion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Diferenciar tipo de usuario
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);

        mButtonGotoLogin = findViewById(R.id.btnGoToLogin);
        mButtonGotoRegister = findViewById(R.id.btnGoToRegister);

        mButtonGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
        mButtonGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });







    }

    private void gotoRegister() {
        String typeUser = mPref.getString("user" , "");
        if (typeUser.equals("client")){
            Intent i = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(SelectOptionAuthActivity.this, RegisterDriverActivity.class);
            startActivity(i);
        }
    }



    private void gotoLogin() {

        Intent intent = new Intent(SelectOptionAuthActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}
