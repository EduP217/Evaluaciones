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
    TextView txtUsuario;

    private void iniciarComponentes(){
        db = new bdconexion(this);
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);

    }

    /*private void poblarUsuario(){
        beanUsuario obj = new beanUsuario();
        obj.setNombre("Eduardo");
        obj.setApellido("Prieto");
        obj.setUser("i201111156");
        obj.setPwd("123456");
        obj.setEstado(1);
        db.insertUsuario(obj);
        //->
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();
        //poblarUsuario();

        //LISTA EN EL LOG TODOS LOS USUARIOS REGISTRADOS EN LA BD
        /*List<beanUsuario> list = db.loadUsuario();
        for(beanUsuario bean:list){
            Log.i("----> Base de datos: ",bean.getNombre()+" "+bean.getApellido());
        }*/

        //RECUPERAR EL INTENT "IR"
        Intent re = getIntent();
        if (re.getSerializableExtra("obj") != null) {
            beanUsuario usuario = (beanUsuario) re.getSerializableExtra("obj");
            txtUsuario.setText(usuario.getApellido()+", "+usuario.getNombre());
        } else {
            goLoginScreen();
        }

        /*if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                goLoginScreen();
            } else {
                nombre= extras.getString("BeanNombre");
                Log.i("Main->extras",nombre);
                txtUsuario.setText(nombre);
            }
        } else {
            //nombre= (String) savedInstanceState.getSerializable("BeanNombre");
            //Log.i("Main->Instance",nombre);
        }*/

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
