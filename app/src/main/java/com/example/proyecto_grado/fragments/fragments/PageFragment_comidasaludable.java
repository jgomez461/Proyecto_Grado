package com.example.proyecto_grado.fragments.fragments;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.proyecto_grado.Clases.Model_iformacion_lugares;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.Imagenes_Recycler_Uris;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.app.Activity.RESULT_OK;

public class PageFragment_comidasaludable extends Fragment {

    private Button añadirimagen;
    private ImageView imagenes;
    private ImageButton rotarderecha;
    private ImageButton rotarizquierda;
    private ImageButton restaurar;
    private RecyclerView recylcerimagenes;
    private ViewPager imagenesinformacion;
    private int contadorizqui = 0;
    private int contadorderecha = 360;
    private ArrayList<Imagenes_Recycler_Uris> uris = new ArrayList<>();
    private ArrayList<Imagenes_Recycler_Uris> uristotales = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private final String CARPETA_RAIZ = "misimagenes/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + "misfotos";
    final int CODIGO_sELECCIONA = 10;
    final int CODIGO_FOTO = 20;
    private String path;
    private float xBegin = 0;
    private float yBegin = 0;

    ViewPager viewPager;
    Adaptador_informacion_lugares adapter;
    List<Model_iformacion_lugares> model_iformacion_lugares;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    PhotoViewAttacher photoViewAttacher;
    RecyclerViewImagenes_lugares_comida adaptador;
    //RecyclerViewImagenesInformacionComidaSaludable adaptadorinformacion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View viewGroup = inflater.inflate(R.layout.pageviewcomidasaludable, container, false);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.pageviewcomidasaludable, container, false);


        //En este espacion agregamos las imagenes que van como información para el item de comida saludable
        model_iformacion_lugares = new ArrayList<>();
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.informacion_comida_saludable_01, "PRIMERO", "este es el contenido 1"));
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.informacion_comida_saludable_02, "SEGUNDO", "este es el contenido 2"));
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.informacion_comidasaludable_03, "TERCERO", "este es el contenido 3"));

        adapter = new Adaptador_informacion_lugares(model_iformacion_lugares, viewGroup.getContext());

        añadirimagen = (Button) viewGroup.findViewById(R.id.cargarimagen);
        recylcerimagenes = (RecyclerView) viewGroup.findViewById(R.id.recyvlerimagenes);
        //imagenesinformacion = (RecyclerView) viewGroup.findViewById(R.id.recyclerinformacion);
        imagenesinformacion = viewGroup.findViewById(R.id.recyclerinformacion);
        imagenesinformacion.setAdapter(adapter);
        imagenesinformacion.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colornaranjaosucro),
                getResources().getColor(R.color.colorPrimaryDark)
        };

        colors = colors_temp;

        imagenesinformacion.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount() -1) && position < (colors.length - 1)){
                    imagenesinformacion.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position]
                            )
                    );
                }else{
                    imagenesinformacion.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        añadirimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoss();
            }
        });

        if (uris.isEmpty()) {
            recylcerimagenes.setVisibility(View.GONE);
        } else {
            recylcerimagenes.setVisibility(View.VISIBLE);
        }

        //informacionconimagens();

        return viewGroup;
    }

/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }*/

    private void rotarizquierdaa(View view, int posicioni, int posiciond) {

        if (posiciond != 360) {
            posiciond += 90;
            imagenes.setRotation(imagenes.getRotation() + posiciond);
        }
        if (posicioni == 360) {
            posicioni = 0;
            imagenes.setRotation(imagenes.getRotation() + posicioni);
        } else {
            posicioni += 90;
            imagenes.setRotation(imagenes.getRotation() + posicioni);
        }
    }

    private void rotarderechaa(View view, int posicioni, int posiciond) {
        if (posicioni != 0) {
            posicioni -= 90;
            imagenes.setRotation(imagenes.getRotation() + posicioni);
        }
        if (posiciond == 0) {
            posiciond = 360;
            imagenes.setRotation(imagenes.getRotation() + posiciond);
        } else {
            posiciond -= 90;
            imagenes.setRotation(imagenes.getRotation() + posiciond);
        }
    }

    public void datoss() {
        dialogPhoto();
    }

    private void dialogPhoto() {
        try {
            final CharSequence[] items = {"Seleccionar de la galería", "Tomar foto", "Buscar en la web", "Cancelar"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Seleccionar una opción");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            //startActivityForResult(intent, REQUEST_IMAGE, null);
                            startActivityForResult(intent.createChooser(intent, "seleccione la aplicación"), CODIGO_sELECCIONA);
                            break;
                        case 1:
                            tomarfoto();
                            break;
                        case 2:
                            Toast.makeText(getContext(), "Buscando en la web", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            break;
                    }
                }
            });
            builder.show();
        } catch (Exception e) {

        }
    }

    private void tomarfoto() {

        File fileimagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean creada = fileimagen.exists();
        String nombreimagen = "";
        if (creada == false) {
            creada = fileimagen.mkdirs();
        }
        if (creada == true) {
            nombreimagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + nombreimagen;

        File imagen = new File(path);
        Intent intentar = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String autgirities = getContext().getPackageName() + ".provider";
            Uri imageuri = FileProvider.getUriForFile(getContext(), autgirities, imagen);
            intentar.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        } else {
            intentar.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intentar, CODIGO_FOTO);
    }

    /*private void informacionconimagens(){
        imagenesinformacion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptadorinformacion = new RecyclerViewImagenesInformacionComidaSaludable(getContext());
        imagenesinformacion.setAdapter(adaptadorinformacion);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //imagenes.setVisibility(View.VISIBLE);
            switch (requestCode) {
                case CODIGO_sELECCIONA:
                    Uri mipath = data.getData();
                    Log.d("Path: ", mipath.toString());
                    //imagenes.setImageURI(mipath);
                    Toast.makeText(getContext(), "imagen: " + mipath, Toast.LENGTH_SHORT).show();
                    uris.add(new Imagenes_Recycler_Uris(mipath));
                    cargarimagenreciclerimagenes_comida();
                    break;
                case CODIGO_FOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{path},
                            null, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("RUTA DE ALMACENAMIENTO", "PATH: " + path);
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //imagenes.setImageBitmap(bitmap);
                    bitmaps.add(bitmap);
                    //cargarimagenreciclerimagenes_comida();
                    uris.add(new Imagenes_Recycler_Uris(Uri.parse(path)));
                    cargarimagenreciclerimagenes_comida();
                    break;
            }

        } else {
            //Toast.makeText(this, "algo no anda bien", Toast.LENGTH_SHORT).show();
        }

    }

    //Con este metodo cargamos las rutas de las imagenes que están en uri y las mostramos con la clase RecyclerViewImagenes_lugares_comida
    private void cargarimagenreciclerimagenes_comida() {

        if (uris.isEmpty()) {
            recylcerimagenes.setVisibility(View.GONE);
        } else {
            recylcerimagenes.setVisibility(View.VISIBLE);
        }

        List<Imagenes_Recycler_Uris> posicionamiento = new ArrayList<>();
        if (!uristotales.isEmpty()) {
            for (int i = 0; i < uristotales.size(); i++) {
                for (int j = 0; j < uris.size(); j++) {
                    if (uris.get(j) == uristotales.get(i)) {
                        uris.remove(j);
                    }
                }
            }
        }

        Toast.makeText(getContext(), "Tamaño de uris: "+ uris.size(), Toast.LENGTH_SHORT).show();
        uristotales.clear();
        recylcerimagenes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptador = new RecyclerViewImagenes_lugares_comida(uris, getContext(), uristotales);
        recylcerimagenes.setAdapter(adaptador);
    }

    //Con este metodo se hace la conversión de Bitmap a uri, el problema es que crea otra carpeta aparate de la que ya creamos
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //-------------------------------------------------------------------- fin


}
