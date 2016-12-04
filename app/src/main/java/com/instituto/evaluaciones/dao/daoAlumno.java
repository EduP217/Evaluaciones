package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoAlumno {

    bdconexion bd;

    public daoAlumno(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues alumnoMapperContentValues(BeanAlumno bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.ALUMNOSID,bean.getCodAlumno());
        cv.put(bdconstants.NOMALUMNOS,bean.getNomAlumno());
        cv.put(bdconstants.APEALUMNOS,bean.getApeAlumno());
        cv.put(bdconstants.SECALUMNOS,bean.getCodSeccion());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanAlumno buscarAlumno(String codigo){
        BeanAlumno bean = null;
        bd.openReadableDB();
        String where = bdconstants.ALUMNOSID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_ALUMNOS,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanAlumno();
            bean.setCodAlumno(c.getInt(0));
            bean.setNomAlumno(c.getString(1));
            bean.setApeAlumno(c.getString(2));
            bean.setCodSeccion(c.getInt(3));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //LISTAR TODOS LOS ALUMNOS POR SECCION
    public ArrayList<BeanAlumno> listarAlumnosxSec(String codseccion) {
        ArrayList<BeanAlumno> List = new ArrayList<BeanAlumno>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.ALUMNOSID,
                bdconstants.NOMALUMNOS,
                bdconstants.APEALUMNOS,
                bdconstants.SECALUMNOS
        };
        String where = bdconstants.SECALUMNOS + "= ?";
        String[] whereArgs = {codseccion};
        Cursor c = bd.getDb().query(bdconstants.TABLA_ALUMNOS, campos,where,whereArgs,null,null,null);

        try {
            while (c.moveToNext()){
                BeanAlumno bean = new BeanAlumno();
                bean.setCodAlumno(c.getInt(0));
                bean.setNomAlumno(c.getString(1));
                bean.setApeAlumno(c.getString(2));
                bean.setCodSeccion(c.getInt(3));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertAlumno(BeanAlumno bean){
        Log.i("daoAlumno","Registrando Alumno "+bean.getCodAlumno());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_ALUMNOS,null,alumnoMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS
    public void updateAlumno(BeanAlumno bean){
        Log.i("daoAlumno","Actualizando Alumno "+bean.getCodAlumno());
        bd.openWriteableDB();
        String where = bdconstants.ALUMNOSID + "= ?";
        bd.getDb().update(bdconstants.TABLA_ALUMNOS,alumnoMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodAlumno())});
        bd.closeDB();
    }

}
