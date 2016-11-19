package com.instituto.evaluaciones.conexion;

/**
 * Created by eprieto on 31/10/2016.
 */
public class bdconstants {
    //General
    public static final String DB_NAME = "evaluaciones.db";
    public static final int DB_VERSION = 1;

    //TABLA USUARIO
    public static final String TABLA_USUARIO = "usuario";
    public static final String TABLA_MODALIDAD = "modalidad";
    public static final String TABLA_CICLO = "ciclo";
    public static final String TABLA_TIPOPRUEBA = "tipoprueba";
    public static final String TABLA_PRUEBA = "prueba";
    public static final String TABLA_ASIGNATURA = "asignatura";
    public static final String TABLA_PROFESOR = "profesor";
    public static final String TABLA_SECCION = "seccion";
    public static final String TABLA_ALUMNOS = "alumnos";
    public static final String TABLA_REGISTRONOTAS = "registro";


    public static final String USU_ID  = "codigo";
    public static final String USU_USU = "user";
    public static final String USU_PWD = "pwd";
    public static final String USU_EST = "estado";
    public static final String USU_URL = "urlImagen";
    public static final String USU_PERF = "perfil";

    public static final String MOD_ID  = "codigo";
    public static final String MOD_DES = "modalidad";

    public static final String CICLO_ID  = "cicloID";
    public static final String CICLO_DES = "cicloDes";

    public static final String SECCIONID  = "seccionID";
    public static final String NOMSECCION = "nomSeccion";

    public static final String TIPOID  = "tipoID";
    public static final String DESPTIPO = "destipo";

    public static final String PRUEBAID  = "pruebaID";
    public static final String NUMPRUEBA = "numPrueba";

    public static final String ASIGNATURAID  = "ASIGNATURAID";
    public static final String NOMASIGNATURA = "NOMASIGNATURA";

    public static final String PROFESORID = "profesor";
    public static final String NOMPROFESOR = "nombre";
    public static final String APEPROFESOR = "apellido";
    public static final String DNIPROFESOR = "dni";


    public static final String ALUMNOSID = "alumnos";
    public static final String NOMALUMNOS = "nombre";
    public static final String APEALUMNOS = "apellido";
    public static final String DNIALUMNOS = "dni";


    public static final String REGISTROID = "registro";
    public static final String REGISTROFECHA = "fecharegistro";
    public static final String REGISTRONOTA = "notas";


    public static final String TABLA_USUARIO_SQL =
            "CREATE TABLE   "+ TABLA_USUARIO + "("+
                    USU_ID + " INTEGER PRIMARY KEY," +
                    USU_USU + " TEXT NOT NULL," +
                    USU_PWD + " TEXT NOT NULL," +
                    USU_EST + " INTEGER NOT NULL,"+
                    USU_URL + " TEXT NOT NULL," +
                    USU_PERF + " TEXT NOT NULL );";

    //TABLA MODALIDAD
    public static final String TABLA_MODALIDAD_SQL =
            "CREATE TABLE   "+ TABLA_MODALIDAD + "("+
                    MOD_ID + " INTEGER PRIMARY KEY," +
                    MOD_DES + " TEXT NOT NULL );";

    //TABLA CICLO
    public static final String TABLA_CICLO_SQL =
            "CREATE TABLE   "+ TABLA_CICLO + "("+
                    CICLO_ID + " INTEGER PRIMARY KEY," +
                    CICLO_DES + " TEXT NOT NULL );";

    //TABLA SECCION
    public static final String TABLA_SECCION_SQL =
            "CREATE TABLE   "+ TABLA_SECCION + "("+
                    SECCIONID + " INTEGER PRIMARY KEY," +
                    NOMSECCION + " TEXT NOT NULL );";

    //TABLA TIPO PRUEBA
    public static final String TABLA_TIPOPRUEBA_SQL =
            "CREATE TABLE   "+ TABLA_TIPOPRUEBA + "("+
                    TIPOID + " INTEGER PRIMARY KEY," +
                    DESPTIPO + " TEXT NOT NULL );";

    //TABLA PRUEBA
    public static final String TABLA_PRUEBA_SQL =
            "CREATE TABLE   "+ TABLA_PRUEBA + "("+
                    PRUEBAID+ " INTEGER PRIMARY KEY," +
                    NUMPRUEBA + " TEXT NOT NULL );";

    //TABLA ASIGNATURA
    public static final String TABLA_ASIGNATURA_SQL =
            "CREATE TABLE   "+ TABLA_ASIGNATURA + "("+
                    ASIGNATURAID+ " INTEGER PRIMARY KEY," +
                    NOMASIGNATURA + " TEXT NOT NULL );";


    public static final String TABLA_PROFESOR_SQL =
            "CREATE TABLE   "+ TABLA_PROFESOR + "("+
                    PROFESORID+ " TEXT PRIMARY KEY," +
                    NOMPROFESOR + " TEXT NOT NULL"+
                    APEPROFESOR  + " TEXT NOT NULL"+
                    DNIPROFESOR + " TEXT NOT NULL );";



    public static final String TABLA_ALUMNOS_SQL =
            "CREATE TABLE   "+ TABLA_ALUMNOS + "("+
                    ALUMNOSID+ " INTEGER PRIMARY KEY," +
                    NOMALUMNOS + " TEXT NOT NULL"+
                    APEALUMNOS  + " TEXT NOT NULL"+
                    DNIALUMNOS + " TEXT NOT NULL );";



    public static final String TABLA_REGISTRONOTAS_SQL =
            "CREATE TABLE   "+ TABLA_REGISTRONOTAS + "("+
                    REGISTROID+ " INTEGER PRIMARY KEY," +
                    REGISTROFECHA+ "TEXT NOT NULL"+
                    REGISTRONOTA+ " INTEGER NOT NULL );";







}
