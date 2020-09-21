package com.example.proyecto_grado.Homedialogos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.SliderPageAdapter_Comidas;
import com.example.proyecto_grado.fragments.fragments.PageFragment_comidaintermedia;
import com.example.proyecto_grado.fragments.fragments.PageFragment_comidanosaludable;
import com.example.proyecto_grado.fragments.fragments.PageFragment_comidasaludable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
public class HomendialogComidas extends BottomSheetDialogFragment {

    private ViewPager viewPager;
    private TabLayout tabs;
    private PagerAdapter pagerAdapter;
    private double latitud;
    private double longitud;
    private String direccion_lugar;
    private int posicion = 5;
    private String codigo;

    public HomendialogComidas(double latitud, double longitud, String direccion_lugar, int posicion, String codigo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion_lugar = direccion_lugar;
        this.posicion = posicion;
        this.codigo = codigo;
    }

    public HomendialogComidas() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_comida, null);

        List<Fragment> list = new ArrayList<>();
        if( posicion == 0 ){
            list.add(new PageFragment_comidasaludable(latitud, longitud, direccion_lugar, codigo));
            list.add(new PageFragment_comidaintermedia());
            list.add(new PageFragment_comidanosaludable());
        }else if( posicion == 1 ){
            list.add(new PageFragment_comidasaludable());
            list.add(new PageFragment_comidaintermedia(latitud, longitud, direccion_lugar, codigo));
            list.add(new PageFragment_comidanosaludable());
        }else if( posicion == 2 ){
            list.add(new PageFragment_comidanosaludable());
            list.add(new PageFragment_comidaintermedia());
            list.add(new PageFragment_comidanosaludable(latitud, longitud, direccion_lugar, codigo));
        }else{
            list.add(new PageFragment_comidasaludable());
            list.add(new PageFragment_comidaintermedia());
            list.add(new PageFragment_comidanosaludable());
        }

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        pagerAdapter = new SliderPageAdapter_Comidas(getChildFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        if( posicion != 5 ){
            selectPage(posicion);
        }
        tabs.setupWithViewPager(viewPager);
        setupTablayout();

        return view;
    }

    private void setupTablayout(){
        tabs.getTabAt(0).setIcon(R.drawable.icono_comidasaludable);
        tabs.getTabAt(1).setIcon(R.drawable.icono_comidaintermedia);
        tabs.getTabAt(2).setIcon(R.drawable.icono_comidanosaludable);
    }

    void selectPage(int pageIndex){
        tabs.setScrollPosition(pageIndex,0f,true);
        viewPager.setCurrentItem(pageIndex);
    }
}
