package com.organizate.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.organizate.Entidades.VOActividad;
import com.organizate.Entidades.VOTag;
import com.organizate.R;

import java.util.ArrayList;

public class AdaptadorLVActividad extends BaseAdapter {
    private ArrayList<VOActividad> lActividad;
    private Context context;

    public AdaptadorLVActividad(ArrayList<VOActividad> lActividad, Context context) {
        this.lActividad = lActividad;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lActividad.size();
    }

    @Override
    public Object getItem(int position) {
        return lActividad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VOActividad actividad = (VOActividad) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_actividad, null);

        //Texto a mostrar
        TextView labDescripcion = (TextView) convertView.findViewById(R.id.labDescripcionActividad);
        TextView labFecha = (TextView) convertView.findViewById(R.id.labFechaActividad);

        //Mostramos el texto
        labDescripcion.setText(actividad.getDescripcionActividad());
        labFecha.setText(actividad.getFechaActividad());

        return convertView;
    }
}