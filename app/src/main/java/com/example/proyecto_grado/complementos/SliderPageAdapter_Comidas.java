package com.example.proyecto_grado.complementos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.proyecto_grado.R;

import java.util.List;

public class SliderPageAdapter_Comidas extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private final int[] titles = {R.string.tab1, R.string.tab2};

    public SliderPageAdapter_Comidas (FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

/*   Para que sirve esto
    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*String iconos = String.valueOf(icons[position]);
        SpannableStringBuilder ss = new SpannableStringBuilder(iconos);
        ss.setSpan(fonte, 0, ss.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(1.5f), 0, ss.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ss;*/
/*
        switch (position) {
            case 0:
                String titulo = R.string.tab1+"";
                return titulo;
            case 1:
                String titulo2 = R.string.tab2+"";
                //return getString(titles[position]);
                return titulo2;

        }
        return "";
    }*/

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
