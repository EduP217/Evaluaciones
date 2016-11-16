package com.instituto.evaluaciones.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.instituto.evaluaciones.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eprieto on 09/11/2016.
 */

public class backgroundImage extends AsyncTask<String, Void, Bitmap> {

    Context contexto;
    ProgressDialog pd;
    ImageView x;
    public backgroundImage(Context ctx){
        contexto = ctx;
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.content_main,null);
        x = (ImageView)v.findViewById(R.id.imgAvatar);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.i("-->doInBackground","Comienza descarga de imagen...");
        String url = "http://institutoevaluaciones.pe.hu/Images/"+params[0];
        Log.i("-->doInBack2",url);
        Bitmap imagen = null;
        try{
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        /*ContextWrapper cw = new ContextWrapper(contexto);
        File dirImages = cw.getDir("Imagenes", contexto.MODE_PRIVATE);
        File myPath = new File(dirImages,params[0]);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG,10,fos);
            fos.flush();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }*/
        return imagen;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(contexto);
        pd.setMessage("Cargando Datos");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        pd.dismiss();
        x.setImageBitmap(result);
    }

}
