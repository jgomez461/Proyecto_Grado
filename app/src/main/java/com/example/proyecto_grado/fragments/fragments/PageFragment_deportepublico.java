package com.example.proyecto_grado.fragments.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.Clases.Model_iformacion_lugares;
import com.example.proyecto_grado.Clases.VariablesGlobales;
import com.example.proyecto_grado.MapsActivity;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.complementos.FiltrosMarcadores_Mapsactivity_principal;
import com.example.proyecto_grado.complementos.Imagenes_Recycler_Uris;
import com.example.proyecto_grado.entidades.Lugar;
import com.example.proyecto_grado.entidades.TipoDeporte;
import com.example.proyecto_grado.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.app.Activity.RESULT_OK;

public class PageFragment_deportepublico extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText textodescripcion;
    private EditText nombre_lugar;
    private EditText direccion_marker;
    private Button añadirimagen;
    private Button agregar_lugar;
    private ImageButton btn_buscar_direccion;
    private RecyclerView tipos_deporte;
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
    private double latitud;
    private double longitud;
    private String direccion_lugar;
    private String codigo;

    private boolean bandera = false;

    ViewPager viewPager;
    Adaptador_informacion_lugares adapter;
    List<Model_iformacion_lugares> model_iformacion_lugares;
    List<TipoDeporte> lista_tipos_deporte_publico = new ArrayList<>();

    PhotoViewAttacher photoViewAttacher;
    RecyclerViewImagenes_lugares_comida adaptador;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    VariablesGlobales variablesGlobales =  new VariablesGlobales();
    JsonObjectRequest jsonObjectRequest;
    AdaptadorTiposDeporte adaptadorTiposDeporte;

    List<Integer> lista_tipo_deporte_guardar = new ArrayList();

    public PageFragment_deportepublico(double latitud, double longitud, String direccion_lugar, String codigo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion_lugar = direccion_lugar;
        this.codigo = codigo;
    }

    public PageFragment_deportepublico() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.deporte_publico, container, false);


        textodescripcion = viewGroup.findViewById(R.id.textodescripcion_dp);
        nombre_lugar = viewGroup.findViewById(R.id.nombre_lugar_dp);
        direccion_marker = viewGroup.findViewById(R.id.direccion_lugar_dp);
        btn_buscar_direccion = viewGroup.findViewById(R.id.btn_buscar_direccion);
        agregar_lugar = viewGroup.findViewById(R.id.add_location_dp);
        tipos_deporte = viewGroup.findViewById(R.id.lista_tipos_deporte);

        if( direccion_lugar != null ){
            direccion_marker.setText(direccion_lugar);
        }

        //En este espacion agregamos las imagenes que van como información para el item de comida saludable
        model_iformacion_lugares = new ArrayList<>();
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.descripcion_deporte_publico_1, R.string.titulo_1, R.string.informacion_imges_comida_01));
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.descripcion_deporte_publico_2, R.string.titulo_2, R.string.informacion_imges_comida_02));
        model_iformacion_lugares.add(new Model_iformacion_lugares(R.drawable.descripcion_deporte_publico_3, R.string.titulo_3, R.string.informacion_imges_comida_03));

        adapter = new Adaptador_informacion_lugares(model_iformacion_lugares, viewGroup.getContext());

        añadirimagen = (Button) viewGroup.findViewById(R.id.cargarimagen_dp);
        recylcerimagenes = (RecyclerView) viewGroup.findViewById(R.id.recyvlerimagenes_dp);
        //imagenesinformacion = (RecyclerView) viewGroup.findViewById(R.id.recyclerinformacion);
        imagenesinformacion = viewGroup.findViewById(R.id.recyclerinformacion);
        imagenesinformacion.setAdapter(adapter);
        imagenesinformacion.setCurrentItem(1);

        añadirimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoss();
            }
        });

        btn_buscar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDireccion();
            }
        });

        agregar_lugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "ELEMENTOS", lista_tipo_deporte_guardar.size()+"" );
                validacionInformacion();
            }
        });

        requestQueue = Volley.newRequestQueue( getContext());

        if (uris.isEmpty()) {
            recylcerimagenes.setVisibility(View.GONE);
        } else {
            recylcerimagenes.setVisibility(View.VISIBLE);
        }

        cargarListaTiposDeporte();

        return viewGroup;
    }

    private void cargarListaTiposDeporte() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(R.string.tipos_de_deporte+"");
        progressDialog.show();

        String url = variablesGlobales.getUrl_DB() + "ws_consulta_tipos_deporte.php?tipo="+ 4 +" ";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        Log.d("URL", url);
        requestQueue.add(jsonObjectRequest);
    }

    private void validacionInformacion() {
        if( nombre_lugar.getText().toString().length() > 0 && textodescripcion.getText().toString().length() > 0 && direccion_marker.getText().toString().length() > 0 ){
            Toast.makeText(getContext(), "SI", Toast.LENGTH_SHORT).show();
            SharedPreferences usario_datos = getContext().getSharedPreferences("Usuario_info", getContext().MODE_PRIVATE );
            int id_sesion = usario_datos.getInt("id", 0);
            if( id_sesion != 0) {
                if( codigo != null){
                    Toast.makeText(getContext(), "Entra cuando se la direccion", Toast.LENGTH_LONG).show();
                    agregarLugar( codigo, id_sesion, direccion_marker.getText().toString(), nombre_lugar.getText().toString(),
                            textodescripcion.getText().toString(), 1, latitud, longitud);
                }else {
                    Toast.makeText(getContext(), "Entra cuando no se la direccion", Toast.LENGTH_LONG).show();
                    LatLng latLng = variablesGlobales.buscarLatLng( direccion_marker.getText().toString(), getContext());
                    if( latLng != null ){
                        Toast.makeText(getContext(), "Entra cuando esta el id en la sesion", Toast.LENGTH_LONG).show();
                        agregarLugar( variablesGlobales.generate_code_random(10), id_sesion, direccion_marker.getText().toString(), nombre_lugar.getText().toString(), textodescripcion.getText().toString(), 1, latLng.latitude, latLng.longitude);
                    }else{
                        variablesGlobales.setAlertDialog(R.string.agregar_lugar, R.string.direccion_incorrecta, R.string.try_again, getContext());
                    }
                }
            }else {
                Toast.makeText(getContext(), "El usuario no existe, volver a ingresar", Toast.LENGTH_SHORT).show();
            }
        }else {
            variablesGlobales.setAlertDialog(R.string.agregar_lugar, R.string.campos_vacios, R.string.try_again, getContext());
        }
    }

    public void cambiarDireccion(){
        try {
            final CharSequence[] items = {"Buscar dirección", "Escribir dirección", "Cancelar"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Seleccione una opción");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            break;
                        case 1:
                            direccion_marker.setEnabled(true);
                            break;
                        case 2:
                            dialog.dismiss();
                            break;
                    }
                }
            });
            builder.show();
        } catch (Exception e) {

        }
    }

    public void agregarLugar( String codigo, int usuario, String direccion, String nombre_lugar, String descripcion_lugar, int tipo_lugar, double latitud, double longitud ){

        if( !codigo.isEmpty() && usuario != 0 && !direccion.isEmpty() && !nombre_lugar.isEmpty() && !descripcion_lugar.isEmpty() &&
                tipo_lugar != 0 && latitud != 0 && longitud != 0 ){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle(R.string.registro_lugar);
            progressDialog.setMessage(R.string.intento_guardar_lugar+"");
            progressDialog.show();

            bandera = true;
            String url = variablesGlobales.getUrl_DB() + "ws_registro_lugar.php?codigo="+
                    codigo+"&usuario="+
                    usuario +"&direccion="+direccion+"&nombre_lugar="+nombre_lugar+"&descripcion_lugar="
                    +descripcion_lugar+"&tipo_lugar="+tipo_lugar+"&latitud="+latitud+"&longitud="+longitud+" ";
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{
            variablesGlobales.setAlertDialog( R.string.registro_lugar, R.string.error_guardar_lugar, R.string.try_again, getContext() );
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        error.printStackTrace();
        Toast.makeText(getContext(), "No se pudo traer los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();

        if( bandera ){
            direccion_lugar = "";
            latitud = 0;
            longitud = 0;
            codigo = "";
            Intent intent = new Intent(getContext(), MapsActivity.class);
            startActivity(intent);
        }else{
            JSONArray jsonArray = response.optJSONArray("tipos_deporte");
            try {
                lista_tipos_deporte_publico.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TipoDeporte tipoDeporte = new TipoDeporte();
                    JSONObject jsonObject = null;
                    jsonObject = jsonArray.getJSONObject(i);

                    lista_tipos_deporte_publico.add( new TipoDeporte(jsonObject.getInt("id"), jsonObject.getInt("id_tipo_lugar"), jsonObject.getString("nombre")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mostrarInformacionTiposDeporte();
        }
    }

    private void mostrarInformacionTiposDeporte() {
        if( lista_tipos_deporte_publico.size() > 0 ){
            tipos_deporte.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            adaptadorTiposDeporte =  new AdaptadorTiposDeporte(getContext(), lista_tipos_deporte_publico,  new AdaptadorTiposDeporte.Conocerposicion() {
                @Override
                public void metodoParaConocerLaposicion( List<Integer> lista_seleccion, int posicion, boolean bandera) {

                }
            }, lista_tipo_deporte_guardar);
            tipos_deporte.setAdapter(adaptadorTiposDeporte);
        }else{
            Toast.makeText(getContext(), "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }

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
