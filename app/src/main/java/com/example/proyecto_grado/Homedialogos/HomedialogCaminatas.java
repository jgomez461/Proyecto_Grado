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
import com.example.proyecto_grado.complementos.SliderPagerAdapter_Caminatas;
import com.example.proyecto_grado.fragments.fragments.PageFragment_caminatas;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomedialogCaminatas extends BottomSheetDialogFragment {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabss;
    private double latitud1;
    private double longitud1;
    private String direccion_lugar1;
    private int posicion = 5;
    private String codigo1;
    private double latitud2;
    private double longitud2;
    private String direccion_lugar2;
    private String codigo2;

    public HomedialogCaminatas(double latitud1, double longitud1, String direccion_lugar1, double latitud2, double longitud2, String direccion_lugar2, int posicion, String codigo1, String codigo2) {
        this.latitud1 = latitud1;
        this.longitud1 = longitud1;
        this.direccion_lugar1 = direccion_lugar1;
        this.latitud2 = latitud2;
        this.longitud2 = longitud2;
        this.direccion_lugar2 = direccion_lugar2;
        this.posicion = posicion;
        this.codigo1 = codigo1;
        this.codigo2 = codigo2;
    }

    public HomedialogCaminatas() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.boton_comida, container, false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_caminatas, null);

        List<Fragment> list = new ArrayList<>();
        if( posicion == 0 ){
            list.add(new PageFragment_caminatas(latitud1, longitud1, direccion_lugar1, codigo1, latitud2, longitud2, direccion_lugar2, codigo2));
        }else {
            list.add(new PageFragment_caminatas());
        }

        pager = (ViewPager) view.findViewById(R.id.viewpagercaminatas);
        pagerAdapter = new SliderPagerAdapter_Caminatas(getChildFragmentManager(), list);
        pager.setAdapter(pagerAdapter);
        tabss = (TabLayout) view.findViewById(R.id.tabsdecaminatas);
        if( posicion != 5 ){
            selectPage(posicion);
        }
        tabss.setupWithViewPager(pager);
        setupTablayout();

        return view;
    }

    private void setupTablayout(){
        tabss.getTabAt(0).setIcon(R.drawable.icono_caminatas);

    }

    void selectPage(int pageIndex){
        tabss.setScrollPosition(pageIndex,0f,true);
        pager.setCurrentItem(pageIndex);
    }
}
