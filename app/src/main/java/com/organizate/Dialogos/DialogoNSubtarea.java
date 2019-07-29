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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.organizate.Notificaciones.CentralizarToasts;
import com.organizate.R;
import com.organizate.Utils.Utils;

public class DialogoNSubtarea {

    Context context;

    EditText tbNombre;
    TextView labChars;
    Button btnAceptar;
    Button btnCancelar;

    DialogoSubtareaListener interfaz;
    public interface DialogoSubtareaListener {
        void AceptarSubtarea(String nombreSubtarea);
    }

    public DialogoNSubtarea(final Context context, DialogoSubtareaListener actividad) {
        this.context = context;
        this.interfaz = actividad;

        //Configuración del cuadro de díalogo
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialogo_add_subtarea);

        //Controles
        tbNombre = dialog.findViewById(R.id.tbNuevaSubtarea);
        tbNombre.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Utils.TEXTO_SUBTAREA)});
        labChars = dialog.findViewById(R.id.labCharsSubtarea);
        btnAceptar = dialog.findViewById(R.id.btnAceptarNSubtarea);
        btnCancelar = dialog.findViewById(R.id.btnCancelarNSubtarea);

        //Eventos
        tbNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                labChars.setText(tbNombre.getText().length() + "/" + Utils.TEXTO_SUBTAREA);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComprobarCampo() == true) {
                    interfaz.AceptarSubtarea(tbNombre.getText().toString().trim());
                    dialog.dismiss();
                }
                else {
                    CentralizarToasts.centralizarToastsCorto(context, context.getString(R.string.introducir_texto));
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Datos en el edittext
    private boolean ComprobarCampo() {
        return tbNombre.getText().toString().trim().length() > 0;
    }
}