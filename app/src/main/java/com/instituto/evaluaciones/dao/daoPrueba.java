package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanPrueba;
import com.instituto.evaluaciones.beans.BeanTipoPrueba;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoPrueba {
    bdconexion bd;

    public daoPrueba(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues pruebaMapperContentValues(BeanPrueba bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.PRUEBAID,bean.getCodPrueba());
        cv.put(bdconstants.NUMPRUEBA,bean.getNumPrueba());
        cv.put(bdconstants.PRUEBATIP,bean.getCodTipo());
        return cv;
    }

    private ContentValues tipopruebaMapperContentValues(BeanTipoPrueba bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.TIPOID,bean.getTipo());
        cv.put(bdconstants.DESPTIPO,bean.getNomTipo());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanPrueba buscarPrueba(String codigo){
        BeanPrueba bean = null;
        bd.openReadableDB();
        String where = bdconstants.PRUEBAID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_PRUEBA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanPrueba();
            bean.setCodPrueba(c.getInt(0));
            bean.setNumPrueba(c.getString(1));
            bean.setCodTipo(c.getInt(2));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //OBTENER UN REGISTRO POR NOMBRE
    public BeanPrueba buscarPruebaxNom(String nombre){
        BeanPrueba bean = null;
        bd.openReadableDB();
        String where = bdconstants.NUMPRUEBA + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = bd.getDb().query(bdconstants.TABLA_PRUEBA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanPrueba();
            bean.setCodPrueba(c.getInt(0));
            bean.setNumPrueba(c.getString(1));
            bean.setCodTipo(c.getInt(2));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //OBTENER UN REGISTRO TIPO
    public BeanTipoPrueba buscarTipo(String codigo){
        BeanTipoPrueba bean = null;
        bd.openReadableDB();
        String where = bdconstants.TIPOID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_TIPOPRUEBA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanTipoPrueba();
            bean.setTipo(c.getInt(0));
            bean.setNomTipo(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //OBTENER UN REGISTRO TIPO POR SU NOMBRE
    public BeanTipoPrueba buscarTipoxNom(String nombre){
        BeanTipoPrueba bean = null;
        bd.openReadableDB();
        String where = bdconstants.DESPTIPO + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = bd.getDb().query(bdconstants.TABLA_TIPOPRUEBA,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanTipoPrueba();
            bean.setTipo(c.getInt(0));
            bean.setNomTipo(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //LISTAR TODOS LOS NUM PRUEBA
    public ArrayList<BeanPrueba> listarNumP(int codTipo) {
        ArrayList<BeanPrueba> List = new ArrayList<BeanPrueba>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.PRUEBAID,
                bdconstants.NUMPRUEBA,
                bdconstants.PRUEBATIP
        };

        String where = bdconstants.PRUEBATIP + "= ?";
        String[] whereArgs = {""+codTipo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_PRUEBA, campos,where,whereArgs,null,null,null);

        try {
            while (c.moveToNext()){
                BeanPrueba bean = new BeanPrueba();
                bean.setCodPrueba(c.getInt(0));
                bean.setNumPrueba(c.getString(1));
                bean.setCodTipo(c.getInt(2));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //LISTAR TODOS LOS TIPOS DE PRUEBA
    public ArrayList<BeanTipoPrueba> listarTipo() {
        ArrayList<BeanTipoPrueba> List = new ArrayList<BeanTipoPrueba>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.TIPOID,
                bdconstants.DESPTIPO
        };

        Cursor c = bd.getDb().query(bdconstants.TABLA_TIPOPRUEBA, campos,null,null,null,null,null);

        try {
            while (c.moveToNext()){
                BeanTipoPrueba bean = new BeanTipoPrueba();
                bean.setTipo(c.getInt(0));
                bean.setNomTipo(c.getString(1));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //INSERTAR EN LA BASE DE DATOS PRUEBA
    public long insertPrueba(BeanPrueba bean){
        Log.i("daoPrueba","Registrando Prueba "+bean.getCodPrueba());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_PRUEBA,null,pruebaMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }

    //INSERTAR EN LA BASE DE DATOS TIPO PRUEBA
    public long insertTipoPrueba(BeanTipoPrueba bean){
        Log.i("daoTipo","Registrando Tipo "+bean.getNomTipo());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_TIPOPRUEBA,null,tipopruebaMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS PRUEBA
    public void updatePrueba(BeanPrueba bean){
        Log.i("daoPrueba","Actualizando Prueba "+bean.getCodPrueba());
        bd.openWriteableDB();
        String where = bdconstants.PRUEBAID + "= ?";
        bd.getDb().update(bdconstants.TABLA_PRUEBA,pruebaMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodPrueba())});
        bd.closeDB();
    }

    //UPDATE EN LA BASE DE DATOS TIPO PRUEBA
    public void updateTipoPrueba(BeanTipoPrueba bean){
        Log.i("daoTipo","Actualizando Tipo "+bean.getNomTipo());
        bd.openWriteableDB();
        String where = bdconstants.TIPOID + "= ?";
        bd.getDb().update(bdconstants.TABLA_TIPOPRUEBA,tipopruebaMapperContentValues(bean), where, new String[]{String.valueOf(bean.getTipo())});
        bd.closeDB();
    }
}
