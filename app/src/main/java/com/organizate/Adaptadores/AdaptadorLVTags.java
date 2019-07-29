package com.organizate.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.organizate.Entidades.VOTag;
import com.organizate.R;

import java.util.ArrayList;

public class AdaptadorLVTags extends BaseAdapter {

    private ArrayList<VOTag> LTags;
    private Context Context;

    public AdaptadorLVTags(ArrayList<VOTag> LTags, Context context) {
        this.LTags = LTags;
        Context = context;
    }

    @Override
    public int getCount() {
        return LTags.size();
    }

    @Override
    public Object getItem(int position) {
        return LTags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VOTag tag = (VOTag) getItem(position);
        convertView = LayoutInflater.from(Context).inflate(R.layout.item_lv_tag, null);

        //Texto a mostrar
        TextView tvTag = (TextView) convertView.findViewById(R.id.labLVTag);
        tvTag.setText(tag.getNombre());

        return convertView;
    }
}