package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanDetalleProfAsig;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoDetalle {

    bdconexion bd;

    public daoDetalle(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues detalleMapperContentValues(BeanDetalleProfAsig bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.DETALLEIDPROF,bean.getIdProfesor());
        cv.put(bdconstants.DETALLEIDASIGN,bean.getIdAsignatura());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanDetalleProfAsig buscarDetalle(String codProf, String codAsign){
        BeanDetalleProfAsig bean = null;
        bd.openReadableDB();
        String where = bdconstants.DETALLEIDPROF + "= ? and "+ bdconstants.DETALLEIDASIGN + "= ?";
        String[] whereArgs = {codProf,codAsign};
        Cursor c = bd.getDb().query(bdconstants.TABLA_DETALLEPROFASI,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanDetalleProfAsig();
            bean.setIdProfesor(c.getString(0));
            bean.setIdAsignatura(c.getString(1));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertDetalle(BeanDetalleProfAsig bean){
        Log.i("daoDetalle","Registrando Detalle "+bean.getIdProfesor()+" "+bean.getIdAsignatura());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_DETALLEPROFASI,null,detalleMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //INSERTAR EN LA BASE DE DATOS
    public void updateDetalle(BeanDetalleProfAsig bean){
        Log.i("daoDetalle","Actualizando Detalle "+bean.getIdProfesor()+" "+bean.getIdAsignatura());
        /*bd.openWriteableDB();
        String where = bdconstants.DETALLEIDPROF + "= ?";
        bd.getDb().update(bdconstants.TABLA_USUARIO,usuarioMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodigo())});
        bd.closeDB();*/
    }

    //LISTAR TODAS LAS MODALIDADES
    public ArrayList<BeanDetalleProfAsig> listarAsignxProf(String idProf) {
        ArrayList<BeanDetalleProfAsig> List = new ArrayList<BeanDetalleProfAsig>();
        bd.openReadableDB();
        String[] campos = new String[]{
                bdconstants.DETALLEIDPROF,
                bdconstants.DETALLEIDASIGN
        };
        String where = bdconstants.DETALLEIDPROF + "= ?";
        String[] whereArgs = {idProf};
        Cursor c = bd.getDb().query(bdconstants.TABLA_DETALLEPROFASI, campos,where,whereArgs,null,null,null);

        try {
            while (c.moveToNext()){
                BeanDetalleProfAsig bean = new BeanDetalleProfAsig();
                bean.setIdProfesor(c.getString(0));
                bean.setIdAsignatura(c.getString(1));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

}
