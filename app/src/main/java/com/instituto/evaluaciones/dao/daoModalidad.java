package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanModalidad;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class daoModalidad {

    bdconexion bd;

    public daoModalidad(Context nom){
        bd = new bdconexion(nom);
    }

    private ContentValues modalMapperContentValues(BeanModalidad bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.MOD_ID,bean.getModID());
        cv.put(bdconstants.MOD_DES,bean.getModalidad());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanModalidad buscarModal(String codigo){
        BeanModalidad bean = null;
        bd.openReadableDB();
        String where = bdconstants.MOD_ID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_MODALIDAD,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanModalidad();
            bean.setModID(c.getInt(0));
            bean.setModalidad(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertModalidad(BeanModalidad bean){
        Log.i("daoModal","Registrando Modalidad "+bean.getModID());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_MODALIDAD,null,modalMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS
    public void updateModalidad(BeanModalidad bean){
        Log.i("daoModal","Actualizando Modalidad "+bean.getModID());
        bd.openWriteableDB();
        String where = bdconstants.MOD_ID + "= ?";
        bd.getDb().update(bdconstants.TABLA_MODALIDAD,modalMapperContentValues(bean), where, new String[]{String.valueOf(bean.getModID())});
        bd.closeDB();
    }

    //LISTAR TODAS LAS MODALIDADES
    public ArrayList<BeanModalidad> listarModalidad(String codigo) {
        ArrayList<BeanModalidad> List = new ArrayList<BeanModalidad>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.MOD_ID,
                bdconstants.MOD_DES,
        };
        String where = bdconstants.MOD_ID + "= ?";
        String[] whereArgs = {""+codigo};

        Cursor c = bd.getDb().query(bdconstants.TABLA_MODALIDAD, campos,where,whereArgs,null,null,null);

        try {
            while (c.moveToNext()){
                BeanModalidad bean = new BeanModalidad();
                bean.setModID(c.getInt(0));
                bean.setModalidad(c.getString(1));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }


}
