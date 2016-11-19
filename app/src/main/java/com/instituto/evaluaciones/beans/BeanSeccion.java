package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanSeccion implements Serializable {
    private int seccionID;
    private String seccion;


    public int getSeccionID() {
        return seccionID;
    }

    public void setSeccionID(int seccionID) {
        this.seccionID = seccionID;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
