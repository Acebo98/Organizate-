package com.organizate.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public final static int TEXTO_TAREA = 60;
    public final static int TEXTO_TAG = 20;
    public final static int TEXTO_SUBTAREA = 40;
    public final static String CERRAR = "cerrar";
    public final static String BORRAR = "borrar";
    public final static String MARCAR_HECHO = "marcar_hecho";
    public final static String MARCAR_NO_HECHO = "marcar_no_hecho";
    public final static String HECHO = "s";
    public final static String NO_HECHO = "n";

    //Sacamos la fecha de hoy
    public static String sacarFechaHoy() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}