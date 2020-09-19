package com.example.proyecto_grado.Clases;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.MapsActivity;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.Register_users;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VariablesGlobales {

    public String url_DB = "https://f9f49f62ba09.ngrok.io/DB_proyecto_grado/";
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

}
