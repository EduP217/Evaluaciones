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

    public static final String TABLA_USUARIO_SQL =
            "CREATE TABLE   "+ TABLA_USUARIO + "("+
            USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USU_NOM + " TEXT NOT NULL," +
            USU_APE + " TEXT NOT NULL," +
            USU_USU + " TEXT NOT NULL," +
            USU_PWD + " TEXT NOT NULL," +
            USU_EST + " INTEGER DEFAULT 1);";
}
