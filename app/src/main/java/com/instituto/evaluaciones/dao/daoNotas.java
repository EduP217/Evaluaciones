package com.instituto.evaluaciones.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanRegistroNota;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.conexion.bdconstants;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class daoNotas {

    bdconexion bd;

    public daoNotas(Context ctx){
        bd = new bdconexion(ctx);
    }

    private ContentValues notasMapperContentValues(BeanRegistroNota bean){
        ContentValues cv = new ContentValues();
        cv.put(bdconstants.REGISTROID,bean.getCodRegistro());
        cv.put(bdconstants.REGISTRONOTA,bean.getNota());
        cv.put(bdconstants.REGISTROFECHA,bean.getFechaRegistro());
        cv.put(bdconstants.REGISTROPROF,bean.getCodProfesor());
        cv.put(bdconstants.REGISTROALUM,bean.getCodAlumno());
        cv.put(bdconstants.REGISTROASIGN,bean.getCodAsignatura());
        cv.put(bdconstants.REGISTROPRUEB,bean.getCodPrueba());
        return cv;
    }

    //OBTENER UN REGISTRO
    public BeanRegistroNota buscarNotas(String codProf,String codAlumn,String codAsign,String codPrueb){
        BeanRegistroNota bean = null;
        bd.openReadableDB();
        String where = bdconstants.REGISTROPROF + "= ? and " + bdconstants.REGISTROALUM + "= ? and " + bdconstants.REGISTROASIGN + "= ? and " + bdconstants.REGISTROPRUEB + "= ?";
        String[] whereArgs = {codProf,codAlumn,codAsign,codPrueb};
        Cursor c = bd.getDb().query(bdconstants.TABLA_REGISTRONOTAS,null,where,whereArgs,null,null,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            bean = new BeanRegistroNota();
            bean.setCodRegistro(c.getInt(0));
            bean.setNota(c.getInt(1));
            bean.setFechaRegistro(c.getString(2));
            bean.setCodProfesor(c.getString(3));
            bean.setCodAlumno(c.getInt(4));
            bean.setCodAsignatura(c.getString(5));
            bean.setCodPrueba(c.getInt(6));
            c.close();
        }
        bd.closeDB();
        return bean;
    }

    //LISTAR REGISTRO SEGUN PARAMETROS
    public ArrayList<BeanRegistroNota> listarRegistros(String codProf,String codAsign,String codPrueb,String codciclo) {
        ArrayList<BeanRegistroNota> List = new ArrayList<BeanRegistroNota>();
        bd.openReadableDB();
        /*String[] campos = new String[]{
                bdconstants.REGISTROID,
                bdconstants.REGISTRONOTA,
                bdconstants.REGISTROFECHA,
                bdconstants.REGISTROPROF,
                bdconstants.REGISTROALUM,
                bdconstants.REGISTROASIGN,
                bdconstants.REGISTROPRUEB
        };*/
        //String where = bdconstants.REGISTROPROF + "= ? and " + bdconstants.REGISTROASIGN + "= ? and " + bdconstants.REGISTROPRUEB + "= ?";
        String sql = "SELECT "+ bdconstants.REGISTROID + "," +
                                bdconstants.REGISTRONOTA + "," +
                                bdconstants.REGISTROFECHA + "," +
                                bdconstants.REGISTROPROF + "," +
                                bdconstants.REGISTROALUM + "," +
                                bdconstants.REGISTROASIGN + "," +
                                bdconstants.REGISTROPRUEB +
                     " FROM "+bdconstants.TABLA_REGISTRONOTAS+ " reg inner join "+bdconstants.TABLA_ALUMNOS+" al on reg."+bdconstants.REGISTROALUM+" = al."+bdconstants.ALUMNOSID+
                     " inner join "+bdconstants.TABLA_SECCION+" se on al."+bdconstants.SECALUMNOS+" = se."+bdconstants.SECCIONID+
                     " inner join "+bdconstants.TABLA_CICLO+" ci on se."+bdconstants.CICLSECCION+" = ci."+bdconstants.CICLO_ID+
                     " where "+bdconstants.REGISTROPROF+"=? and "+bdconstants.REGISTROASIGN+"=? and "+bdconstants.REGISTROPRUEB+"=? and "+bdconstants.CICLO_ID+"=?";
        String[] whereArgs = {codProf,codAsign,codPrueb, codciclo};
        //Cursor c = bd.getDb().query(bdconstants.TABLA_REGISTRONOTAS, campos,where,whereArgs,null,null,null);
        Cursor c = bd.getDb().rawQuery(sql,whereArgs);
        try {
            while (c.moveToNext()){
                BeanRegistroNota bean = new BeanRegistroNota();
                bean.setCodRegistro(c.getInt(0));
                bean.setNota(c.getInt(1));
                bean.setFechaRegistro(c.getString(2));
                bean.setCodProfesor(c.getString(3));
                bean.setCodAlumno(c.getInt(4));
                bean.setCodAsignatura(c.getString(5));
                bean.setCodPrueba(c.getInt(6));
                List.add(bean);
            }
        } finally {
            c.close();
        }
        bd.closeDB();
        return List;
    }

    //OBTENER EL ULTIMO ID
    public int UltimaNota(){
        int Id = 0;
        bd.openReadableDB();
        String max = "select max(idRegistro) from registro";
        Cursor c = bd.getDb().rawQuery(max,null);
        if(c.getCount()>=1){
            c.moveToFirst();
            Id = c.getInt(0);
            c.close();
        }
        bd.closeDB();
        Id += 1;
        return Id;
    }

    //INSERTAR EN LA BASE DE DATOS
    public long insertNota(BeanRegistroNota bean){
        Log.i("daoRegistro","Registrando Notas "+bean.getCodAlumno()+"-"+bean.getNota());
        bd.openWriteableDB();
        long rowID = bd.getDb().insert(bdconstants.TABLA_REGISTRONOTAS,null,notasMapperContentValues(bean));
        bd.closeDB();
        return rowID;
    }
    //UPDATE EN LA BASE DE DATOS
    public void updateNota(BeanRegistroNota bean){
        Log.i("daoRegistro","Actualizando Notas "+bean.getNota());
        bd.openWriteableDB();
        String where = bdconstants.REGISTROID + "= ?";
        bd.getDb().update(bdconstants.TABLA_REGISTRONOTAS,notasMapperContentValues(bean), where, new String[]{String.valueOf(bean.getCodRegistro())});
        bd.closeDB();
    }

}
