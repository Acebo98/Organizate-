package com.organizate.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.organizate.Base_Datos.DataBase;
import com.organizate.Entidades.VOTag;
import com.organizate.R;

import java.util.ArrayList;

public class DAOEtiquetas {
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public DAOEtiquetas(Context context) {
        DataBase dataBase = new DataBase(context);
        this.context = context;
        sqLiteDatabase = dataBase.getWritableDatabase();
    }

    //Insert
    public void insertarEtiqueta(VOTag tag) throws Exception {
        ContentValues values = null;

        try {
            if (ExistirEtiqueta(tag) == true) {
                values = new ContentValues();

                //Datos
                values.put("nombreEtiqueta", tag.getNombre());
                sqLiteDatabase.insert(DataBase.ESTRUCTURA_BD.ETIQUETAS, null, values);
            }
            else {
                throw new Exception(context.getString(R.string.tag_existente));
            }
        }
        catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }

    //Read
    public ArrayList<VOTag> readTags() {
        ArrayList<VOTag> lTags = null;

        try {
            Cursor c = sqLiteDatabase.rawQuery("SELECT " + BaseColumns._ID + ", nombreEtiqueta " +
                    "FROM " + DataBase.ESTRUCTURA_BD.ETIQUETAS, null);

            //Sacamos los datos
            lTags = new ArrayList<>();
            if (c.getCount() > 0) {
                while (c.moveToNext() == true) {
                    VOTag tag = new VOTag(c.getString(1));
                    tag.setID(c.getInt(0));
                    lTags.add(tag);
                }
            }
        }
        catch (Exception err) {
            lTags = null;
        }

        return lTags;
    }

    //Numero de etiquetas almacenadas
    public int sacarNumEtiquetas() {
        int num = 0;

        try {
            ArrayList<VOTag> tags = this.readTags();
            if (tags == null) {
                throw new Exception();
            }
            else {
                num = tags.size();
            }
        }
        catch (Exception err) {
            num = -1;
        }

        return num;
    }

    //Read pero devolviendo una array
    public ArrayList<String> readTagsList() {
        ArrayList<String> listaNombres = null;

        try {
            ArrayList<VOTag> lTags = this.readTags();
            if (lTags == null) {
                throw new Exception();
            }
            else {
                listaNombres = new ArrayList<>();
                for (VOTag tag : lTags) {
                    listaNombres.add(tag.getNombre());
                }
            }
        }
        catch (Exception err) {
            listaNombres = null;
        }

        return listaNombres;
    }

    //Comprobamos si existe una etiqueta
    private boolean ExistirEtiqueta(VOTag tag) {
        boolean vof = true;

        try {
            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + DataBase.ESTRUCTURA_BD.ETIQUETAS + " " +
                    "WHERE nombreEtiqueta = ?", new String[] {tag.getNombre()});
            if (c.getCount() > 0) {
                vof = false;
            }
        }
        catch (Exception err) {
            vof = false;
        }

        return vof;
    }
}
