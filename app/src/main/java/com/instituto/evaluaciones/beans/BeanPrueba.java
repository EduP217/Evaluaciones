package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanPrueba implements Serializable {
    private int codPrueba;
    private String numPrueba;
    private int codTipo;

    public int getCodPrueba() {
        return codPrueba;
    }

    public void setCodPrueba(int codPrueba) {
        this.codPrueba = codPrueba;
    }

    public String getNumPrueba() {
        return numPrueba;
    }

    public void setNumPrueba(String numPrueba) {
        this.numPrueba = numPrueba;
    }

    public int getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    public String toString(){
        return numPrueba;
    }
}
