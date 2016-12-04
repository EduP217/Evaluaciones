package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class daoProfesor {
    bdconexion bd;

    public daoProfesor(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues profesorMapperContentValues(BeanProfesor bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.PROFESORID,bean.getCodProfesor());
        cv.put(bdconstants.NOMPROFESOR,bean.getNomProfesor());
        cv.put(bdconstants.APEPROFESOR,bean.getApeProfesor());
        cv.put(bdconstants.DNIPROFESOR,bean.getDni());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanProfesor buscarProfesor(String usuario){
        BeanProfesor bean = null;
        bd.openReadableDB();
        String where = bdconstants.DNIPROFESOR + "= ?";
        String[] whereArgs = {usuario};
        Cursor c = bd.getDb().query(bdconstants.TABLA_PROFESOR,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanProfesor();
            bean.setCodProfesor(c.getString(0));
            bean.setNomProfesor(c.getString(1));
            bean.setApeProfesor(c.getString(2));
            bean.setDni(c.getString(3));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertProfesor(BeanProfesor bean){
        Log.i("daoProfe","Registrando Profesor "+bean.getCodProfesor());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_PROFESOR,null,profesorMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }

}
