package com.organizate.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.organizate.Modelos.DAOEtiquetas;
import com.organizate.R;
import com.organizate.Utils.Utils;
import com.organizate.Notificaciones.CentralizarToasts;

import java.util.ArrayList;

public class DialogoNTarea {
    Context context;

    //Controles
    EditText tbAdd;
    TextView labChars;
    Button btnAceptar;
    Button btnCancelar;
    Spinner spnTag;

    //Interfaz
    private DialogoNTareaListener interfaz;
    public interface DialogoNTareaListener {
        void AceptarTarea(String nTarea, String nTag);
    }

    public DialogoNTarea(final Context context, DialogoNTareaListener actividad) {
        interfaz = actividad;
        this.context = context;

        //Configuración del cuadro de díalogo
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialogo_add_tarea);

        //Controles
        tbAdd = (EditText) dialog.findViewById(R.id.tbNuevaTarea);
        tbAdd.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Utils.TEXTO_TAREA)});
        labChars = (TextView) dialog.findViewById(R.id.labCharsTarea);
        btnAceptar = (Button) dialog.findViewById(R.id.btnAceptarNTarea);
        btnCancelar = (Button) dialog.findViewById(R.id.btnCancelarNTarea);
        spnTag = (Spinner) dialog.findViewById(R.id.spnSeleccionTag);

        //Mostramos las etiquetas en el spinner
        ArrayList<String> lTags = new DAOEtiquetas(context).readTagsList();
        if (lTags != null) {
            spnTag.setAdapter(new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, lTags));
        }

        //Eventos
        tbAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                labChars.setText(tbAdd.getText().length() + "/" + Utils.TEXTO_TAREA);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComprobarCampo() == true) {
                    interfaz.AceptarTarea(tbAdd.getText().toString().trim(), spnTag.getSelectedItem().toString());
                    dialog.dismiss();
                }
                else {
                    CentralizarToasts.centralizarToastsCorto(context, context.getString(R.string.introducir_texto));
                }
            }
        });

        dialog.show();
    }

    //Datos en el edittext
    private boolean ComprobarCampo() {
        return tbAdd.getText().toString().trim().length() > 0;
    }
}