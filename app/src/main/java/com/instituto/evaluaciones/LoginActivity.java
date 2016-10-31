package com.instituto.evaluaciones;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.instituto.evaluaciones.dialogos.LoginDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnIniciarCibertec;

    private void iniciarComponentes(){
        btnIniciarCibertec = (Button) findViewById(R.id.btnCib);
        btnIniciarCibertec.setOnClickListener(this);
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
            FragmentManager fragmentManager = getFragmentManager();
            new LoginDialog().show(fragmentManager, "LoginDialog");
        }
    }
}
