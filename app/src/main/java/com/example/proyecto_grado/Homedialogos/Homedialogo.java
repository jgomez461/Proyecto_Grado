package com.example.proyecto_grado.Homedialogos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.fragments.fragments.RecyclerviewAdapter_casa;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Homedialogo extends BottomSheetDialogFragment{
    // se saca con logt
    private static final String TAG = "Homedialogo";

    private ArrayList<Integer> mnames = new ArrayList<>();
    private ArrayList<Integer> mimageurl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.boton_home, container, false);
        CircleImageView image;
        TextView texto;

        getImages();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.reciclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerviewAdapter_casa recyclerviewAdapter = new RecyclerviewAdapter_casa(mnames, mimageurl, v.getContext());
        recyclerView.setAdapter(recyclerviewAdapter);
        return v;
    }


    private void getImages(){

        mimageurl.add(R.drawable.food_filter);
        mnames.add(R.string.filtocomida);

        mimageurl.add(R.drawable.exercise_filter);
        mnames.add(R.string.filtoejercicio);

        mimageurl.add(R.drawable.hikes_filter);
        mnames.add(R.string.filtocaminata);

        mimageurl.add(R.drawable.bikepaths_filter);
        mnames.add(R.string.filtociclorutas);
    }




}
