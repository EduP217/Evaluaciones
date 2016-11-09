package com.instituto.evaluaciones.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eprieto on 09/11/2016.
 */

public class backgroundImage extends AsyncTask<String, Void, String> {

    Context contexto;
    ProgressDialog pd;

    public backgroundImage(Context ctx){
        contexto = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("-->doInBackground","Comienza descarga de imagen...");
        String url = "http://institutoevaluaciones.pe.hu/"+params[0];
        Bitmap imagen = descargarImagen(url);
        ContextWrapper cw = new ContextWrapper(contexto);
        File dirImages = cw.getDir("Imagenes", contexto.MODE_PRIVATE);
        File myPath = new File(dirImages,params[0]);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            //imagen.compress(Bitmap.CompressFormat.JPEG,10,fos);
            fos.flush();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(contexto);
        pd.setMessage("Cargando Datos");
        pd.setCancelable(false);
        //pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pd.dismiss();
        Log.i("-->FindoInBackground",result);
        Toast.makeText(contexto,result,Toast.LENGTH_LONG).show();
    }

    private Bitmap descargarImagen(String imageHttpAdress) {
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAdress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return imagen;
    }

}
