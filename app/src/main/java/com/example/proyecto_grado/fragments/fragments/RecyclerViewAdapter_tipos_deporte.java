package com.example.proyecto_grado.fragments.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.TiposDeporteLugar;

import java.util.List;

public class RecyclerViewAdapter_tipos_deporte extends RecyclerView.Adapter<RecyclerViewAdapter_tipos_deporte.CategoriaViewHolder> {

    List<TiposDeporteLugar> tiposDeporteLugars;
    Context context;

    public RecyclerViewAdapter_tipos_deporte(List<TiposDeporteLugar> tiposDeporteLugars, Context context) {
        this.tiposDeporteLugars = tiposDeporteLugars;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_tipos_deporte.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion_tipos_deporte_lugar, null, false);
        return new RecyclerViewAdapter_tipos_deporte.CategoriaViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_tipos_deporte.CategoriaViewHolder holder, int position) {
        holder.textView.setText(tiposDeporteLugars.get(position).getNombre_tipo_deporte());
        holder.textView.setTextColor(Color.parseColor("#FEFEFE"));
        holder.textView.setBackgroundColor(Color.parseColor("#F48403"));
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_tipo_deporte, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return tiposDeporteLugars.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textofiltros_tdeporte);
        }
    }
}
