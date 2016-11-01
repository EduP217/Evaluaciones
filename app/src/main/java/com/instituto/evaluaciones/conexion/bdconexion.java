package com.instituto.evaluaciones.conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.instituto.evaluaciones.beans.beanUsuario;

import java.util.ArrayList;

/**
 * Created by eprieto on 31/10/2016.
 */
public class bdconexion {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public bdconexion(Context context){
        dbHelper = new DBHelper(context);
    }

    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB(){
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB(){
        if(db!=null){
            db.close();
        }
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, bdconstants.DB_NAME, null, bdconstants.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase bd) {
            bd.execSQL(bdconstants.TABLA_USUARIO_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAnterior, int versionNueva) {
            //ESTO SE USA PARA ELIMINAR UNA TABLA ANTIGUA E INGRESAR UNA NUEVA.
        }

    }

}
