package com.organizate.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.organizate.Adaptadores.AdaptadorLVTags;
import com.organizate.Entidades.VOTag;
import com.organizate.Modelos.DAOEtiquetas;
import com.organizate.Notificaciones.DialogoAlerta;
import com.organizate.R;
import com.organizate.Notificaciones.CentralizarToasts;

import java.util.ArrayList;

public class EtiquetasFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Controles
    ListView lvEtiquetas;

    public EtiquetasFragment() {
        // Required empty public constructor
    }

    public static EtiquetasFragment newInstance() {
        EtiquetasFragment fragment = new EtiquetasFragment();
        return fragment;
    }

    //Leemos de la base de datos
    public void leerEtiquetas() {
        try {
            if (this.isAdded() == true) {
                ArrayList<VOTag> lTags = new DAOEtiquetas(getActivity().getApplicationContext()).readTags();
                if (lTags != null) {
                    lvEtiquetas.setAdapter(new AdaptadorLVTags(lTags, getActivity().getApplicationContext()));
                } else {
                    throw new Exception(getString(R.string.ocurrir_error));
                }
            }
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getFragmentManager(), getString(R.string.ocurrir_error), err.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_etiquetas, container, false);

        //Controles
        lvEtiquetas = (ListView) view.findViewById(R.id.lvEtiquetas);

        //Lectura de los datos
        leerEtiquetas();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String tarea);
    }
}