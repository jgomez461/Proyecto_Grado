package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.proyecto_grado.Clases.Model_iformacion_lugares;
import com.example.proyecto_grado.R;

import java.util.List;

public class Adaptador_informacion_lugares extends PagerAdapter {

    private List<Model_iformacion_lugares> model_iformacion_lugares;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adaptador_informacion_lugares(List<Model_iformacion_lugares> model_iformacion_lugares, Context context) {
        this.model_iformacion_lugares = model_iformacion_lugares;
        this.context = context;
    }

    @Override
    public int getCount() {
        return model_iformacion_lugares.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.informacion_lugares_paraagregar, container, false);

        ImageView imageView;
        TextView title, description;

        imageView = view.findViewById(R.id.informacion_comida_saludable);
        title = view.findViewById(R.id.titulo);
        description = view.findViewById(R.id.contenido);

        imageView.setImageResource(model_iformacion_lugares.get(position).getImagen());
        title.setText(model_iformacion_lugares.get(position).getTitulo());
        description.setText(model_iformacion_lugares.get(position).getTitulo());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
