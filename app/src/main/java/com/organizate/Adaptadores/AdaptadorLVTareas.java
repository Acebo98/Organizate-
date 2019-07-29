package com.organizate.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.organizate.Entidades.VOTag;
import com.organizate.Entidades.VOTarea;
import com.organizate.R;

import java.util.ArrayList;

public class AdaptadorLVTareas extends BaseAdapter {

    private ArrayList<VOTarea> LTareas;
    private Context Context;

    public AdaptadorLVTareas(ArrayList<VOTarea> LTareas, Context context) {
        this.LTareas = LTareas;
        Context = context;
    }

    @Override
    public int getCount() {
        return LTareas.size();
    }

    @Override
    public Object getItem(int position) {
        return LTareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VOTarea tarea = (VOTarea) getItem(position);
        convertView = LayoutInflater.from(Context).inflate(R.layout.item_lv_tarea, null);

        //Textos a mostrar
        TextView labNombre = (TextView) convertView.findViewById(R.id.labLVTareaNombre);
        TextView labTag = (TextView) convertView.findViewById(R.id.labLVTareaTag);
        TextView labFecha = (TextView) convertView.findViewById(R.id.labLVTareaFecha);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.pbLVTask);

        //Aplicamos los textos
        labNombre.setText(tarea.getNombre());
        labTag.setText(tarea.gettEtiqueta());
        labFecha.setText(tarea.getF_Creacion());

        //Imagen
        if (tarea.getHechi().equals("n")) {
            imageView.setImageResource(R.drawable.ic_tasks_black);
        }
        else {
            imageView.setImageResource(R.drawable.done);
        }

        return convertView;
    }
}