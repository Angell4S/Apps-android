package com.example.whatsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class chatMessage extends AppCompatActivity {

    TextView nomtv;
    TextView apetv;
    ImageView imgfoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);


        nomtv = findViewById(R.id.nom_mess);
        apetv = findViewById(R.id.ape_mess);
        imgfoto = findViewById(R.id.img_chat);

        Intent intent = getIntent();

        if (intent.getExtras() != null){
            ConstChat usermodelnom = (ConstChat) intent.getSerializableExtra("nombre");
            ConstChat usermodelape = (ConstChat) intent.getSerializableExtra("apellidos");
            ConstChat usermodelimg = (ConstChat) intent.getSerializableExtra("foto");

            nomtv.setText(usermodelnom.getNom());
            apetv.setText(usermodelape.getApe());
            imgfoto.setImageResource(usermodelimg.getFoto());

        }

        /*ActionBar actionBar = getSupportActionBar();


        actionBar.setElevation(0);*/
    }
}
