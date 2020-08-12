package com.example.proyecto_grado.fragments.fragments;

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

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.Imagenes_Recycler_Uris;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewImagenes_lugares_comida extends RecyclerView.Adapter<RecyclerViewImagenes_lugares_comida.CategoriaViewHolder> {

    List<Imagenes_Recycler_Uris> imagenporuri = new ArrayList<>();
    List<Imagenes_Recycler_Uris> eliminados = new ArrayList<>();
    Context context;
    boolean bandera = true;
    boolean aBoolean = false;
    boolean ocultarcantidad = false;

    public RecyclerViewImagenes_lugares_comida(List<Imagenes_Recycler_Uris> imagenporuri, Context context, List<Imagenes_Recycler_Uris> eliminados) {
        this.imagenporuri = imagenporuri;
        this.context = context;
        this.eliminados = eliminados;
    }

    ArrayList<Integer> listaiconos = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerViewImagenes_lugares_comida.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagenes_del_marcador, null, false);
        return new RecyclerViewImagenes_lugares_comida.CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewImagenes_lugares_comida.CategoriaViewHolder holder, final int position) {

        try {
            if (!imagenporuri.isEmpty()) {
                pintar(holder, position);
            }

            holder.imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bandera) {
                        bandera = false;
                        //holder.eliminarimagen.setVisibility(View.INVISIBLE);
                        holder.relativeLayout.setVisibility(View.GONE);
                    } else {
                        bandera = true;
                        //holder.eliminarimagen.setVisibility(View.VISIBLE);
                        holder.relativeLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            holder.eliminarimagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Posiciona eliminar: " + position, Toast.LENGTH_SHORT).show();
                    holder.eliminarimagen.setVisibility(View.GONE);
                    holder.imagen.setVisibility(View.GONE);
                    holder.imagenactual.setVisibility(View.GONE);
                    eliminados.add(imagenporuri.get(position));
                    aBoolean = true;
                    notifyDataSetChanged();
                    //pintar(holder, position);
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return imagenporuri.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        ImageButton eliminarimagen;
        TextView imagenactual;
        RelativeLayout relativeLayout;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenactual = (TextView) itemView.findViewById(R.id.imagenactual);
            imagen = (ImageView) itemView.findViewById(R.id.imagenescargadas);
            eliminarimagen = (ImageButton) itemView.findViewById(R.id.eliminar_imagen);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);
        }
    }

    public void pintar(CategoriaViewHolder holder, int position) {
        holder.eliminarimagen.setImageResource(R.drawable.eliminar_imagen);
        holder.imagen.setImageURI(imagenporuri.get(position).getImagenuri());
        if((imagenporuri.size()-eliminados.size())<=1){
            holder.imagenactual.setVisibility(View.GONE);
        }else{
            if(position==0){
                holder.imagenactual.setText((position + 1) + "/" + (imagenporuri.size() - eliminados.size()));
            }else{
                if((position + 1)-eliminados.size()<=0){
                    holder.imagenactual.setText((position+1) + "/" + (imagenporuri.size() - eliminados.size()));
                }else{
                    holder.imagenactual.setText(((position + 1)-eliminados.size()) + "/" + (imagenporuri.size() - eliminados.size()));
                    /*Toast.makeText(context, "Tamaño: " + (position - eliminados.size()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Tamaño: " + (imagenporuri.size()-eliminados.size()), Toast.LENGTH_SHORT).show();*/
                }
            }
        }
    }
    public boolean ocultarcantidaddeimagenes(){
        for (int i=0;i<1000000; i++){
        }
        return true;
    }

    public void agregar() {
        //listaiconos.add(R.drawable.rotar_ala_izquierda);
        //listaiconos.add(R.drawable.rotar_ala_derecha);
        //listaiconos.add(R.drawable.restaurar);
        listaiconos.add(R.drawable.eliminar_imagen);
    }



}
