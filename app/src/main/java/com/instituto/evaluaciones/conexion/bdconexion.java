package com.instituto.evaluaciones.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eprieto on 31/10/2016.
 */
public class bdconexion {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public bdconexion(Context context){
        setDbHelper(new DBHelper(context));
    }

    public void openReadableDB(){
        setDb(getDbHelper().getReadableDatabase());
    }

    public void openWriteableDB(){
        setDb(getDbHelper().getWritableDatabase());
    }

    public void closeDB(){
        if(getDb() !=null){
            getDb().close();
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, bdconstants.DB_NAME, null, bdconstants.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase bd) {
            bd.execSQL(bdconstants.TABLA_USUARIO_SQL);
            bd.execSQL(bdconstants.TABLA_USU_SETT_SQL);
            bd.execSQL(bdconstants.TABLA_PROFESOR_SQL);
            bd.execSQL(bdconstants.TABLA_ALUMNOS_SQL);
            bd.execSQL(bdconstants.TABLA_MODALIDAD_SQL);
            bd.execSQL(bdconstants.TABLA_CICLO_SQL);
            bd.execSQL(bdconstants.TABLA_SECCION_SQL);
            bd.execSQL(bdconstants.TABLA_ASIGNATURA_SQL);
            bd.execSQL(bdconstants.TABLA_TIPOPRUEBA_SQL);
            bd.execSQL(bdconstants.TABLA_PRUEBA_SQL);
            bd.execSQL(bdconstants.TABLA_REGISTRONOTAS_SQL);
            bd.execSQL(bdconstants.TABLA_DETALLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAnterior, int versionNueva) {
            //ESTO SE USA PARA ELIMINAR UNA TABLA ANTIGUA E INGRESAR UNA NUEVA.
        }

    }

}
