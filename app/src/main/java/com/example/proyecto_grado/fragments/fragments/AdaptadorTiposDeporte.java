package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.FiltrosMarcadores_Mapsactivity_principal;
import com.example.proyecto_grado.entidades.TipoDeporte;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorTiposDeporte extends RecyclerView.Adapter<AdaptadorTiposDeporte.CategoriaViewHolder> {

    Context context;
    List<TipoDeporte> listatipodeporte;
    List<Boolean> seleccionados = new ArrayList<>();
    Conocerposicion miposicion; //interface
    int posicion;
    List<Integer> lista_deporte_seleccionados;
    boolean bandera = false;
    List<Integer> lista_secundaria = new ArrayList<>();


    public AdaptadorTiposDeporte(Context context, List<TipoDeporte> listatipodeporte, Conocerposicion miposicion,  List<Integer> lista_deporte_seleccionados ) {
        this.context = context;
        this.listatipodeporte = listatipodeporte;
        this.miposicion = miposicion;
        this.lista_deporte_seleccionados = lista_deporte_seleccionados;
    }

    @NonNull
    @Override
    public AdaptadorTiposDeporte.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tipos_deporte, null, false);
        return new AdaptadorTiposDeporte.CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorTiposDeporte.CategoriaViewHolder holder, final int position) {
        holder.textofiltro.setText(listatipodeporte.get(position).getNombre());

        holder.textofiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bandera = false;
                int posicicion_arreglo = -12;
                for (int i=0; i<lista_deporte_seleccionados.size(); i++){
                    if( lista_deporte_seleccionados.get(i).equals(listatipodeporte.get(position).getId()) ){
                        bandera = true;
                        posicicion_arreglo = i;
                        break;
                    }
                }
                if( !bandera && posicicion_arreglo == -12 ){
                    lista_deporte_seleccionados.add(listatipodeporte.get(position).getId());
                    holder.textofiltro.setBackgroundColor(Color.parseColor("#F48403"));
                    holder.textofiltro.setTextColor(Color.WHITE);
                    holder.textofiltro.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_tipo_deporte, 0, 0, 0);
                }else{
                    lista_deporte_seleccionados.remove(posicicion_arreglo);
                    holder.textofiltro.setBackgroundColor(Color.parseColor("#F3EFEF"));
                    holder.textofiltro.setTextColor(Color.BLACK);
                    holder.textofiltro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
/*
                for(int i=0 ; i<lista_deporte_seleccionados.size() ; i++){
                    if( lista_deporte_seleccionados.get(i) == listatipodeporte.get(position).getId() ){
                        bandera = true;
                        posicion = i;
                        break;
                    }
                }
                if( bandera ){
                    bandera = false;
                    holder.textofiltro.setBackgroundColor(Color.parseColor("#F3EFEF"));
                    holder.textofiltro.setTextColor(Color.BLACK);
                    holder.textofiltro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    lista_deporte_seleccionados.remove(posicion);
                    miposicion.metodoParaConocerLaposicion(lista_deporte_seleccionados, listatipodeporte.get(position).getId(), bandera);
                    notifyDataSetChanged();
                }else{
                    holder.textofiltro.setBackgroundColor(Color.parseColor("#F48403"));
                    holder.textofiltro.setTextColor(Color.WHITE);
                    holder.textofiltro.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_tipo_deporte, 0, 0, 0);
                    lista_deporte_seleccionados.add(listatipodeporte.get(position).getId());
                    miposicion.metodoParaConocerLaposicion( lista_deporte_seleccionados, listatipodeporte.get(position).getId(), bandera);
                    notifyDataSetChanged();
                }*/
            }
        });
    }

    @Override
    public int getItemCount(){
        return listatipodeporte.size();
    }

    public interface Conocerposicion {
        void metodoParaConocerLaposicion(List<Integer> lista, int posicion, boolean bandera);
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView textofiltro;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            textofiltro = (TextView) itemView.findViewById(R.id.textofiltros_tdeporte);
        }
    }
}
