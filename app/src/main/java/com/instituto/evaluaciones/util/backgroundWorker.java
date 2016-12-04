package com.instituto.evaluaciones.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.instituto.evaluaciones.MainActivity;
import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.dao.daoProfesor;
import com.instituto.evaluaciones.dao.daoUsuario;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
    Bitmap imagex;

    public backgroundWorker(Context contexto) {
        this.contexto = contexto;
        dao = new daoUsuario(contexto);
        daoProf = new daoProfesor(contexto);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String usuparam = params[1];
        String pwdparam = params[2];
        //String login_url = "http://institutoevaluaciones.pe.hu/login.php";
        String resultado;
        String result = "";
        if(type.equals("login")){
            try {
                HttpClient cliente = new DefaultHttpClient();
                HttpContext contexto = new BasicHttpContext();
                String login_url = "http://institutoevaluaciones.pe.hu/login.php?usuparam="+usuparam+"&pwdparam="+pwdparam;
                HttpGet getUsuario = new HttpGet(login_url);
                HttpResponse response = cliente.execute(getUsuario,contexto);
                HttpEntity entity = response.getEntity();
                resultado = EntityUtils.toString(entity,"UTF-8");

                JSONArray arregloLogin = new JSONArray(resultado);

                for(int i=0;i<arregloLogin.length();i++) {
                    i++;
                    result = arregloLogin.getString(0);
                    Log.i("-->condi",result);

                    if(result.equals("login success")) {
                        bean = new beanUsuario();
                        bean.setCodigo(arregloLogin.getJSONObject(i).getInt("codigo"));
                        bean.setUser(arregloLogin.getJSONObject(i).getString("usuario"));
                        bean.setPwd(arregloLogin.getJSONObject(i).getString("clave"));
                        bean.setEstado(arregloLogin.getJSONObject(i).getInt("estado"));
                        bean.setUrlImagen(arregloLogin.getJSONObject(i).getString("imagen"));
                        bean.setPerfil(arregloLogin.getJSONObject(i).getString("perfil"));

                        beanProf = new BeanProfesor();
                        beanProf.setCodProfesor(arregloLogin.getJSONObject(i).getString("codProfesor"));
                        beanProf.setNomProfesor(arregloLogin.getJSONObject(i).getString("nombre"));
                        beanProf.setApeProfesor(arregloLogin.getJSONObject(i).getString("apellido"));
                        beanProf.setDni(arregloLogin.getJSONObject(i).getString("usuario"));

                        dao.insertUsuario(bean);
                        daoProf.insertProfesor(beanProf);
                        dao.insertSetting(beanProf.getCodProfesor());
                    } else {

                    }
                }

                String urlImg = "http://institutoevaluaciones.pe.hu/Images/"+bean.getUrlImagen();
                URL imageUrl = new URL(urlImg);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagex = BitmapFactory.decodeStream(conn.getInputStream());

                /*URL url = new URL(login_url);
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

                String Line = "";
                while ((Line = bufferedReader.readLine())!=null){
                    result += Line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();*/

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
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
        final ImageView imglogin = (ImageView) v.findViewById(R.id.img);
        final Button btnAceptar = (Button) v.findViewById(R.id.btnAceptarLogin);

        try {
            //Write file
            FileOutputStream stream = contexto.openFileOutput(bean.getUrlImagen(), Context.MODE_PRIVATE);
            imagex.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            //imagex.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result.equals("login success")){
            txtUsuario.setText(beanProf.getApeProfesor()+", "+beanProf.getNomProfesor());
            txtPerfil.setText(bean.getPerfil());
            imglogin.setImageBitmap(imagex);
            Log.i("--->bd","Se importó el usuario con éxito");
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Intent i = new Intent(contexto, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("obj", bean);
                    i.putExtra("objProf", beanProf);
                    //i.putExtra("imgUsu",imagex);
                    contexto.startActivity(i);
                }
            });
        }else { //(result.equals("login not success")){
            txtUsuario.setText("Error:"+result);
            txtPerfil.setText("Corrija los datos y vuelva a ingresar");
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
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
