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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_comida, null);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment_comidasaludable());
        list.add(new PageFragment_comidaintermedia());
        list.add(new PageFragment_comidanosaludable());

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        pagerAdapter = new SliderPageAdapter_Comidas(getChildFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setupTablayout();

        return view;
    }

    private void setupTablayout(){
        tabs.getTabAt(0).setIcon(R.drawable.icono_comidasaludable);
        tabs.getTabAt(1).setIcon(R.drawable.icono_comidaintermedia);
        tabs.getTabAt(2).setIcon(R.drawable.icono_comidanosaludable);
    }



}
