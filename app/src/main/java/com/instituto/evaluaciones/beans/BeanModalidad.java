package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanModalidad implements Serializable {
    private int modID;
    private String modalidad;


    public int getModID() {
        return modID;
    }

    public void setModID(int modID) {
        this.modID = modID;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
