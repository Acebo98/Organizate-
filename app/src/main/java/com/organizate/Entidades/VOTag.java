package com.organizate.Entidades;

public class VOTag {
    private int ID;
    private String nombre;

    //Constructores
    public VOTag() {
    }
    public VOTag(String Nombre) {
        this.nombre = Nombre;
    }

    //Propiedades
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}