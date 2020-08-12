package com.example.proyecto_grado;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Informacion_marker implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    private TextView nombre;
    private TextView placas;
    private TextView estado;
    private ImageButton delete_marker;
    

    public Informacion_marker(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public Informacion_marker(ImageButton delete_marker){
        this.delete_marker = delete_marker;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.informacion_markers, null);
        final String[] info = marker.getTitle().split("&");
        String url = marker.getSnippet();

        nombre = (TextView) v.findViewById(R.id.info_window_nombre);
        placas = (TextView) v.findViewById(R.id.info_window_placas);
        estado = (TextView) v.findViewById(R.id.info_window_estado);


        nombre.setText(marker.getTitle());
        placas.setText("Codigo postal: " + marker.getSnippet());
        estado.setText("id: " + marker.getId());

        return v;
    }


    @Override
    public void onInfoWindowClick(final Marker marker) {

        View v = inflater.inflate(R.layout.informacion_markers, null);

        Toast.makeText(v.getContext(),"sikas", Toast.LENGTH_SHORT).show();


        delete_marker = (ImageButton) v.findViewById(R.id.eliminar_marcador);

        delete_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "le dickcnslk", Toast.LENGTH_SHORT).show();
                marker.setVisible(false);

            }
        });

    }

}
