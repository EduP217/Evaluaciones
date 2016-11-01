package com.instituto.evaluaciones.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 01/11/2016.
 */

public class daoUsuario {
    /*
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
    }*/

}
