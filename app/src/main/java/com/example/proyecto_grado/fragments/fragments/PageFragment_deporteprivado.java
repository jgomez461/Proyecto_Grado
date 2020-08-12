package com.example.proyecto_grado.fragments.fragments;

import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.proyecto_grado.R;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PageFragment_deporteprivado extends Fragment {
    
    private Button añadirimagen;
    private ImageView imagenes;
    private ArrayList<Uri> uris = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private final String CARPETA_RAIZ = "misimagenes/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ+"misfotos";
    final int CODIGO_sELECCIONA = 10;
    final int CODIGO_FOTO = 20;
    private String path;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.deporte_privado, container, false);
                   
        añadirimagen = (Button) viewGroup.findViewById(R.id.boton_imagenes);
        imagenes = (ImageView) viewGroup.findViewById(R.id.cargar_imagenes);

        if(uris.size()!= 0){
            Toast.makeText(getContext(), "Lo que hay: " + uris.size(), Toast.LENGTH_LONG).show();
            imagenes.setVisibility(View.VISIBLE);
        }else{
            imagenes.setVisibility(View.INVISIBLE);
        }
        if (bitmaps.size()!=0){
            imagenes.setVisibility(View.VISIBLE);
        }else{
            imagenes.setVisibility(View.INVISIBLE);
        }

        
        añadirimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Entra aquí", Toast.LENGTH_SHORT).show();
                datoss();

            }
        });

        return viewGroup;
    }

    //prueba del metodo -------------------------------------------------------------------
    public void datoss(){
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

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String autgirities = getContext().getPackageName()+".provider";
            Uri imageuri = FileProvider.getUriForFile(getContext(), autgirities, imagen);
            intentar.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        }else {
            intentar.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
        }
        startActivityForResult(intentar, CODIGO_FOTO);
        /*try {
            startActivityForResult(intentar, CODIGO_FOTO);
        } catch (Exception e) {
            Toast.makeText(context, "Error al abrir la cámara:"+e.toString(), Toast.LENGTH_SHORT)
                        .show();
            Log.e("Cámara ", e.toString());
        }

         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imagenes.setVisibility(View.VISIBLE);
            switch (requestCode) {

                case CODIGO_sELECCIONA:
                    Uri mipath = data.getData();
                    Log.d("Path: " , mipath.toString());
                    imagenes.setImageURI(mipath);
                    uris.add(mipath);
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
                    imagenes.setImageBitmap(bitmap);
                    bitmaps.add(bitmap);
                    break;

            }

        } else {
            //Toast.makeText(this, "algo no anda bien", Toast.LENGTH_SHORT).show();
        }

    }

    //-------------------------------------------------------------------- fin



}
