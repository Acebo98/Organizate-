package com.organizate.Entidades;

import com.organizate.Utils.Utils;

public class VOSubtarea {
    private String Hecho;
    private int Identificador;
    private String NombreSubtarea;
    private int IdTarea;

    public VOSubtarea() {
        this.Hecho = Utils.NO_HECHO;
    }

    //Getter
    public String getHecho() {
        return Hecho;
    }

    public int getIdentificador() {
        return Identificador;
    }

    public String getNombreSubtarea() {
        return NombreSubtarea;
    }

    public int getIdTarea() {
        return IdTarea;
    }

    //Setters
    public void setHecho(String hecho) {
        Hecho = hecho;
    }

    public void setIdentificador(int identificador) {
        Identificador = identificador;
    }

    public void setNombreSubtarea(String nombreSubtarea) {
        NombreSubtarea = nombreSubtarea;
    }

    public void setIdTarea(int idTarea) {
        IdTarea = idTarea;
    }
}