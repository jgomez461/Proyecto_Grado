package com.example.proyecto_grado.complementos;

import android.net.Uri;

public class Imagenes_Recycler_Uris {

    // con logt se saca esto
    private static final String TAG = "Imagenes_Recycler_Uris";

    private Uri imagenuri;

    public Imagenes_Recycler_Uris(Uri imagenuri) {
        this.imagenuri = imagenuri;
    }

    public Uri getImagenuri() {
        return imagenuri;
    }

    public void setImagenuri(Uri imagenuri) {
        this.imagenuri = imagenuri;
    }
}
