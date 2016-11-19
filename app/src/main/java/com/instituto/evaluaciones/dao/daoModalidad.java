package com.instituto.evaluaciones.dao;

import android.content.Context;
import android.database.Cursor;

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

    //CONSULTAR TODOS LOS USUARIOS
    public ArrayList<BeanModalidad> loadModalidad() {
        ArrayList<BeanModalidad> List = new ArrayList<BeanModalidad>();
        bd.openReadableDB();
        String[] campos =
                new String[]{
                        bdconstants.MOD_ID,
                        bdconstants.MOD_DES,
                };

        Cursor c = bd.getDb().query(bdconstants.TABLA_MODALIDAD, campos,null,null,null,null,null);

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
