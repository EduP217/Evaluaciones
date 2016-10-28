package com.instituto.evaluaciones;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnIniciarCibertec;

    private void iniciarComponentes(){
        btnIniciarCibertec = (Button) findViewById(R.id.btnCib);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarComponentes();
    }

    @Override
    public void onClick(View view) {
        if(view == btnIniciarCibertec){

        }
    }
}
