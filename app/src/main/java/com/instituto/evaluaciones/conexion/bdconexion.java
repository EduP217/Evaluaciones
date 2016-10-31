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

    private ContentValues usuarioMapperContentValues(beanUsuario bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.USU_NOM,bean.getNombre());
        cv.put(bdconstants.USU_APE,bean.getApellido());
        cv.put(bdconstants.USU_USU,bean.getUser());
        cv.put(bdconstants.USU_PWD,bean.getPwd());
        cv.put(bdconstants.USU_EST,bean.getEstado());
        return cv;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertUsuario(beanUsuario bean){
        this.openWriteableDB();
        long rowID = db.insert(bdconstants.TABLA_USUARIO,null,usuarioMapperContentValues(bean));
        this.closeDB();
        return rowID;
    }

    //CONSULTAR TODOS LOS USUARIOS
    public ArrayList<beanUsuario> loadUsuario(){
        ArrayList<beanUsuario> List = new ArrayList<beanUsuario>();
        this.openReadableDB();
        String[] campos =
                new String[]{
                    bdconstants.USU_ID,
                    bdconstants.USU_NOM,
                    bdconstants.USU_APE,
                    bdconstants.USU_USU,
                    bdconstants.USU_PWD,
                    bdconstants.USU_EST
                };
        Cursor c = db.query(bdconstants.TABLA_USUARIO, campos,null,null,null,null,null);

        try {
            while (c.moveToNext()){
                beanUsuario bean = new beanUsuario();
                bean.setCodigo(c.getInt(0));
                bean.setNombre(c.getString(1));
                bean.setApellido(c.getString(2));
                bean.setUser(c.getString(3));
                bean.setPwd(c.getString(4));
                bean.setEstado(c.getInt(5));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        this.closeDB();
        return List;
    }

    //OBTENER UN REGISTRO
    public beanUsuario buscarUsuario(String userparam, String pwdparam){
        beanUsuario bean = new beanUsuario();
        this.openReadableDB();
        String where = bdconstants.USU_USU + "= ? and " + bdconstants.USU_PWD + "= ?";
        String[] whereArgs = {userparam,pwdparam};
        Cursor c = db.query(bdconstants.TABLA_USUARIO,null,where,whereArgs,null,null,null);

        if(c!=null||c.getCount()>=0){
            c.moveToFirst();
            bean.setCodigo(c.getInt(0));
            bean.setNombre(c.getString(1));
            bean.setApellido(c.getString(2));
            bean.setUser(c.getString(3));
            bean.setPwd(c.getString(4));
            bean.setEstado(c.getInt(5));
            c.close();
        }
        this.closeDB();
        return bean;
    }
}
