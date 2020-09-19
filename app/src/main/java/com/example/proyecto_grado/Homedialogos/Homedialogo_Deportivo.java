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
import com.example.proyecto_grado.complementos.SliderPageAdapter_deporte;
import com.example.proyecto_grado.fragments.fragments.PageFragment_deporteprivado;
import com.example.proyecto_grado.fragments.fragments.PageFragment_deportepublico;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Homedialogo_Deportivo extends BottomSheetDialogFragment{

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabss;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.boton_comida, container, false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_deportivo, null);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment_deportepublico());
        list.add(new PageFragment_deporteprivado());

        pager = (ViewPager) view.findViewById(R.id.viewpagerdeporte);
        pagerAdapter = new SliderPageAdapter_deporte(getChildFragmentManager(), list);
        pager.setAdapter(pagerAdapter);
        tabss = (TabLayout) view.findViewById(R.id.tabsdeportivo);
        tabss.setupWithViewPager(pager);
        setupTablayout();

        return view;
    }

    private void setupTablayout(){
        tabss.getTabAt(0).setIcon(R.drawable.icono_deporte_publico);
        tabss.getTabAt(1).setIcon(R.drawable.icono_deporte_privado);
    }
}
