package com.instituto.evaluaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.dialogos.LoginDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private bdconexion db;
    private beanUsuario usuario;

    private void iniciarComponentes(){
        db = new bdconexion(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();

        //RECUPERAR EL INTENT "IR"
        Intent re = getIntent();
        if (re.getSerializableExtra("obj") != null) {
            usuario = (beanUsuario) re.getSerializableExtra("obj");
        } else {
            goLoginScreen();
        }

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
