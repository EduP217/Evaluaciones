package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanCiclo implements Serializable {
    private int cicloID;
    private String ciclo;
    private int codMod;

    public int getCicloID() {
        return cicloID;
    }

    public void setCicloID(int cicloID) {
        this.cicloID = cicloID;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }


    public int getCodMod() {
        return codMod;
    }

    public void setCodMod(int codMod) {
        this.codMod = codMod;
    }

    public String toString(){
        return ciclo;
    }
}
