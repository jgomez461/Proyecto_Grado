package com.example.proyecto_grado.entidades;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class VariablesGlobales {

    //clave de google maps -> jgomezp97
    //AIzaSyBZfPecjBnm2jS-pZ5DpJVJOAFcm08nkSE
    public String url_DB = "https://f59ec29c9710.ngrok.io/DB_proyecto_grado/";
    char [] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'W', 'x', 'y', 'z' };

    public VariablesGlobales(String url_DB) {
        this.url_DB = url_DB;
    }

    public VariablesGlobales() {
    }

    public String getUrl_DB() {
        return url_DB;
    }

    public void setUrl_DB(String url_DB) {
        this.url_DB = url_DB;
    }

    public void setAlertDialog(int titulo, int contendio, int boton, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(contendio);
        builder.setPositiveButton(boton, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public LatLng buscarLatLng( String direccion, Context context ){
        LatLng latLng = null;
        List<Address> listadireccion = null;

        if( !TextUtils.isEmpty(direccion) ){
            Geocoder geocoder = new Geocoder(context);
            boolean bander = false;
            try {
                listadireccion = geocoder.getFromLocationName(direccion, 6);

                if (listadireccion != null) {
                    for (int i = 0; i < listadireccion.size(); i++) {
                        bander = true;
                        Address useradres = listadireccion.get(i);
                        latLng = new LatLng(useradres.getLatitude(), useradres.getLongitude());
                    }

                    if (bander == false) {
                        latLng = null;
                    }
                } else {
                    latLng = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return latLng;
    }

    public String generate_code_random(int size) {
        String number = "";

        for (int i = 1; i <= size/2 ; i++){
            int Ra = (int)(Math.random()*9);
            number += String.valueOf(Ra);
            number += letters[(int)(Math.random()*26)];
        }
        return number;
    }

    public String encodeurl( String variable ){
        String url = "";
        for( int i=0; i< variable.length(); i++ ){
            if( variable.charAt(i) == '#' ){
                url += "::::";
            }else if( variable.charAt(i) == '%' ){
                url += "----";
            }else if( variable.charAt(i) == '&' ){
                url += ";;;;";
            }else{
                url += variable.charAt(i);
            }
        }
        return url;
    }

    public static class Model_iformacion_lugares {

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
}
