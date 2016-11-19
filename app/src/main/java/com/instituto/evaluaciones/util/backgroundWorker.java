package com.instituto.evaluaciones.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.instituto.evaluaciones.MainActivity;
import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.dao.daoProfesor;
import com.instituto.evaluaciones.dao.daoUsuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Edu on 01/11/2016.
 */

public class backgroundWorker extends AsyncTask<String,Void,String> {

    Context contexto;
    ProgressDialog pd;
    AlertDialog alertDialog;
    beanUsuario bean = null;
    BeanProfesor beanProf = null;
    daoUsuario dao;
    daoProfesor daoProf;

    public backgroundWorker(Context contexto) {
        this.contexto = contexto;
        dao = new daoUsuario(contexto);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://institutoevaluaciones.pe.hu/login.php";
        if(type.equals("login")){
            try {
                String usuparam = params[1];
                String pwdparam = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("usuparam","UTF-8")+"="+URLEncoder.encode(usuparam,"UTF-8")+"&"
                        +URLEncoder.encode("pwdparam","UTF-8")+"="+URLEncoder.encode(pwdparam,"UTF-8")+"&";
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result = "";
                String Line = "";
                while ((Line = bufferedReader.readLine())!=null){
                    result += Line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(contexto);
        pd.setTitle("Bienvenido");
        pd.setCancelable(false);
        pd.setMessage("Iniciando Sesión");
        pd.setMax(20);
        pd.show();
    }

    @Override
    protected void onPostExecute(String result) {
        pd.dismiss();

        alertDialog = new AlertDialog.Builder(contexto).create();
        alertDialog.setTitle(R.string.strBienvenido);
        alertDialog.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_login_success,null);
        alertDialog.setView(v);

        final TextView txtUsuario = (TextView) v.findViewById(R.id.txtUser);
        final TextView txtPerfil = (TextView) v.findViewById(R.id.txtPerfil);
        final Button btnAceptar = (Button) v.findViewById(R.id.btnAceptarLogin);

        String[] resultarray = result.split(",");
        String resultado = resultarray[0].substring(1,resultarray[0].length());
        Log.i("--->res",resultado);
        if(resultado.equals("login not success")){
            txtUsuario.setText("Error:"+resultado);
            txtPerfil.setText("Corrija los datos y vuelva a ingresar");
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
        } else if(resultado.equals("login success")){
            String codigo   = resultarray[1];
            String codProf = resultarray[2];
            String nombre   = resultarray[3];
            String apellido = resultarray[4];
            String usuario  = resultarray[5];
            String contraseña = resultarray[6];
            String estado   = resultarray[7];
            String urlImagen = resultarray[8];
            String perfil   = resultarray[9];

            txtUsuario.setText(apellido+", "+nombre);
            txtPerfil.setText(perfil);

            bean = new beanUsuario();
            bean.setCodigo(Integer.parseInt(codigo));
            bean.setUser(usuario);
            bean.setPwd(contraseña);
            bean.setEstado(Integer.parseInt(estado));
            bean.setUrlImagen(urlImagen);
            bean.setPerfil(perfil);

            beanProf = new BeanProfesor();
            beanProf.setCodProfesor(codProf);
            beanProf.setNomProfesor(nombre);
            beanProf.setApeProfesor(apellido);
            beanProf.setDni(usuario);

            dao.insertUsuario(bean);
            daoProf.insertProfesor(beanProf);

            Log.i("--->bd","Se importó el usuario con éxito");

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Intent i = new Intent(contexto, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("obj", bean);
                    i.putExtra("objProf", beanProf);
                    contexto.startActivity(i);
                }
            });
        }

        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
