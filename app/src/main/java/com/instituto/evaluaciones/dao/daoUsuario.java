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
        Log.i("cantidad",""+c.getCount());
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new beanUsuario();
            bean.setCodigo(c.getInt(0));
            bean.setNombre(c.getString(1));
            bean.setApellido(c.getString(2));
            bean.setUser(c.getString(3));
            bean.setPwd(c.getString(4));
            bean.setEstado(c.getInt(5));
            bean.setUrlImagen(c.getString(6));
            bean.setPerfil(c.getString(7));
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
                        bdconstants.USU_NOM,
                        bdconstants.USU_APE,
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
                bean.setNombre(c.getString(1));
                bean.setApellido(c.getString(2));
                bean.setUser(c.getString(3));
                bean.setPwd(c.getString(4));
                bean.setEstado(c.getInt(5));
                bean.setUrlImagen(c.getString(6));
                bean.setPerfil(c.getString(7));
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
        cv.put(bdconstants.USU_NOM,bean.getNombre());
        cv.put(bdconstants.USU_APE,bean.getApellido());
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


}
