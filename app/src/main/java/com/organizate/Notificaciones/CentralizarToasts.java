package com.organizate.Notificaciones;

import android.content.Context;
import android.widget.Toast;

public class CentralizarToasts {

    //Toast Corto
    public static void centralizarToastsCorto(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    //Toast largo
    public static void centralizarToastsLargo(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }
}