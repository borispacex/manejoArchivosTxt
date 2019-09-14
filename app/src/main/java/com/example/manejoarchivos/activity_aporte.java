package com.example.manejoarchivos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_aporte extends AppCompatActivity {

    private static String dato;
    private TextView tdato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aporte);

        tdato = (TextView) findViewById(R.id.dato);

        Bundle b = getIntent().getExtras();
        dato = b.getString("dato");
        tdato.setText(dato);
    }

    public void retornar(View view) {
        finish();
    }
}
