package com.instituto.evaluaciones.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instituto.evaluaciones.MainActivity;
import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.dao.daoUsuario;
import com.instituto.evaluaciones.dialogos.LoginDialog;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edu on 01/11/2016.
 */

public class backgroundWorker extends AsyncTask<String,Void,String> {

    Context contexto;
    AlertDialog alertDialog;
    View v;
    beanUsuario bean = null;
    daoUsuario dao;

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
        alertDialog = new AlertDialog.Builder(contexto).create();
        alertDialog.setTitle(R.string.strBienvenido);
        alertDialog.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.dialog_login_success,null);
        alertDialog.setView(v);
    }

    @Override
    protected void onPostExecute(String result) {
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
            String nombre   = resultarray[2];
            String apellido = resultarray[3];
            String usuario  = resultarray[4];
            String contraseña = resultarray[5];
            String estado   = resultarray[6];
            String urlImagen = resultarray[7];
            String perfil   = resultarray[8];

            txtUsuario.setText(apellido+", "+nombre);
            txtPerfil.setText(perfil);

            bean = new beanUsuario();
            bean.setCodigo(Integer.parseInt(codigo));
            bean.setNombre(nombre);
            bean.setApellido(apellido);
            bean.setUser(usuario);
            bean.setPwd(contraseña);
            bean.setEstado(Integer.parseInt(estado));
            bean.setUrlImagen(urlImagen);
            bean.setPerfil(perfil);

            dao.insertUsuario(bean);
            Log.i("--->bd","Se importó el usuario con éxito");
            /*String url = "http://institutoevaluaciones.pe.hu/Images/avatar-user.png";
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(),new Rect(0,0,0,0),options);
                avatar.setImageBitmap(imagen);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("-->img",url);*/
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Intent i = new Intent(contexto, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("obj", bean);
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
