package com.example.proyecto_grado.entidades;

public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String codigo_acceso;
    private String clave;
    private String correo_electronico;
    private String telefono;
    private String acceso;

    public Usuario(int id, String nombre, String usuario, String codigo_acceso, String clave, String correo_electronico, String telefono, String acceso) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.codigo_acceso = codigo_acceso;
        this.clave = clave;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
        this.acceso = acceso;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigo_acceso() {
        return codigo_acceso;
    }

    public void setCodigo_acceso(String codigo_acceso) {
        this.codigo_acceso = codigo_acceso;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}
