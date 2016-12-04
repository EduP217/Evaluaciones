package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanCiclo;
import com.instituto.evaluaciones.beans.BeanModalidad;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoCiclo {
    bdconexion bd;

    public daoCiclo(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues cicloMapperContentValues(BeanCiclo bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.CICLO_ID,bean.getCicloID());
        cv.put(bdconstants.CICLO_DES,bean.getCiclo());
        cv.put(bdconstants.CICLO_MOD,bean.getCodMod());
        return cv;
    }

    private ContentValues seccMapperContentValues(BeanSeccion bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.SECCIONID,bean.getSeccionID());
        cv.put(bdconstants.NOMSECCION,bean.getSeccion());
        cv.put(bdconstants.CICLSECCION,bean.getCodCiclo());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanCiclo buscarCiclo(String codigo){
        BeanCiclo bean = null;
        bd.openReadableDB();
        String where = bdconstants.CICLO_ID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_CICLO,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanCiclo();
            bean.setCicloID(c.getInt(0));
            bean.setCiclo(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //OBTENER CICLO POR NOMBRE
    public BeanCiclo buscarCicloxNombre(String nombre){
        BeanCiclo bean = null;
        bd.openReadableDB();
        String where = bdconstants.CICLO_DES + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = bd.getDb().query(bdconstants.TABLA_CICLO,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanCiclo();
            bean.setCicloID(c.getInt(0));
            bean.setCiclo(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    public BeanSeccion buscarSeccion(String codigo){
        BeanSeccion bean = null;
        bd.openReadableDB();
        String where = bdconstants.SECCIONID + "= ?";
        String[] whereArgs = {codigo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_SECCION,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanSeccion();
            bean.setSeccionID(c.getInt(0));
            bean.setSeccion(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    public BeanSeccion buscarSeccionxNom(String nombre){
        BeanSeccion bean = null;
        bd.openReadableDB();
        String where = bdconstants.NOMSECCION + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = bd.getDb().query(bdconstants.TABLA_SECCION,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanSeccion();
            bean.setSeccionID(c.getInt(0));
            bean.setSeccion(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertCiclo(BeanCiclo bean){
        Log.i("daoCiclo","Registrando Ciclo "+bean.getCicloID());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_CICLO,null,cicloMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }

    public long insertSeccion(BeanSeccion bean){
        Log.i("daoCiclo","Registrando Seccion "+bean.getSeccionID());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_SECCION,null,seccMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS
    public void updateCiclo(BeanCiclo bean){
        Log.i("daoCiclo","Actualizando Ciclo "+bean.getCicloID());
        bd.openWriteableDB();
        String where = bdconstants.CICLO_ID + "= ?";
        bd.getDb().update(bdconstants.TABLA_CICLO,cicloMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCicloID())});
        bd.closeDB();
    }

    public void updateSeccion(BeanSeccion bean){
        Log.i("daoCiclo","Actualizando Seccion "+bean.getSeccionID());
        bd.openWriteableDB();
        String where = bdconstants.SECCIONID + "= ?";
        bd.getDb().update(bdconstants.TABLA_SECCION,seccMapperContentValues(bean), where, new String[]{String.valueOf(bean.getSeccionID())});
        bd.closeDB();
    }

    //LISTAR TODOS LOS CICLOS
    public ArrayList<BeanCiclo> listarCiclos() {
        ArrayList<BeanCiclo> List = new ArrayList<BeanCiclo>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.CICLO_ID,
                bdconstants.CICLO_DES,
                bdconstants.CICLO_MOD
        };

        Cursor c = bd.getDb().query(bdconstants.TABLA_CICLO, campos,null,null,null,null,null);

        try {
            while (c.moveToNext()){
                BeanCiclo bean = new BeanCiclo();
                bean.setCicloID(c.getInt(0));
                bean.setCiclo(c.getString(1));
                bean.setCodMod(c.getInt(2));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //LISTAR TODAS LAS MODALIDADES
    public ArrayList<BeanSeccion> listarSecciones(int codCiclo) {
        ArrayList<BeanSeccion> List = new ArrayList<BeanSeccion>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.SECCIONID,
                bdconstants.NOMSECCION,
                bdconstants.CICLSECCION
        };
        String where = bdconstants.CICLSECCION + "= ?";
        String[] whereArgs = {""+codCiclo};
        Cursor c = bd.getDb().query(bdconstants.TABLA_SECCION, campos,where,whereArgs,null,null,null);

        try {
            while (c.moveToNext()){
                BeanSeccion bean = new BeanSeccion();
                bean.setSeccionID(c.getInt(0));
                bean.setSeccion(c.getString(1));
                bean.setCodCiclo(c.getInt(2));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }
}
