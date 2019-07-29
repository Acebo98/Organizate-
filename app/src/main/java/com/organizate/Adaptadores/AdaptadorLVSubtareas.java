package com.organizate.Adaptadores;

import android.content.Context;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.organizate.Entidades.VOSubtarea;
import com.organizate.R;
import com.organizate.Utils.Utils;

import java.util.ArrayList;

public class AdaptadorLVSubtareas extends BaseAdapter {

    private ArrayList<VOSubtarea> LSubtareas;
    private Context Context;

    public AdaptadorLVSubtareas(ArrayList<VOSubtarea> LSubtareas, Context context) {
        this.LSubtareas = LSubtareas;
        Context = context;
    }

    @Override
    public int getCount() {
        return LSubtareas.size();
    }

    @Override
    public Object getItem(int position) {
        return LSubtareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VOSubtarea subtarea = (VOSubtarea) getItem(position);
        convertView = LayoutInflater.from(Context).inflate(R.layout.item_lv_subtarea, null);

        //Texto a mostrar
        TextView labNombre = (TextView) convertView.findViewById(R.id.lablvNombreSubtarea);
        CheckBox cbHecho = (CheckBox) convertView.findViewById(R.id.cbHacerSubtarea);

        //Mostramos los datos
        labNombre.setText(subtarea.getNombreSubtarea());
        cbHecho.setChecked(subtarea.getHecho().equals(Utils.HECHO) ? true : false);

        return convertView;
    }
}
