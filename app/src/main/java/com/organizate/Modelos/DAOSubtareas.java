package com.organizate.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.organizate.Base_Datos.DataBase;
import com.organizate.Entidades.VOSubtarea;

import java.util.ArrayList;

public class DAOSubtareas {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DAOSubtareas(Context context) {
        this.context = context;
        DataBase dataBase = new DataBase(context);
        sqLiteDatabase = dataBase.getWritableDatabase();
    }

    //Insert
    public boolean insertSubtarea(VOSubtarea subtarea) {
        boolean vof = true;

        try {
            ContentValues values = new ContentValues();
            values.put("hecho", subtarea.getHecho());
            values.put("nombreSubtarea", subtarea.getNombreSubtarea());
            values.put("idTarea", subtarea.getIdTarea());
            sqLiteDatabase.insert(DataBase.ESTRUCTURA_BD.SUBTAREAS, null, values);
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }

    //Read
    public ArrayList<VOSubtarea> readSubtareas(int idTarea) {
        ArrayList<VOSubtarea> lSubtareas = null;

        try {
            lSubtareas = new ArrayList<>();
            Cursor c = sqLiteDatabase.rawQuery("SELECT hecho, " + BaseColumns._ID + ", nombreSubtarea, idTarea " +
                    "FROM " + DataBase.ESTRUCTURA_BD.SUBTAREAS + " WHERE idTarea = ?", new String[]{String.valueOf(idTarea)});
            if (c.getCount() > 0) {
                while (c.moveToNext() == true) {
                    VOSubtarea subtarea = new VOSubtarea();
                    subtarea.setHecho(c.getString(0));
                    subtarea.setIdentificador(c.getInt(1));
                    subtarea.setNombreSubtarea(c.getString(2));
                    subtarea.setIdTarea(c.getInt(3));
                    lSubtareas.add(subtarea);
                }
            }
        }
        catch (Exception err) {
            lSubtareas = null;
        }

        return lSubtareas;
    }

    //Cambiamos el estado de la subtarea
    public void cambiarEstado(int id, String nEstado) {
        try {
            ContentValues values = new ContentValues();
            values.put("hecho", nEstado);
            sqLiteDatabase.update(DataBase.ESTRUCTURA_BD.SUBTAREAS, values, BaseColumns._ID + " = ?",
                    new String[]{String.valueOf(id)});
        }
        catch (Exception err) {

        }
    }
}
