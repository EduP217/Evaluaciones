package com.instituto.evaluaciones.dialogos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.instituto.evaluaciones.LoginActivity;
import com.instituto.evaluaciones.MainActivity;
import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;

import java.util.List;
import java.util.Objects;

/**
 * Created by eprieto on 31/10/2016.
 */
public class LoginDialog extends DialogFragment {

    private static final String TAG = LoginDialog.class.getSimpleName();

    public LoginDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    private AlertDialog createLoginDialogo() {

        final bdconexion db = new bdconexion(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.login_user,null);
        builder.setView(v);

        Button signup = (Button) v.findViewById(R.id.btnCrearCuenta);
        Button signin = (Button) v.findViewById(R.id.btnIngresar);

        final EditText edtUser = (EditText) v.findViewById(R.id.edtUser);
        final EditText edtPass = (EditText) v.findViewById(R.id.edtPwd);

        //CREAR CUENTA
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //LOGUEAR
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                beanUsuario bean = db.buscarUsuario(user,pass);
                Log.i("LoginDialog->metodo",bean.getNombre()+" "+bean.getApellido().toString());

                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("BeanNombre",bean.getNombre());
                startActivity(i);

            }
        });

        return builder.create();

    }

}