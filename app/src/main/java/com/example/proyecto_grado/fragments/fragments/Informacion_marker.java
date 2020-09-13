package com.example.proyecto_grado.fragments.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.proyecto_grado.Clases.Model_iformacion_lugares;
import com.example.proyecto_grado.MapsActivity;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.Lugar;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class Informacion_marker extends PagerAdapter {

    private List<Lugar> informacion_lugares;
    private LayoutInflater layoutInflater;
    private Context context;
    Informacion_marker.Conocerposicion miposicion; //interface
    int posicion;

    public Informacion_marker(List<Lugar> informacion_lugares, Context context, Informacion_marker.Conocerposicion miposicion) {
        this.informacion_lugares = informacion_lugares;
        this.context = context;
        this.miposicion = miposicion;
    }

    @Override
    public int getCount() {
        return informacion_lugares.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = layoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.informacion_markers, container, false);

        ImageView imagen_marker;
        final TextView nombre_lugar, direccion_lugar, division, descripcion_lugar;
        Button agregar;

        imagen_marker = (ImageView) view.findViewById(R.id.imagen_marker);
        nombre_lugar = (TextView) view.findViewById(R.id.nombre_lugar);
        direccion_lugar = (TextView) view.findViewById(R.id.direccion_lugar);
        descripcion_lugar = (TextView) view.findViewById(R.id.descripcion_lugar);
        agregar = (Button) view.findViewById(R.id.agregar);

        nombre_lugar.setText(informacion_lugares.get(position).getNombre_lugar());
        direccion_lugar.setText(informacion_lugares.get(position).getDireccion() + " - " + "Ciclo rutas");
        descripcion_lugar.setText(informacion_lugares.get(position).getDescripcion_lugar());
        final LatLng latLng = new LatLng(informacion_lugares.get(position).getLatitud(), informacion_lugares.get(position).getLongitud());

        if( informacion_lugares.get(position).getId() != 0 ){
            agregar.setVisibility(View.INVISIBLE);
        }else{
            agregar.setVisibility(View.VISIBLE);
        }

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miposicion.posisicionDelMarcador(position, latLng, direccion_lugar.getText().toString());
                posicion = position;
                notifyDataSetChanged();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que quermos cargar del otro lado
            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface Conocerposicion {
        void posisicionDelMarcador(int posicion, LatLng latLng, String direccion_lugar);
    }
}
