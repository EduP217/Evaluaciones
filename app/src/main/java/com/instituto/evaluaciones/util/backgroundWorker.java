package com.instituto.evaluaciones.util;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Edu on 01/11/2016.
 */

public class backgroundWorker extends AsyncTask<String,Void,Void> {

    Context contexto;

    public backgroundWorker(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    protected Void doInBackground(String... params) {
        String type = params[0];
        if(type.equals("login")){
            //--> continuar viendo https://www.youtube.com/watch?v=eldh8l8yPew ;

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
