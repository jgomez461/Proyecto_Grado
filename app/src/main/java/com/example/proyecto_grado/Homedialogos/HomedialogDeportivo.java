package com.example.proyecto_grado.Homedialogos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.SliderPageAdapter_Deporte;
import com.example.proyecto_grado.fragments.fragments.PageFragment_deporteprivado;
import com.example.proyecto_grado.fragments.fragments.PageFragment_deportepublico;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomedialogDeportivo extends BottomSheetDialogFragment{

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabss;
    private double latitud;
    private double longitud;
    private String direccion_lugar;
    private int posicion = 5;
    private String codigo;

    public HomedialogDeportivo(double latitud, double longitud, String direccion_lugar, int posicion, String codigo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion_lugar = direccion_lugar;
        this.posicion = posicion;
        this.codigo = codigo;
    }

    public HomedialogDeportivo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.boton_comida, container, false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_deportivo, null);

        List<Fragment> list = new ArrayList<>();
        if( posicion == 0 ){
            list.add(new PageFragment_deportepublico(latitud, longitud, direccion_lugar, codigo));
            list.add(new PageFragment_deporteprivado());
        }else if( posicion == 1 ){
            list.add(new PageFragment_deportepublico());
            list.add(new PageFragment_deporteprivado(latitud, longitud, direccion_lugar, codigo));
        }else {
            list.add(new PageFragment_deportepublico());
            list.add(new PageFragment_deporteprivado());
        }

        pager = (ViewPager) view.findViewById(R.id.viewpagerdeporte);
        pagerAdapter = new SliderPageAdapter_Deporte(getChildFragmentManager(), list);
        pager.setAdapter(pagerAdapter);
        tabss = (TabLayout) view.findViewById(R.id.tabsdeportivo);
        if( posicion != 5 ){
            selectPage(posicion);
        }
        tabss.setupWithViewPager(pager);
        setupTablayout();

        return view;
    }

    private void setupTablayout(){
        tabss.getTabAt(0).setIcon(R.drawable.icono_deporte_publico);
        tabss.getTabAt(1).setIcon(R.drawable.icono_deporte_privado);
    }

    void selectPage(int pageIndex){
        tabss.setScrollPosition(pageIndex,0f,true);
        pager.setCurrentItem(pageIndex);
    }
}
