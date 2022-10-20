package com.clase.sitourist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContentActivity extends AppCompatActivity {

    public static final String NOMBREUSU = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Button btnEnviar = findViewById(R.id.enviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Has Seleccionado Enviar", Toast.LENGTH_LONG).show();

                Intent intentEnviar = new Intent(ContentActivity.this,DestiActivity.class);
                EditText nombreUsuario = findViewById(R.id.nombre);
                String nombreUsuarioMens = nombreUsuario.getText().toString();
                intentEnviar.putExtra(NOMBREUSU, nombreUsuarioMens);
                startActivity(intentEnviar);
            }
        });
    }

    /*public void enviarNombre(View view){
        Intent intentEnviar = new Intent(this, DestiActivity.class);
        EditText nombreUsuario = findViewById(R.id.nombre);
        String nombreUsuarioMens = nombreUsuario.getText().toString();
        intentEnviar.putExtra(NOMBREUSU, nombreUsuarioMens);
    }*/
}
