package com.example.proyecto_grado.fragments.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.MapsActivity;
import com.example.proyecto_grado.R;

import java.util.ArrayList;

//nos muestra las imagenes en el boton home

public class RecyclerviewAdapter_casa extends RecyclerView.Adapter<RecyclerviewAdapter_casa.VieHolder>{

    private static final String TAG = "RecyclerviewAdapter";

    private ArrayList<Integer> mnames = new ArrayList<>();
    private ArrayList<Integer> mimageurl = new ArrayList<>();
    private Context context;
    Activity activity;

    public  RecyclerviewAdapter_casa(ArrayList<Integer> mnames, ArrayList<Integer> mimageurl, Context context) {
        this.mnames = mnames;
        this.mimageurl = mimageurl;
        this.context = context;
    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtromarcadorescomidahome, parent, false);
        return new VieHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VieHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        /*Glide.with(context)
                .asBitmap()
                .load(mimageurl.get(position))
                .into(holder.imageView);*/
        holder.imageView.setImageResource(mimageurl.get(position));

        holder.textView.setText(mnames.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image" + mnames.get(position));
                Toast.makeText(context, mnames.get(position), Toast.LENGTH_SHORT).show();
                int dato = mnames.get(position);
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("datos", dato);
                context.startActivity(intent);
                //activity.startActivity(intent);
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mimageurl.size();

    }

    public static class VieHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public VieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.filtrosimagen);
            textView = (TextView) itemView.findViewById(R.id.textot);
        }
    }

}
