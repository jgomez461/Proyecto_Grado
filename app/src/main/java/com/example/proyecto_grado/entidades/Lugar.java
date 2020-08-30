package com.example.proyecto_grado.entidades;

import java.util.Date;

public class Lugar {

    private int id;
    private int usuario;
    private String direccion;
    private String nombre_lugar;
    private String descripcion_lugar;
    private int tipo_lugar;
    private String fecha_creacion;
    private double latitud;
    private double longitud;

    public Lugar(int id, int usuario, String direccion, String nombre_lugar, String descripcion_lugar, int tipo_lugar, String fecha_creacion, double latitud, double longitud) {
        this.id = id;
        this.usuario = usuario;
        this.direccion = direccion;
        this.nombre_lugar = nombre_lugar;
        this.descripcion_lugar = descripcion_lugar;
        this.tipo_lugar = tipo_lugar;
        this.fecha_creacion = fecha_creacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Lugar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }

    public String getDescripcion_lugar() {
        return descripcion_lugar;
    }

    public void setDescripcion_lugar(String descripcion_lugar) {
        this.descripcion_lugar = descripcion_lugar;
    }

    public int getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(int tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
