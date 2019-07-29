package com.organizate.Entidades;

import com.organizate.Utils.Utils;

public class VOTarea {
    private String Hechi;
    private int Identificador;
    private String Nombre;
    private String DescriocionTarea;
    private String f_Creacion;
    private String tEtiqueta;

    public VOTarea() {
        this.Hechi = Utils.NO_HECHO;
    }

    //Getters
    public String getHechi() {
        return Hechi;
    }

    public int getIdentificador() {
        return Identificador;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDescriocionTarea() {
        return DescriocionTarea;
    }

    public String getF_Creacion() {
        return f_Creacion;
    }

    public String gettEtiqueta() {
        return tEtiqueta;
    }

    //Setters
    public void setHechi(String hechi) {
        Hechi = hechi;
    }

    public void setIdentificador(int identificador) {
        Identificador = identificador;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setDescriocionTarea(String descriocionTarea) {
        DescriocionTarea = descriocionTarea;
    }

    public void setF_Creacion(String f_Creacion) {
        this.f_Creacion = f_Creacion;
    }

    public void settEtiqueta(String tEtiqueta) {
        this.tEtiqueta = tEtiqueta;
    }
}