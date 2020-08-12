package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_grado.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class RecyclerViewImagenesInformacionComidaSaludable extends RecyclerView.Adapter<RecyclerViewImagenesInformacionComidaSaludable.CategoriaViewHolder> {

    Context context;
    private ArrayList<Integer> mnames = new ArrayList<>();
    private ArrayList<Integer> mimageurl = new ArrayList<>();
    PhotoViewAttacher photoViewAttacher;

    public RecyclerViewImagenesInformacionComidaSaludable(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewImagenesInformacionComidaSaludable.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion_lugares_paraagregar, null, false);
        return new RecyclerViewImagenesInformacionComidaSaludable.CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewImagenesInformacionComidaSaludable.CategoriaViewHolder holder, int position) {

        getImages();

        Glide.with(context)
                .asBitmap()
                .load(mimageurl.get(position))
                .into(holder.imagen);

        holder.textView.setText(mnames.get(position));

        photoViewAttacher = new PhotoViewAttacher(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView textView;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = (ImageView) itemView.findViewById(R.id.informacion_comida_saludable);
            textView = (TextView) itemView.findViewById(R.id.contenido);

        }
    }

    private void getImages(){

        mimageurl.add(R.drawable.prueba_imagenes_redondeo_eliminar_despues);
        mnames.add(R.string.informacion_imges_comida_01);

        mimageurl.add(R.drawable.informacion_comida_saludable_02);
        mnames.add(R.string.informacion_imges_comida_02);

        mimageurl.add(R.drawable.informacion_comidasaludable_03);
        mnames.add(R.string.informacion_imges_comida_03);

    }


}
