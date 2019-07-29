package com.organizate.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.organizate.Base_Datos.DataBase;
import com.organizate.Entidades.VOActividad;
import com.organizate.Utils.Utils;

import java.util.ArrayList;

public class DAOActividad {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DAOActividad(Context context) {
        DataBase dataBase = new DataBase(context);
        this.context = context;
        sqLiteDatabase = dataBase.getWritableDatabase();
    }

    //Insertamos una actividad
    public void insertActividad(VOActividad actividad) {
        try {
            ContentValues values = new ContentValues();

            values.put("descripcion", actividad.getDescripcionActividad());
            values.put("fActividad", actividad.getFechaActividad());
            values.put("idTarea", actividad.getIdTarea());

            sqLiteDatabase.insert(DataBase.ESTRUCTURA_BD.ACTIVIDADES, null, values);
        }
        catch (Exception err) {
        }
    }

    //Leemos las actividad
    public ArrayList<VOActividad> readActividades(int identificador) {
        ArrayList<VOActividad> lActividades = null;

        try {
            Cursor c = sqLiteDatabase.rawQuery("SELECT " + BaseColumns._ID + ", descripcion, fActividad, idTarea " +
                    "FROM " + DataBase.ESTRUCTURA_BD.ACTIVIDADES + " " +
                    "WHERE idTarea  = ? " +
                    "ORDER BY " + BaseColumns._ID + " DESC", new String[]{String.valueOf(identificador)});
            lActividades = new ArrayList<>();

            if (c.getCount() > 0) {
                while (c.moveToNext() == true) {
                    VOActividad actividad = new VOActividad();

                    actividad.setIdentificador(c.getInt(0));
                    actividad.setDescripcionActividad(c.getString(1));
                    actividad.setFechaActividad(c.getString(2));
                    actividad.setIdTarea(c.getInt(3));

                    lActividades.add(actividad);
                }
            }
        }
        catch (Exception err) {
            lActividades = null;
        }

        return lActividades;
    }
}