package com.organizate.Actividades;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.organizate.Adaptadores.AdaptadorLVSubtareas;
import com.organizate.Dialogos.DialogoConfirmacion;
import com.organizate.Dialogos.DialogoNSubtarea;
import com.organizate.Entidades.VOActividad;
import com.organizate.Entidades.VOSubtarea;
import com.organizate.Entidades.VOTarea;
import com.organizate.Modelos.DAOActividad;
import com.organizate.Modelos.DAOSubtareas;
import com.organizate.Modelos.DAOTareas;
import com.organizate.Notificaciones.CentralizarToasts;
import com.organizate.Notificaciones.DialogoAlerta;
import com.organizate.R;
import com.organizate.Utils.FrasesActividad;
import com.organizate.Utils.Utils;

import java.util.ArrayList;

public class TareaActivity extends AppCompatActivity implements DialogoConfirmacion.MiDialogListener,
        DialogoNSubtarea.DialogoSubtareaListener {

    TextView labNombre;
    EditText tbDescripcion;
    ListView lvSubtareas;
    Button btnGuardar;
    Button btnSubtarea;

    VOTarea tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        //Botón de ir atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Controles
        labNombre = (TextView) findViewById(R.id.labNombreTarea);
        tbDescripcion = (EditText) findViewById(R.id.tbDescripcionTarea);
        lvSubtareas = (ListView) findViewById(R.id.lvSubtareas);
        btnGuardar = (Button) findViewById(R.id.btnAceptarTarea);
        btnSubtarea = (Button) findViewById(R.id.btnAñadirSubtarea);

        //Guardamos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean vof = new DAOTareas(getApplicationContext()).modificarDescripcion(tarea.getIdentificador(),
                        tbDescripcion.getText().toString().trim());
                if (vof == false) {
                    DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.ocurrir_error));
                }
                else {
                    //Registro de la actividad
                    VOActividad actividad = new VOActividad();
                    actividad.setIdTarea(tarea.getIdentificador());
                    actividad.setDescripcionActividad(FrasesActividad.NUEVA_DESCRIPCION);
                    new DAOActividad(getApplicationContext()).insertActividad(actividad);

                    CentralizarToasts.centralizarToastsCorto(getApplicationContext(), getString(R.string.descripcion_cambiada));
                }
            }
        });
        btnSubtarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogoNSubtarea(TareaActivity.this, TareaActivity.this);
            }
        });

        //Leemos la tarea
        int identificador = getIntent().getExtras().getInt("ID");
        tarea = new DAOTareas(getApplicationContext()).readTarea(identificador);
        if (tarea != null) {
            labNombre.setText(tarea.getNombre());
            tbDescripcion.setText(tarea.getDescriocionTarea());
        }
        else {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.error_lectura));
        }

        //Listview
        lvSubtareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VOSubtarea subtarea = (VOSubtarea) lvSubtareas.getAdapter().getItem(position);
                CheckBox cbHecho = (CheckBox) findViewById(R.id.cbHacerSubtarea);

                //Realizamos el cambio
                new DAOSubtareas(getApplicationContext()).cambiarEstado(subtarea.getIdentificador(),
                        cbHecho.isChecked() ? Utils.HECHO : Utils.NO_HECHO);
            }
        });

        //Leemos los datos
        LeerSubtareas();
    }

    //Leemos de la base de datos
    private void LeerSubtareas() {
        try {
            ArrayList<VOSubtarea> lSubtareas = new DAOSubtareas(getApplicationContext()).readSubtareas(tarea.getIdentificador());
            if (lSubtareas != null) {
                lvSubtareas.setAdapter(new AdaptadorLVSubtareas(lSubtareas, getApplicationContext()));
            }
            else {
                throw new Exception(getString(R.string.ocurrir_error));
            }
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), err.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (tarea.getHechi().equals(Utils.NO_HECHO)) {
            getMenuInflater().inflate(R.menu.menu_tarea, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_tarea_hecho, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }
            break;
            case R.id.itemHecho: {
                DialogoConfirmacion dialogoConfirmacion = new DialogoConfirmacion();
                Bundle bundle = new Bundle();

                bundle.putString("TITULO", getString(R.string.confirmación));
                bundle.putString("MENSAJE", getString(R.string.confirmar_hecho));
                dialogoConfirmacion.setArguments(bundle);

                dialogoConfirmacion.show(getSupportFragmentManager(), Utils.MARCAR_HECHO);
            }
            break;
            case R.id.itemNoHecho: {
                DialogoConfirmacion dialogoConfirmacion = new DialogoConfirmacion();
                Bundle bundle = new Bundle();

                bundle.putString("TITULO", getString(R.string.confirmación));
                bundle.putString("MENSAJE", getString(R.string.confirmar_no_hecho));
                dialogoConfirmacion.setArguments(bundle);

                dialogoConfirmacion.show(getSupportFragmentManager(), Utils.MARCAR_NO_HECHO);
            }
            break;
            case R.id.itemModificar: {

            }
            break;
            case R.id.itemRemove: {
                DialogoConfirmacion dialogoConfirmacion = new DialogoConfirmacion();
                Bundle bundle = new Bundle();

                bundle.putString("TITULO", getString(R.string.confirmación));
                bundle.putString("MENSAJE", getString(R.string.confirmar_borrar));
                dialogoConfirmacion.setArguments(bundle);

                dialogoConfirmacion.show(getSupportFragmentManager(), Utils.BORRAR);
            }
            break;
            case R.id.itemActividad: {
                Intent intent = new Intent(TareaActivity.this, ActividadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", tarea.getIdentificador());
                intent.putExtras(bundle);
                startActivity(intent);
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog.getTag().equals(Utils.MARCAR_HECHO)) {
            boolean vof = new DAOTareas(getApplicationContext()).cambiarEstado(tarea.getIdentificador(), Utils.HECHO);
            if (vof == false) {
                DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.ocurrir_error));
            }
            else {
                //Registro de la actividad
                VOActividad actividad = new VOActividad();
                actividad.setIdTarea(tarea.getIdentificador());
                actividad.setDescripcionActividad(FrasesActividad.PASAR_HECHA);
                new DAOActividad(getApplicationContext()).insertActividad(actividad);

                finish();
            }
        }
        else if (dialog.getTag().equals(Utils.MARCAR_NO_HECHO)) {
            boolean vof = new DAOTareas(getApplicationContext()).cambiarEstado(tarea.getIdentificador(), Utils.NO_HECHO);
            if (vof == false) {
                DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.ocurrir_error));
            }
            else {
                //Registro de la actividad
                VOActividad actividad = new VOActividad();
                actividad.setIdTarea(tarea.getIdentificador());
                actividad.setDescripcionActividad(FrasesActividad.PASAR_NO_HECHA);
                new DAOActividad(getApplicationContext()).insertActividad(actividad);

                finish();
            }
        }
        else if (dialog.getTag().equals(Utils.BORRAR)) {
            boolean vof = new DAOTareas(getApplicationContext()).deleteTarea(tarea.getIdentificador());
            if (vof == false) {
                DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), getString(R.string.ocurrir_error));
            }
            else {
                CentralizarToasts.centralizarToastsCorto(getApplicationContext(), getString(R.string.tarea_borrada));
                finish();
            }
        }
    }

    @Override
    public void AceptarSubtarea(String nombreSubtarea) {
        try {
            VOSubtarea subtarea = new VOSubtarea();
            subtarea.setIdTarea(tarea.getIdentificador());
            subtarea.setNombreSubtarea(nombreSubtarea);

            //Insertamos
            boolean vof = new DAOSubtareas(getApplicationContext()).insertSubtarea(subtarea);
            if (vof == false) {
                throw new Exception(getString(R.string.ocurrir_error));
            }
            else {
                //Registro de la subtarea
                VOActividad actividad = new VOActividad();
                actividad.setIdTarea(tarea.getIdentificador());
                actividad.setDescripcionActividad(FrasesActividad.NUEVA_SUBTAREA + " \"" + subtarea.getNombreSubtarea() + "\"");
                new DAOActividad(getApplicationContext()).insertActividad(actividad);

                //Refrescamos la lista
                LeerSubtareas();
            }
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), getString(R.string.ocurrir_error), err.getMessage());
        }
    }
}