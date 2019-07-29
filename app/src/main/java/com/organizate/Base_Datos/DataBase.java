package com.organizate.Base_Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBase extends SQLiteOpenHelper {

    private static final int VERSION = 4;

    public static class ESTRUCTURA_BD {
        public static final String NOMBRE_BD = "organizate";
        public static final String TAREAS = "tareas";
        public static final String SUBTAREAS = "subtareas";
        public static final String ETIQUETAS = "etiquetas";
        public static final String ACTIVIDADES = "actividades";
    }

    public DataBase(Context context) {
        super(context, ESTRUCTURA_BD.NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_TAREAS);
        db.execSQL(SCRIPT_SUBTAREAS);
        db.execSQL(SCRIPT_ETIQUETAS);
        db.execSQL(SCRIPT_ACTIVIDADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ESTRUCTURA_BD.TAREAS);
        db.execSQL("DROP TABLE IF EXISTS " + ESTRUCTURA_BD.SUBTAREAS);
        db.execSQL("DROP TABLE IF EXISTS " + ESTRUCTURA_BD.ETIQUETAS);
        db.execSQL("DROP TABLE IF EXISTS " + ESTRUCTURA_BD.ACTIVIDADES);
        onCreate(db);
    }

    //Scripts creación tablas
    private static final String SCRIPT_TAREAS = "CREATE TABLE " + ESTRUCTURA_BD.TAREAS + " (" +
            "hecho TEXT NOT NULL, " +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombreTarea TEXT NOT NULL, " +
            "descripcionTarea TEXT, " +
            "fCreacion TEXT NOT NULL, " +
            "tEtiqueta TEXT NOT NULL)";
    private static final String SCRIPT_SUBTAREAS = "CREATE TABLE " + ESTRUCTURA_BD.SUBTAREAS + " (" +
            "hecho TEXT NOT NULL, " +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombreSubtarea TEXT NOT NULL, " +
            "idTarea INTEGER NOT NULL)";
    private static final String SCRIPT_ETIQUETAS = "CREATE TABLE " + ESTRUCTURA_BD.ETIQUETAS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombreEtiqueta TEXT NOT NULL)";
    private static final String SCRIPT_ACTIVIDADES = "CREATE TABLE " + ESTRUCTURA_BD.ACTIVIDADES + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descripcion TEXT NOT NULL, " +
            "fActividad TEXT NOT NULL, " +
            "idTarea INTEGER NOT NULL)";
}