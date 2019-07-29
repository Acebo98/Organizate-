package com.organizate.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.organizate.Actividades.MainActivity;
import com.organizate.Actividades.TareaActivity;
import com.organizate.Adaptadores.AdaptadorLVTags;
import com.organizate.Adaptadores.AdaptadorLVTareas;
import com.organizate.Entidades.VOTag;
import com.organizate.Entidades.VOTarea;
import com.organizate.Modelos.DAOTareas;
import com.organizate.Notificaciones.DialogoAlerta;
import com.organizate.R;
import com.organizate.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView lvTareas;

    public TareasFragment() {
        // Required empty public constructor
    }

    public static TareasFragment newInstance() {
        TareasFragment fragment = new TareasFragment();
        return fragment;
    }

    //Lectura de datos
    public void leerTareas() {
        try {
            if (this.isAdded() == true) {
                ArrayList<VOTarea> lTareas = new DAOTareas(getContext()).readTareas(Utils.NO_HECHO);
                if (lTareas != null) {
                    AdaptadorLVTareas adaptadorLVTareas = new AdaptadorLVTareas(lTareas, getActivity().getApplicationContext());
                    lvTareas.setAdapter(new AdaptadorLVTareas(lTareas, getContext()));
                } else {
                    throw new Exception(getString(R.string.error_lectura));
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        //Listview
        lvTareas = (ListView) view.findViewById(R.id.lvTareas);
        lvTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VOTarea tarea = (VOTarea) lvTareas.getAdapter().getItem(position);
                Intent intent = new Intent((MainActivity)getActivity(), TareaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", tarea.getIdentificador());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Leemos los datos
        leerTareas();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Interfaz
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String tarea);
    }
}