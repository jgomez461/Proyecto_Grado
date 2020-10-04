package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.Imagenes_Recycler_Uris;
import com.example.proyecto_grado.entidades.Lugar;
import com.example.proyecto_grado.entidades.VariablesGlobales;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Informacion_marker extends PagerAdapter {

    private List<Lugar> informacion_lugares;
    private LayoutInflater layoutInflater;
    private Context context;
    private RecyclerView recylcerimagenes_lugares;
    private RecyclerView recylcertiposdeporte;
    Informacion_marker.Conocerposicion miposicion; //interface
    RecyclerViewAdapter_imagenes_lugares adaptador;
    RecyclerViewAdapter_tipos_deporte adapter_tipos_deporte;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    VariablesGlobales variablesGlobales = new VariablesGlobales();
    String variablesDB = variablesGlobales.getUrl_DB();
    List<String> imagenes_marcadores_seleccionados = new ArrayList<>();
    int posicion;

    public Informacion_marker( List<Lugar> informacion_lugares, Context context, Informacion_marker.Conocerposicion miposicion) {
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
        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.informacion_markers, container, false);

        ImageView imagen_marker;
        final TextView nombre_lugar, direccion_lugar, division, descripcion_lugar, tipolugar;
        Button agregar;

        //imagen_marker = (ImageView) view.findViewById(R.id.imagen_marker);
        nombre_lugar = (TextView) view.findViewById(R.id.nombre_lugar);
        direccion_lugar = (TextView) view.findViewById(R.id.direccion_lugar);
        descripcion_lugar = (TextView) view.findViewById(R.id.descripcion_lugar);
        agregar = (Button) view.findViewById(R.id.agregar);
        tipolugar = (TextView) view.findViewById(R.id.tipolugar);
        recylcerimagenes_lugares = (RecyclerView) view.findViewById(R.id.recyvlerimagenes_marker);
        recylcertiposdeporte = (RecyclerView) view.findViewById(R.id.recyclerinformacion_tipodeporte);

        String tipo_lugar = "";
        if( informacion_lugares.get(position).getTipo_lugar_principal() == 1 ){
            tipo_lugar = "Comida";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 2 ){
            tipo_lugar = "Deporte";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 3 ){
            tipo_lugar = "Caminatas";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 4 ){
            tipo_lugar = "Ciclorutas";
        }

        nombre_lugar.setText(informacion_lugares.get(position).getNombre_lugar());
        tipolugar.setText(tipo_lugar);
        direccion_lugar.setText(informacion_lugares.get(position).getDireccion());
        descripcion_lugar.setText(informacion_lugares.get(position).getDescripcion_lugar());
        final LatLng latLng = new LatLng(informacion_lugares.get(position).getLatitud(), informacion_lugares.get(position).getLongitud());

        if( informacion_lugares.get(position).getId() != 0 ){
            agregar.setVisibility(View.GONE);
        }else{
            agregar.setVisibility(View.VISIBLE);
        }


        if( informacion_lugares.get(position).getTipo_lugar_principal() == 2 && informacion_lugares.get(position).getTiposDeporteLugars().size() > 0 ){
            cargarTiposDeporteLugar( position );
        }

        if( informacion_lugares.get(position).getImagenes().size() > 0 ){
            cargarimagenreciclerimagenes_comida( position );
        }
/*
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                miposicion.posisicionDelMarcador(informacion_lugares.get(position).getCodigo(), informacion_lugares.get(position).getId(), latLng, direccion_lugar.getText().toString(), 1);
                notifyDataSetChanged();
                return true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Posición: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Posición: "+ position, Toast.LENGTH_SHORT).show();
                miposicion.posisicionDelMarcador(informacion_lugares.get(position).getCodigo(), informacion_lugares.get(position).getId(), latLng, direccion_lugar.getText().toString(), 2);
                notifyDataSetChanged();
            }
        });*/

        container.addView(view, 0);

        return view;
    }

    private void cargarTiposDeporteLugar( int posicion ) {
        recylcertiposdeporte.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter_tipos_deporte = new RecyclerViewAdapter_tipos_deporte(informacion_lugares.get(posicion).getTiposDeporteLugars(), context);
        recylcertiposdeporte.setAdapter(adapter_tipos_deporte);
        Log.d( "tipo_deporte",  posicion + "y  " +informacion_lugares.get(posicion).getTiposDeporteLugars()+"");
    }

    private void cargarimagenreciclerimagenes_comida( int posicion) {
        recylcerimagenes_lugares.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adaptador = new RecyclerViewAdapter_imagenes_lugares(informacion_lugares.get(posicion).getImagenes(), context);
        recylcerimagenes_lugares.setAdapter(adaptador);
        recylcerimagenes_lugares.setPadding(0,0,30, 0);
        Log.d( "Problem_Type2",  posicion + "y  " + informacion_lugares.get(posicion).getImagenes()+"");
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface Conocerposicion {
        void posisicionDelMarcador(String posicion, int id, LatLng latLng, String direccion_lugar, int codigo_accion);
    }
}
