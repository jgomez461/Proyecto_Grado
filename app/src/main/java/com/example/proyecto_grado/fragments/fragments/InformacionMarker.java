package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.Lugar;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class InformacionMarker extends RecyclerView.Adapter<InformacionMarker.SliderViewHolder> {

    private ViewPager2 viewPager2;
    private List<Lugar> informacion_lugares;
    private Context context;
    InformacionMarker.posicion miposicion;

    public InformacionMarker(List<Lugar> informacion_lugares, ViewPager2 viewPager2, Context context, posicion miposicion) {
        this.informacion_lugares = informacion_lugares;
        this.viewPager2 = viewPager2;
        this.context = context;
        this.miposicion = miposicion;
    }

    @NonNull
    @Override
    public InformacionMarker.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion_markers, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InformacionMarker.SliderViewHolder holder, final int position) {
        holder.textViewTitulo.setText(informacion_lugares.get(position).getNombre_lugar());
        holder.textViewTipoLugar.setText(ObtenerTipoDeporte(informacion_lugares.get(position).getTipo_lugar_principal(), position));
        holder.textViewDireccion.setText(informacion_lugares.get(position).getDireccion());
        holder.textViewContenido.setText(informacion_lugares.get(position).getDescripcion_lugar());

        for (int i=0; i<informacion_lugares.size(); i++){
            Log.d("DATA_I", i + informacion_lugares.get(i).getImagenes().size()+"");
            Log.d("DATA_TI", i + informacion_lugares.get(i).getTiposDeporteLugars().size()+"");
        }

        if( informacion_lugares.get(position).getId() != 0 ){
            holder.buttonAgregar.setVisibility(View.GONE);
        }else{
            holder.buttonAgregar.setVisibility(View.VISIBLE);
        }

        if( informacion_lugares.get(position).getTipo_lugar_principal() == 2 && !informacion_lugares.get(position).getTiposDeporteLugars().isEmpty() ){
            cargarTiposDeporteLugar( position, holder.recylcertiposdeporte, informacion_lugares);
        }

        if( !informacion_lugares.get(position).getImagenes().isEmpty() ){
            cargarimagenreciclerimagenes_comida( position, holder.recylcerimagenes_lugares, informacion_lugares);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(informacion_lugares.get(position).getLatitud(), informacion_lugares.get(position).getLongitud());
                miposicion.posicionMarcador(informacion_lugares.get(position).getCodigo(), informacion_lugares.get(position).getId(), latLng,  informacion_lugares.get(position).getDireccion(), 1);
                Toast.makeText(context, "Posición: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String ObtenerTipoDeporte(int tipo_lugar_principal, int position) {
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

        return tipo_lugar;
    }

    @Override
    public int getItemCount() {
        return informacion_lugares.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recylcerimagenes_lugares, recylcertiposdeporte;
        TextView textViewTitulo, textViewContenido, textViewTipoLugar, textViewDireccion;
        Button buttonAgregar;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            recylcerimagenes_lugares = itemView.findViewById(R.id.recyvlerimagenes_marker);
            recylcertiposdeporte = itemView.findViewById(R.id.recyclerinformacion_tipodeporte);
            textViewTitulo = itemView.findViewById(R.id.nombre_lugar);
            textViewTipoLugar = itemView.findViewById(R.id.tipolugar);
            textViewContenido = itemView.findViewById(R.id.descripcion_lugar);
            textViewDireccion = itemView.findViewById(R.id.direccion_lugar);
            buttonAgregar = itemView.findViewById(R.id.agregar);
        }
    }

    RecyclerViewAdapter_imagenes_lugares adaptador;
    RecyclerViewAdapter_tipos_deporte adapter_tipos_deporte;
    private void cargarTiposDeporteLugar( int posicion, RecyclerView recylcertiposdeporte, List<Lugar> informacion_lugares) {
        recylcertiposdeporte.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter_tipos_deporte = new RecyclerViewAdapter_tipos_deporte(informacion_lugares.get(posicion).getTiposDeporteLugars(), context);
        recylcertiposdeporte.setAdapter(adapter_tipos_deporte);
        Log.d( "tipo_deporte",  posicion + "y  " +informacion_lugares.get(posicion).getTiposDeporteLugars()+"");
    }

    private void cargarimagenreciclerimagenes_comida( int posicion, RecyclerView recylcerimagenes_lugares, List<Lugar> informacion_lugares) {
        recylcerimagenes_lugares.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adaptador = new RecyclerViewAdapter_imagenes_lugares(informacion_lugares.get(posicion).getImagenes(), context);
        recylcerimagenes_lugares.setAdapter(adaptador);
        recylcerimagenes_lugares.setPadding(0,0,30, 0);
        Log.d( "Problem_Type2",  posicion + "y  " + informacion_lugares.get(posicion).getImagenes()+"");
    }

    public interface posicion{
        void posicionMarcador(String posicion, int id, LatLng latLng, String direccion_lugar, int codigo_accion);
    }
}
