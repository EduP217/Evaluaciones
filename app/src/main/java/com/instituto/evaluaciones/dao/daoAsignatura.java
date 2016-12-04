package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanCiclo;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoAsignatura {

    bdconexion bd;

    public daoAsignatura(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues asignaturaMapperContentValues(BeanAsignatura bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.ASIGNATURAID,bean.getCodAsignatura());
        cv.put(bdconstants.NOMASIGNATURA,bean.getNomAsignatura());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanAsignatura buscarAsignatura(String codigo){
        BeanAsignatura bean = null;
        bd.openReadableDB();
        String where = bdconstants.ASIGNATURAID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_ASIGNATURA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanAsignatura();
            bean.setCodAsignatura(c.getString(0));
            bean.setNomAsignatura(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //OBTENER UN REGISTRO POR NOMBRE
    public BeanAsignatura buscarAsignaturaxNom(String nombre){
        BeanAsignatura bean = null;
        bd.openReadableDB();
        String where = bdconstants.NOMASIGNATURA + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = bd.getDb().query(bdconstants.TABLA_ASIGNATURA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanAsignatura();
            bean.setCodAsignatura(c.getString(0));
            bean.setNomAsignatura(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //LISTAR TODOS LOS CICLOS
    public ArrayList<BeanAsignatura> listarCursos() {
        ArrayList<BeanAsignatura> List = new ArrayList<BeanAsignatura>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.ASIGNATURAID,
                bdconstants.NOMASIGNATURA
        };

        Cursor c = bd.getDb().query(bdconstants.TABLA_ASIGNATURA, campos,null,null,null,null,null);

        try {
            while (c.moveToNext()){
                BeanAsignatura bean = new BeanAsignatura();
                bean.setCodAsignatura(c.getString(0));
                bean.setNomAsignatura(c.getString(1));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertAsignatura(BeanAsignatura bean){
        Log.i("daoAsignatura","Registrando Asignatura "+bean.getCodAsignatura());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_ASIGNATURA,null,asignaturaMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS
    public void updateAsignatura(BeanAsignatura bean){
        Log.i("daoAsignatura","Actualizando Asignatura "+bean.getCodAsignatura());
        bd.openWriteableDB();
        String where = bdconstants.ASIGNATURAID + "= ?";
        bd.getDb().update(bdconstants.TABLA_ASIGNATURA,asignaturaMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodAsignatura())});
        bd.closeDB();
    }

}
