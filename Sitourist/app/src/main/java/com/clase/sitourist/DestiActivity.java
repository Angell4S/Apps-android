package com.clase.sitourist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DestiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desti);

        Intent intentFinal = getIntent();
        String nombreusuFinal = intentFinal.getStringExtra(ContentActivity.NOMBREUSU);

        TextView mensajeFinal = findViewById(R.id.messFinal);
        mensajeFinal.setText(nombreusuFinal);

    }
}
