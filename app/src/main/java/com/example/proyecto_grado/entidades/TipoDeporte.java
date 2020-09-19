package com.example.proyecto_grado.entidades;

public class TipoDeporte {

    private int id;
    private int id_tipo_lugar;
    private String nombre;

    public TipoDeporte(int id, int id_tipo_lugar, String nombre) {
        this.id = id;
        this.id_tipo_lugar = id_tipo_lugar;
        this.nombre = nombre;
    }

    public TipoDeporte(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tipo_lugar() {
        return id_tipo_lugar;
    }

    public void setId_tipo_lugar(int id_tipo_lugar) {
        this.id_tipo_lugar = id_tipo_lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
