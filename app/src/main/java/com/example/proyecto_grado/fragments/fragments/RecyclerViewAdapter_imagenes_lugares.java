package com.example.proyecto_grado.fragments.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.Imagenes_Recycler_Uris;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_imagenes_lugares extends RecyclerView.Adapter<RecyclerViewAdapter_imagenes_lugares.CategoriaViewHolder> {

    List<String> imagenlugar = new ArrayList<>();
    Context context;

    public RecyclerViewAdapter_imagenes_lugares(List<String> imagenlugar, Context context) {
        this.imagenlugar = imagenlugar;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_imagenes_lugares.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion_imagenes_lugares, null, false);
        return new RecyclerViewAdapter_imagenes_lugares.CategoriaViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_imagenes_lugares.CategoriaViewHolder holder, int position) {
        Glide.with(context).
        load(imagenlugar.get(position)).
        fitCenter().centerCrop().
        into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagenlugar.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagenescargadas_lugares);
        }
    }
}