package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.FiltrosMarcadores_Mapsactivity_principal;

import java.util.ArrayList;
import java.util.List;

//este es el que nos muestra una vez se le da click a un filtro

public class AdaptadorCategorias_principal extends RecyclerView.Adapter<AdaptadorCategorias_principal.CategoriaViewHolder> {

    Context context;
    List<FiltrosMarcadores_Mapsactivity_principal> listacategoria;
    List<Boolean> seleccionados = new ArrayList<>();
    Conocerposicion miposicion; //interface
    int posicion;

    public AdaptadorCategorias_principal(Context context, List<FiltrosMarcadores_Mapsactivity_principal> listacategoria, Conocerposicion miposicion, int posicion) {
        this.context = context;
        this.listacategoria = listacategoria;
        this.miposicion = miposicion;
        this.posicion = posicion;
    }

    ArrayList<Integer> listaiconos = new ArrayList<>();
    boolean[] seleccion = new boolean[5];

    @NonNull
    @Override
    public AdaptadorCategorias_principal.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.botonesrecycler_filtromarcadores_mapsactivity, null, false);
        return new AdaptadorCategorias_principal.CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorCategorias_principal.CategoriaViewHolder holder, final int position) {
        holder.textofiltro.setText(listacategoria.get(position).getMnames());
        seleccion[posicion] = true;

        holder.textofiltro.setCompoundDrawablesWithIntrinsicBounds(listacategoria.get(position).getCategoria(), 0, 0, 0);

        if (seleccion[position] == false) {
            holder.textofiltro.setBackgroundColor(Color.WHITE);
        }else{
            holder.textofiltro.setBackgroundColor(Color.parseColor("#D31B1B"));
        }

        holder.textofiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miposicion.metodoParalaposiciondelclick(position);
                for(int i=0; i<seleccion.length;i++){
                    seleccion[i] = false;
                }
                seleccion[position] = true;
                posicion = position;
                holder.textofiltro.setBackgroundColor(Color.parseColor("#D31B1B"));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listacategoria.size();
    }

    public interface Conocerposicion{
        void metodoParalaposiciondelclick(int posicion);
    }


    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView textofiltro;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            textofiltro = (TextView) itemView.findViewById(R.id.textofiltros);
            textofiltro.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
