package com.example.proyecto_grado.Clases;

public class VariablesGlobales {

    public String url_DB = "https://cf9fb0c2ef16.ngrok.io/DB_proyecto_grado/";

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
}
