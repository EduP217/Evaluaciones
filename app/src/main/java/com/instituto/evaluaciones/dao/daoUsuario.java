package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 01/11/2016.
 */

public class daoUsuario {

    bdconexion bd;

    public daoUsuario(Context ctx){
        bd = new bdconexion(ctx);
    }

    //OBTENER UN REGISTRO
    public beanUsuario buscarUsuario(String userparam, String pwdparam){
        beanUsuario bean = null;
        bd.openReadableDB();
        String where = bdconstants.USU_USU + "= ? and " + bdconstants.USU_PWD + "= ?";
        String[] whereArgs = {userparam,pwdparam};
        Cursor c = bd.getDb().query(bdconstants.TABLA_USUARIO,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new beanUsuario();
            bean.setCodigo(c.getInt(0));
            bean.setUser(c.getString(1));
            bean.setPwd(c.getString(2));
            bean.setEstado(c.getInt(3));
            bean.setUrlImagen(c.getString(4));
            bean.setPerfil(c.getString(5));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //CONSULTAR TODOS LOS USUARIOS
    public ArrayList<beanUsuario> loadUsuario(){
        ArrayList<beanUsuario> List = new ArrayList<beanUsuario>();
        bd.openReadableDB();
        String[] campos =
                new String[]{
                        bdconstants.USU_ID,
                        bdconstants.USU_USU,
                        bdconstants.USU_PWD,
                        bdconstants.USU_EST,
                        bdconstants.USU_URL,
                        bdconstants.USU_PERF
                };
        Cursor c = bd.getDb().query(bdconstants.TABLA_USUARIO, campos,null,null,null,null,null);

        try {
            while (c.moveToNext()){
                beanUsuario bean = new beanUsuario();
                bean.setCodigo(c.getInt(0));
                bean.setUser(c.getString(1));
                bean.setPwd(c.getString(2));
                bean.setEstado(c.getInt(3));
                bean.setUrlImagen(c.getString(4));
                bean.setPerfil(c.getString(5));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }


    private ContentValues usuarioMapperContentValues(beanUsuario bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.USU_USU,bean.getUser());
        cv.put(bdconstants.USU_PWD,bean.getPwd());
        cv.put(bdconstants.USU_EST,bean.getEstado());
        cv.put(bdconstants.USU_URL,bean.getUrlImagen());
        cv.put(bdconstants.USU_PERF,bean.getPerfil());
        return cv;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertUsuario(beanUsuario bean){
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_USUARIO,null,usuarioMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //ACTUALIZAR O MODIFICAR EN LA BASE DE DATOS
    public void updateUsuario(beanUsuario bean){
        bd.openWriteableDB();
        String where = bdconstants.USU_ID + "= ?";
        bd.getDb().update(bdconstants.TABLA_USUARIO,usuarioMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodigo())});
        bd.closeDB();
    }
    //ELIMINAR
    public void deleteUsuario(int id){
        bd.openWriteableDB();
        String where = bdconstants.USU_ID + "= ?";
        bd.getDb().delete(bdconstants.TABLA_USUARIO, where, new String[]{String.valueOf(id)});
        bd.closeDB();
    }

    //INSERTAR EN LA BASE DE DATOS SETTINGS
    public void insertSetting(String codigo){
        Log.i("daoProfe","Registrando Setting "+codigo);
        bd.openWriteableDB();
        String sql = "INSERT INTO "+bdconstants.TABLA_USUARIO_SETT+" values('"+codigo+"',1)";
        bd.getDb().execSQL(sql);
        bd.closeDB();
    }

    public int estadoSetting(String codigo){
        int estado = 1;
        bd.openReadableDB();
        String where = bdconstants.USU_SET_ID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_USUARIO_SETT,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            estado = c.getInt(1);
            c.close();
        }
        bd.closeDB();
        return estado;
    }

    //UPDATE EN LA BASE DE DATOS SETTINGS
    public void updateSetting(String codigo,int valor){
        Log.i("daoProfe","Actualizando Setting "+codigo);
        bd.openWriteableDB();
        String sql = "UPDATE "+bdconstants.TABLA_USUARIO_SETT+" set "+bdconstants.USU_SET_VALUE+"="+valor+" where "+bdconstants.USU_SET_ID+"='"+codigo+"'";
        bd.getDb().execSQL(sql);
        bd.closeDB();
    }
}
