package com.instituto.evaluaciones.beans;

import java.io.Serializable;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class BeanRegistroNota implements Serializable {
    private int CodRegistro;
    private String fechaRegistro;
    private int nota;
    private String codProfesor;
    private int codAlumno;
    private String codAsignatura;
    private int codPrueba;

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


    public String getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(String codProfesor) {
        this.codProfesor = codProfesor;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public int getCodPrueba() {
        return codPrueba;
    }

    public void setCodPrueba(int codPrueba) {
        this.codPrueba = codPrueba;
    }
}
