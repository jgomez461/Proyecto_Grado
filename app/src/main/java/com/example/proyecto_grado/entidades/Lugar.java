package com.example.proyecto_grado.entidades;

import java.util.Date;
import java.util.List;

public class Lugar {

    private int id;
    private String codigo;
    private int usuario;
    private String direccion;
    private String nombre_lugar;
    private String descripcion_lugar;
    private int tipo_lugar;
    private int tipo_lugar_principal;
    private String fecha_creacion;
    private double latitud;
    private double longitud;
    private String codigo2;
    private String direccion2;
    private double latitud2;
    private double longitud2;
    private List<String> imagenes;
    private List<TiposDeporteLugar> tiposDeporteLugars;

    public Lugar(int id, String codigo, int usuario, String direccion, String nombre_lugar, String descripcion_lugar,
                 int tipo_lugar, int tipo_lugar_principal, String fecha_creacion, double latitud, double longitud, String codigo2,
                 String direccion2, double latitud2, double longitud2, List<String> imagenes, List<TiposDeporteLugar> tiposDeporteLugars) {
        this.id = id;
        this.codigo = codigo;
        this.usuario = usuario;
        this.direccion = direccion;
        this.nombre_lugar = nombre_lugar;
        this.descripcion_lugar = descripcion_lugar;
        this.tipo_lugar = tipo_lugar;
        this.tipo_lugar_principal = tipo_lugar_principal;
        this.fecha_creacion = fecha_creacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.codigo2 = codigo2;
        this.direccion2 = direccion2;
        this.latitud2 = latitud2;
        this.longitud2 = longitud2;
        this.imagenes = imagenes;
        this.tiposDeporteLugars = tiposDeporteLugars;
    }

    public Lugar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getTipo_lugar_principal() {
        return tipo_lugar_principal;
    }

    public void setTipo_lugar_principal(int tipo_lugar_principal) {
        this.tipo_lugar_principal = tipo_lugar_principal;
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

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public double getLatitud2() {
        return latitud2;
    }

    public void setLatitud2(double latitud2) {
        this.latitud2 = latitud2;
    }

    public double getLongitud2() {
        return longitud2;
    }

    public void setLongitud2(double longitud2) {
        this.longitud2 = longitud2;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public List<TiposDeporteLugar> getTiposDeporteLugars() {
        return tiposDeporteLugars;
    }

    public void setTiposDeporteLugars(List<TiposDeporteLugar> tiposDeporteLugars) {
        this.tiposDeporteLugars = tiposDeporteLugars;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre_lugar='" + nombre_lugar + '\'' +
                ", tipo_lugar_principal=" + tipo_lugar_principal +
                ", imagenes=" + imagenes.toString() +
                ", tiposDeporteLugars=" + tiposDeporteLugars.toString() +
                '}';
    }
}
