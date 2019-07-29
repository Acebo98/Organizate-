package com.organizate.Entidades;

import com.organizate.Utils.Utils;

public class VOActividad {
    private int Identificador;
    private String DescripcionActividad;
    private String FechaActividad;
    private int IdTarea;

    public VOActividad() {
        this.FechaActividad = Utils.sacarFechaHoy();
    }

    //Getters
    public int getIdentificador() {
        return Identificador;
    }

    public String getDescripcionActividad() {
        return DescripcionActividad;
    }

    public String getFechaActividad() {
        return FechaActividad;
    }

    public int getIdTarea() {
        return IdTarea;
    }

    //Setters
    public void setIdentificador(int identificador) {
        Identificador = identificador;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        DescripcionActividad = descripcionActividad;
    }

    public void setFechaActividad(String fechaActividad) {
        FechaActividad = fechaActividad;
    }

    public void setIdTarea(int idTarea) {
        IdTarea = idTarea;
    }
}