package com.organizate.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.TextView;

import com.organizate.Base_Datos.DataBase;
import com.organizate.Entidades.VOTarea;
import com.organizate.R;

import java.util.ArrayList;

public class DAOTareas {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DAOTareas(Context context) {
        this.context = context;
        DataBase dataBase = new DataBase(context);
        sqLiteDatabase = dataBase.getWritableDatabase();
    }

    //Insert
    public void insertTarea(VOTarea tarea) throws Exception {
        try {
            if (comprobarExistente(tarea.getNombre()) == true) {
                ContentValues values = new ContentValues();

                //Valores
                values.put("hecho", tarea.getHechi());
                values.put("nombreTarea", tarea.getNombre());
                values.put("fCreacion", tarea.getF_Creacion());
                values.put("tEtiqueta", tarea.gettEtiqueta());

                sqLiteDatabase.insert(DataBase.ESTRUCTURA_BD.TAREAS, null, values);
            }
            else {
                throw new Exception(context.getString(R.string.tarea_repetida));
            }
        }
        catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }

    //Comprobamos que exista ya una tarea insertada
    public boolean comprobarExistente(String nombre) {
        boolean vof = true;

        try {
            int cuantos = (sqLiteDatabase.rawQuery("SELECT * FROM " + DataBase.ESTRUCTURA_BD.TAREAS + " " +
                    "WHERE nombreTarea = ?", new String[]{nombre})).getCount();
            if (cuantos > 0) {
                vof = false;
            }
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }

    //Read
    public ArrayList<VOTarea> readTareas(String estado) {
        ArrayList<VOTarea> lTareas = null;

        try {
            lTareas = new ArrayList<>();

            //Lectura
            Cursor c = sqLiteDatabase.rawQuery("SELECT hecho, " + BaseColumns._ID + ", nombreTarea, " +
                    "fCreacion, tEtiqueta FROM " + DataBase.ESTRUCTURA_BD.TAREAS + " " +
                    "WHERE hecho = ?", new String[] {estado});

            if (c.getCount() > 0) {
                while (c.moveToNext() == true) {
                    VOTarea tarea = new VOTarea();

                    tarea.setHechi(c.getString(0));
                    tarea.setIdentificador(c.getInt(1));
                    tarea.setNombre(c.getString(2));
                    tarea.setF_Creacion(c.getString(3));
                    tarea.settEtiqueta(c.getString(4));

                    lTareas.add(tarea);
                }
            }
        }
        catch (Exception err) {
            lTareas = null;
        }

        return lTareas;
    }

    //Read de UNA tarea
    public VOTarea readTarea(int identificador) {
        VOTarea tarea = null;

        try {
            tarea = new VOTarea();

            //Query
            Cursor c = sqLiteDatabase.rawQuery("SELECT hecho, " + BaseColumns._ID + ", nombreTarea, descripcionTarea, " +
                    "fCreacion, tEtiqueta FROM " + DataBase.ESTRUCTURA_BD.TAREAS + " " +
                    "WHERE " + BaseColumns._ID + " = ?", new String[] {String.valueOf(identificador)});
            if (c.getCount() > 0) {
                c.moveToNext();
                tarea.setHechi(c.getString(0));
                tarea.setIdentificador(c.getInt(1));
                tarea.setNombre(c.getString(2));
                tarea.setDescriocionTarea(c.getString(3));
                tarea.setF_Creacion(c.getString(4));
                tarea.settEtiqueta(c.getString(5));
            }
        }

        catch (Exception err) {
            tarea = null;
        }

        return tarea;
    }

    //Delete
    public boolean deleteTarea(int identificador) {
        boolean vof = true;

        try {
            sqLiteDatabase.delete(DataBase.ESTRUCTURA_BD.TAREAS, BaseColumns._ID + " = ?",
                    new String[]{String.valueOf(identificador)});
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }

    //Modificar la descripcion
    public boolean modificarDescripcion(int identificador, String descripcion) {
        boolean vof = true;

        try {
            ContentValues values = new ContentValues();
            values.put("descripcionTarea", descripcion);
            sqLiteDatabase.update(DataBase.ESTRUCTURA_BD.TAREAS, values, BaseColumns._ID + " = ?",
                    new String[] {String.valueOf(identificador)});
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }

    //Cambiamos el estado de la tarea
    public boolean cambiarEstado(int identificador, String estado) {
        boolean vof = true;

        try {
            ContentValues values = new ContentValues();
            values.put("hecho", estado);
            sqLiteDatabase.update(DataBase.ESTRUCTURA_BD.TAREAS, values, BaseColumns._ID + " = ?",
                    new String[] {String.valueOf(identificador)});
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }

    //Sacamos el identificador de una tarea a partir de su nombre
    public int sacarIdTarea(String nombre) {
        int id = -1;

        try {
            Cursor c = sqLiteDatabase.rawQuery("SELECT " + BaseColumns._ID + " " +
                    "FROM " + DataBase.ESTRUCTURA_BD.TAREAS + " " +
                    "WHERE nombreTarea = ?", new String[]{nombre});
            if (c.getCount() > 0) {
                c.moveToNext();
                id = c.getInt(0);
            }
        }
        catch (Exception err) {
            id = -1;
        }

        return id;
    }
}