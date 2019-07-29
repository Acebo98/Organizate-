package com.organizate.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.organizate.Adaptadores.AdaptadorLVActividad;
import com.organizate.Entidades.VOActividad;
import com.organizate.Entidades.VOTarea;
import com.organizate.Modelos.DAOActividad;
import com.organizate.Modelos.DAOTareas;
import com.organizate.Notificaciones.DialogoAlerta;
import com.organizate.R;

import java.util.ArrayList;

public class ActividadActivity extends AppCompatActivity {

    ListView listView;

    int identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);

        //Botón de ir atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Identificador
        identificador = getIntent().getExtras().getInt("ID");

        //Listview
        listView = (ListView) findViewById(R.id.lvActividad);

        //Leemos las actividades
        LeerActividades();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Leemos las tareas
    private void LeerActividades() {
        try {
            ArrayList<VOActividad> lActividades = new DAOActividad(getApplicationContext()).readActividades(identificador);
            if (lActividades != null) {
                listView.setAdapter(new AdaptadorLVActividad(lActividades, getApplicationContext()));
            }
            else {
                throw new Exception(getString(R.string.error_lectura));
            }
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.error_lectura));
        }
    }
}