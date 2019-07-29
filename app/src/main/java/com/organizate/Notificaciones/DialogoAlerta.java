package com.organizate.Notificaciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.organizate.R;

public class DialogoAlerta extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        //Datos del diálogo
        Bundle bundle = getArguments();
        String titulo = bundle.getString("TITULO");
        String mensaje = bundle.getString("MENSAJE");

        //Cuerpo del cuadro de diálogo
        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setPositiveButton(getString(R.string.entendido), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    //Mostramos el dialogo
    public static void mostrarDialogo(FragmentManager fragmentManager, String titulo, String mensaje) {
        DialogFragment dialogFragment = new DialogoAlerta();
        Bundle bundle = new Bundle();

        bundle.putString("TITULO", titulo);
        bundle.putString("MENSAJE", mensaje);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(fragmentManager, "error");
    }
}