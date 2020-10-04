package com.example.proyecto_grado.entidades;

public class TiposDeporteLugar {

    private int id_lugar;
    private String codigo_lugar;
    private int id_tipo_deporte;
    private String nombre_tipo_deporte;

    public TiposDeporteLugar(int id_lugar, String codigo_lugar, int id_tipo_deporte, String nombre_tipo_deporte) {
        this.id_lugar = id_lugar;
        this.codigo_lugar = codigo_lugar;
        this.id_tipo_deporte = id_tipo_deporte;
        this.nombre_tipo_deporte = nombre_tipo_deporte;
    }

    public TiposDeporteLugar() {
    }

    public int getId_lugar() {
        return id_lugar;
    }

    public void setId_lugar(int id_lugar) {
        this.id_lugar = id_lugar;
    }

    public String getCodigo_lugar() {
        return codigo_lugar;
    }

    public void setCodigo_lugar(String codigo_lugar) {
        this.codigo_lugar = codigo_lugar;
    }

    public int getId_tipo_deporte() {
        return id_tipo_deporte;
    }

    public void setId_tipo_deporte(int id_tipo_deporte) {
        this.id_tipo_deporte = id_tipo_deporte;
    }

    public String getNombre_tipo_deporte() {
        return nombre_tipo_deporte;
    }

    public void setNombre_tipo_deporte(String nombre_tipo_deporte) {
        this.nombre_tipo_deporte = nombre_tipo_deporte;
    }

    @Override
    public String toString() {
        return "TiposDeporteLugar{" +
                "codigo_lugar='" + codigo_lugar + '\'' +
                ", nombre_tipo_deporte='" + nombre_tipo_deporte + '\'' +
                '}';
    }
}
