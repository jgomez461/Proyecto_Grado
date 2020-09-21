package com.example.proyecto_grado.fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.Lugar;

import java.util.ArrayList;

public class Informacion_markers extends Fragment {

    Informacion_marker informacion_marker;
    private ViewPager markerinformacion;
    private ArrayList<Lugar> lista_lugares_usuario;

    public Informacion_markers(ArrayList<Lugar> lista_lugares_usuario) {
        this.lista_lugares_usuario = lista_lugares_usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View viewGroup = inflater.inflate(R.layout.pageviewcomidasaludable, container, false);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_maps, container, false);

        //informacion_marker = new Informacion_marker(lista_lugares_usuario, viewGroup.getContext());

        Toast.makeText(viewGroup.getContext(), "Datos: " + lista_lugares_usuario.size(), Toast.LENGTH_SHORT).show();

        //imagenesinformacion = (RecyclerView) viewGroup.findViewById(R.id.recyclerinformacion);
        markerinformacion = viewGroup.findViewById(R.id.informacion_markers);
        markerinformacion.setAdapter(informacion_marker);
        markerinformacion.setPadding(130, 0, 130, 0);

        return viewGroup;
    }

}
