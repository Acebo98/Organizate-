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

import com.organizate.Utils.Utils;
import com.organizate.Entidades.VOTag;
import com.organizate.R;
import com.organizate.Notificaciones.CentralizarToasts;

public class DialogoNTag {
    Context context;

    //Controles
    EditText tbNTag;
    Button btnAceptar;
    Button btnCancelar;
    TextView labChars;

    //Interfaz
    private DialogoNTagListener interfaz;
    public interface DialogoNTagListener {
        void AceptarTag(VOTag tag);
    }

    public DialogoNTag(final Context context, DialogoNTagListener actividad) {
        this.interfaz = actividad;
        this.context = context;

        //Configuración del cuadro de díalogo
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialogo_add_tag);

        //Controles
        tbNTag = (EditText) dialog.findViewById(R.id.tbNuevaTag);
        tbNTag.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Utils.TEXTO_TAG)});
        btnAceptar = (Button) dialog.findViewById(R.id.btnAceptarNTag);
        btnCancelar = (Button) dialog.findViewById(R.id.btnCancelarNTag);
        labChars = (TextView) dialog.findViewById(R.id.labCharsTag);

        //Eventos
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
                    interfaz.AceptarTag(new VOTag(tbNTag.getText().toString().trim()));
                    dialog.dismiss();
                }
                else {
                    CentralizarToasts.centralizarToastsCorto(context, context.getString(R.string.introducir_texto));
                }
            }
        });
        tbNTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                labChars.setText(tbNTag.getText().length() + "/" + Utils.TEXTO_TAG);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
    }

    //Datos en el edittext
    private boolean ComprobarCampo() {
        return tbNTag.getText().toString().trim().length() > 0;
    }
}