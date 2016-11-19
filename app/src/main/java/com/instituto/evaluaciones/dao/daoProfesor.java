package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;

import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class daoProfesor {
    bdconexion bd;

    public daoProfesor(Context nom){
        bd = new bdconexion(nom);
    }

    private ContentValues profesorMapperContentValues(BeanProfesor bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.PROFESORID,bean.getCodProfesor());
        cv.put(bdconstants.NOMPROFESOR,bean.getNomProfesor());
        cv.put(bdconstants.APEPROFESOR,bean.getApeProfesor());
        cv.put(bdconstants.DNIPROFESOR,bean.getDni());
        return cv;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertProfesor(BeanProfesor bean){
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_PROFESOR,null,profesorMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
}
