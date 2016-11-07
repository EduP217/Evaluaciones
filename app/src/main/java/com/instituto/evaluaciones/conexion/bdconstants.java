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

    public static final String USU_ID  = "codigo";
    public static final String USU_NOM = "nombre";
    public static final String USU_APE = "apellido";
    public static final String USU_USU = "user";
    public static final String USU_PWD = "pwd";
    public static final String USU_EST = "estado";
    public static final String USU_URL = "urlImagen";
    public static final String USU_PERF = "perfil";

    public static final String TABLA_USUARIO_SQL =
            "CREATE TABLE   "+ TABLA_USUARIO + "("+
                    USU_ID + " INTEGER PRIMARY KEY," +
                    USU_NOM + " TEXT NOT NULL," +
                    USU_APE + " TEXT NOT NULL," +
                    USU_USU + " TEXT NOT NULL," +
                    USU_PWD + " TEXT NOT NULL," +
                    USU_EST + " INTEGER NOT NULL,"+
                    USU_URL + " TEXT NOT NULL," +
                    USU_PERF + " TEXT NOT NULL );";

    //TABLA ENLACES
    public static final String TABLA_ENLACES = "enlaces";

    public static final String ENL_ID   = "id";
    public static final String ENL_PERF = "perfil";
    public static final String ENL_ENL  = "enlace";
    public static final String ENL_DESC = "descripcion";

    public static final String TABLA_ENLACES_SQL =
            "CREATE TABLE   "+ TABLA_ENLACES + "("+
                    ENL_ID   + " INTEGER PRIMARY KEY," +
                    ENL_PERF + " TEXT NOT NULL," +
                    ENL_ENL  + " TEXT NOT NULL," +
                    ENL_DESC + " TEXT NOT NULL);";

}
