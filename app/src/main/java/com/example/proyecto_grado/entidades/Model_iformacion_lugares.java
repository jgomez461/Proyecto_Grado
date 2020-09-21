package com.example.proyecto_grado.entidades;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Model_iformacion_lugares {

    private int imagen;
    private int titulo;
    private int contenido;

    public Model_iformacion_lugares(int imagen, int titulo, int contenido) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

}
