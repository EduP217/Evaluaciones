package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanRegistroNota implements Serializable {
    private int CodRegistro;
    private String fechaRegistro;
    private int nota;


    public int getCodRegistro() {
        return CodRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        CodRegistro = codRegistro;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
