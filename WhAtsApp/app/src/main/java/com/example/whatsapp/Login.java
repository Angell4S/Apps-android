package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText u,p;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        u=(EditText)findViewById(R.id.edtUsu);
        p=(EditText)findViewById(R.id.edtContra);


        btn=(Button)findViewById(R.id.btnIngresar);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String usu=u.getText().toString();
                String pass=p.getText().toString();
                if (usu.equals("admin") && pass.equals("admin")){
                    Intent intent=new Intent(Login.this, menu.class);
                    Toast.makeText(Login.this,"Bienvenido "+usu,Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}
