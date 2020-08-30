package com.example.proyecto_grado;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grado.Homedialogos.Homedialogo;
import com.example.proyecto_grado.Homedialogos.Homedialogo_Deportivo;
import com.example.proyecto_grado.Homedialogos.HomendialogComidas;
import com.example.proyecto_grado.complementos.FiltrosMarcadores_Mapsactivity_principal;
import com.example.proyecto_grado.complementos.SliderPageAdapter_Comidas;
import com.example.proyecto_grado.fragments.fragments.AdaptadorCategorias_principal;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.proyecto_grado.R.drawable.estilo_borde_editext_satelite;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    AdaptadorCategorias_principal adaptador;

    private ArrayList<String> mnames = new ArrayList<>();
    private ArrayList<Integer> mimageurl = new ArrayList<>();

    private GoogleMap mMap;
    private EditText buscardireccionn;
    private ImageButton mtypebutton;
    private ImageButton cambioidioma;
    private boolean cambiidiomaa;
    private Button street;
    private ImageButton lupabuscar;
    private Marker currentUserLocationMarker;
    private ImageButton deletemarker;
    private boolean banderatipo = false, banderabusqueda = false;
    private int contador = 0;
    private ArrayList<Marker> marcadoresm = new ArrayList<>();
    private ArrayList<MarkerOptions> pruebam = new ArrayList<>();
    //este es el marcador de prueba que vamo hacer para los filtros
    private ArrayList<LatLng> latLngsdepruebacomida = new ArrayList<>();
    private ArrayList<LatLng> latLngsdepruebacaminatas = new ArrayList<>();
    private ArrayList<LatLng> latLngsdeprueba = new ArrayList<>();
    private boolean filtros = false;
    private boolean banderacomida = false;
    private boolean banderacaminata = false;
    private boolean banderaotro = false;

    private Marker marcadorprincipal = null;

    //variable de barra de estados
    private BottomNavigationView bottomNavigationView;
    //variables para buscar direccion
    private LatLng miposicion;


    private Bundle extra;
    private SupportMapFragment mapFragment;
    private GoogleMap mapa;
    private Address address;
    private String loongitud;
    private String laatitud;

    //variables para el click en  el mapa
    private boolean banderaclick = false;

    private boolean ba = false;

    //bandera para dejar solo el mapa
    private boolean banderamostrar = false;

    //listas para almacenar los filtros
    private ArrayList<String> nombrestext = new ArrayList<>();
    private ArrayList<Integer> urldelicono = new ArrayList<>();
    private TextView textocambiaidioma;

    //filtro desde el mapa
    RecyclerView recyclerView;
    SliderPageAdapter_Comidas viewPager_comidas;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mtypebutton = (ImageButton) findViewById(R.id.btnsatelite);
        buscardireccionn = (EditText) findViewById(R.id.location);
        lupabuscar = (ImageButton) findViewById(R.id.buscardireccion);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.boton_navegacion);
        deletemarker = (ImageButton) findViewById(R.id.delete_marker);
        recyclerView = findViewById(R.id.filtrosrecycler);
        cambioidioma = (ImageButton) findViewById(R.id.cambioidioma);
        textocambiaidioma = (TextView) findViewById(R.id.textoidioma);

        deletemarker.setVisibility(View.INVISIBLE);
        int posicion = 6;
        //con este codigo nos devolvemos de recyclerview a Mapsactivity y traemos datos
        Bundle parametros = this.getIntent().getExtras();
        //con esto sabremos si hay filtros
        if (parametros != null) {
            filtros = true;
            int datos = parametros.getInt("datos");
            if (datos == R.string.filtocomida) {
                posicion = 1;
                filtrosalamamohome(recyclerView, posicion);
                banderacomida = true;
                Toast.makeText(this, "el marcado es comida", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtocaminata) {
                posicion = 3;
                filtrosalamamohome(recyclerView, posicion);
                banderacaminata = true;
                Toast.makeText(this, "el marcado es caminatas", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtoejercicio) {
                posicion = 2;
                filtrosalamamohome(recyclerView, posicion);
                banderaotro = true;
                Toast.makeText(this, "el marcado es deporte", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtociclorutas) {
                posicion = 4;
                filtrosalamamohome(recyclerView, posicion);
                Toast.makeText(this, "el marcado es ciclorutas", Toast.LENGTH_SHORT).show();
            }
        }

        SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
        int id = usario_datos.getInt("id", 0);

        Toast.makeText(this, "Id: "+ id, Toast.LENGTH_SHORT).show();

        if (Locale.getDefault().getLanguage().equals("es")) {
            Log.d("idioma", Locale.getDefault().getLanguage() + " true");
            cambiidiomaa = true;
            textocambiaidioma.setText("ES");
            cambioidioma.setImageResource(R.drawable.language);
        } else {
            Toast.makeText(this, Locale.getDefault().getLanguage(), Toast.LENGTH_SHORT).show();
            Log.d("idioma", Locale.getDefault().getLanguage() + "  ingles false");
            cambiidiomaa = false;
            textocambiaidioma.setText("EN");
            cambioidioma.setImageResource(R.drawable.language_change);
        }

        //cambiamos el idioma true=español y false=ingles
        cambioidioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cambiidiomaa) {
                    Locale español = new Locale("es", "ES");
                    Locale.setDefault(español);
                    Configuration configuration_es = new Configuration();
                    configuration_es.locale = español;
                    getBaseContext().getResources().updateConfiguration(configuration_es, getBaseContext().getResources().getDisplayMetrics());
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    cambiidiomaa = false;
                    textocambiaidioma.setText("ES");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Locale ingles = new Locale("en", "EN");
                    Locale.setDefault(ingles);
                    Configuration configuration_es = new Configuration();
                    configuration_es.locale = ingles;
                    getBaseContext().getResources().updateConfiguration(configuration_es, getBaseContext().getResources().getDisplayMetrics());
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    cambiidiomaa = true;
                    //intent.putExtra("bandera", cambiidiomaa);
                    textocambiaidioma.setText("EN");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        //con este metodo mostramos un barra despegable
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menudeportivo) {
                    Homedialogo_Deportivo homedialogo_deportivo = new Homedialogo_Deportivo();
                    homedialogo_deportivo.show(getSupportFragmentManager(), "boton deportivo");
                } else if (item.getItemId() == R.id.menucomida) {
                    HomendialogComidas homendialogComidas = new HomendialogComidas();
                    homendialogComidas.show(getSupportFragmentManager(), "boton comida");
                } else if (item.getItemId() == R.id.menuhome) {
                    Homedialogo homedialogo = new Homedialogo();
                    homedialogo.show(getSupportFragmentManager(), "ejemplo boton");
                } else if (item.getItemId() == R.id.menucaminatas) {

                } else if (item.getItemId() == R.id.menuciclorutas) {

                }
                return true;
            }
        });

        mtypebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (banderatipo == true) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    buscardireccionn.setBackgroundResource(R.drawable.estilo_bordes_editext);
                    lupabuscar.setImageResource(R.drawable.buscar);
                    lupabuscar.setBackground(null);
                    buscardireccionn.setTextColor(Color.BLACK);
                    buscardireccionn.setHintTextColor(Color.BLACK);
                    mtypebutton.setImageResource(R.drawable.mundo);
                    Toast.makeText(MapsActivity.this, R.string.cambiodevista_normal, Toast.LENGTH_SHORT).show();
                    banderatipo = false;
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    mtypebutton.setImageResource(R.drawable.satelite);
                    buscardireccionn.setTextColor(Color.WHITE);
                    buscardireccionn.setHintTextColor(Color.WHITE);
                    lupabuscar.setImageResource(R.drawable.buscar_blanco);
                    lupabuscar.setBackground(null);
                    buscardireccionn.setBackgroundResource(estilo_borde_editext_satelite);
                    Toast.makeText(MapsActivity.this, R.string.cambiodevista_hibrido, Toast.LENGTH_SHORT).show();
                    banderatipo = true;
                }

            }
        });

        Mispermisos();

    }

    private void Mispermisos() {
        if (validarpermisos()) {
            Toast.makeText(getApplicationContext(), "Ya", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    // Con este metodo controlamos el boton retroceder del celular
    int contarsalir = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            contarsalir++;
            if( contarsalir == 2 ){
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir")
                        .setMessage("Estás seguro?")
                        .setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir
                                moveTaskToBack(true);
                            }
                        })
                        .show();
            }else if( contarsalir == 3 ){
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Estás seguro que deseas cerrar sesión?")
                        .setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir
                                salirSesion();
                                Intent intent = new Intent(MapsActivity.this, Login_users.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        })
                        .show();
                contarsalir=0;
            }
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

    private void salirSesion() {
        SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = usario_datos.edit();
        editor.clear();
        editor.commit();
    }

    //con este metodo lanzamos los filtros para que el usuario no tenga que darle click a home
    public void filtrosalamamohome(final RecyclerView recyclerView, int posicion) {

        ArrayList<FiltrosMarcadores_Mapsactivity_principal> texto = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.quitarfiltros, R.drawable.princ_quitar_filtro));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtocomida, R.drawable.princ_comida_filtro));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtoejerciciopri, R.drawable.princ_ejercicio_filtro));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtocaminata, R.drawable.princ_correr_filtro));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtociclorutaspri, R.drawable.princ_cicloruta_filtro));
        adaptador = new AdaptadorCategorias_principal(MapsActivity.this, texto, new AdaptadorCategorias_principal.Conocerposicion() {
            @Override
            public void metodoParalaposiciondelclick(int posicion) {

                Toast.makeText(MapsActivity.this, "Le damos click", Toast.LENGTH_SHORT).show();
                if (posicion == 0) {
                    Toast.makeText(MapsActivity.this, "click a la posicición: " + posicion, Toast.LENGTH_SHORT).show();
                    filtros = false;
                    recyclerView.setVisibility(View.GONE);
                    if (mMap != null) {
                        mMap.clear();
                    }
                } else {
                    Toast.makeText(MapsActivity.this, "Nel", Toast.LENGTH_SHORT).show();
                }
            }
        }, posicion);
        recyclerView.setAdapter(adaptador);
    }

    boolean posicion = true;
    JSONObject jso;
    double latitud, longitud;

    //metodo para busca direcciones
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.buscardireccion:
                String direccion = buscardireccionn.getText().toString();

                List<Address> listadireccion = null;
                final MarkerOptions usermarkeroptions = new MarkerOptions();

                if (!TextUtils.isEmpty(direccion)) {
                    Geocoder geocoder = new Geocoder(this);
                    boolean bander = false;
                    try {
                        listadireccion = geocoder.getFromLocationName(direccion, 6);

                        if (listadireccion != null) {

                            for (int i = 0; i < listadireccion.size(); i++) {

                                bander = true;
                                Address useradres = listadireccion.get(i);
                                LatLng latilog = new LatLng(useradres.getLatitude(), useradres.getLongitude());
                                usermarkeroptions.position(latilog);
                                usermarkeroptions.title(direccion);
                                usermarkeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

                                final Marker marcador = mMap.addMarker(new MarkerOptions().position(latilog).title(direccion).
                                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                                marcadoresm.add(marcador);
                                mMap.setOnMarkerClickListener(MapsActivity.this);
                                pruebam.add(usermarkeroptions);

                                onMarkerClick(marcador);

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latilog, 20));
                                CameraPosition cameraPosition = CameraPosition.builder()
                                        .target(latilog)
                                        .zoom(16.0f)
                                        .tilt(45.0f)
                                        .bearing(45.0f)
                                        .build();
                                Toast.makeText(this, "Has creado un marcador", Toast.LENGTH_SHORT).show();

            /*Por último se invoca al método encargado de actualizar el movimiento de la cámara y
            el tiempo de duración para actualizar dicho movimiento.*/
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

                            }

                            if (bander == false) {
                                Toast.makeText(this, "Direccióm no encontrada", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Direccióm no encontrada", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "por favor escriba una direccion correcta", Toast.LENGTH_SHORT).show();
                }
                buscardireccionn.setText("");
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mostrarmiubicacion();
        // con este se desabilita las herramientas
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //mover los botones zoom del mapa
        googleMap.setPadding(-20, 5, 0, 125);

        //esta es una prueba que hacemos para mirar si está funcionando o no los filtros
        //solo es una prueba
        LatLng latLng = new LatLng(7.1000321656733405, -73.12352743378906);
        LatLng latLng2 = new LatLng(7.1046249022857495, -73.11209359437005);
        LatLng latLng3 = new LatLng(7.117950177866987, -73.10443675913243);
        LatLng latLng4 = new LatLng(7.121218534488548, -73.11548746027914);
        LatLng latLng5 = new LatLng(7.124722926557084, -73.11812694556178);
        LatLng latLng6 = new LatLng(7.13162193113493, -73.12064497968733);
        latLngsdepruebacaminatas.add(latLng);
        latLngsdepruebacaminatas.add(latLng2);
        latLngsdeprueba.add(latLng3);
        latLngsdeprueba.add(latLng4);
        latLngsdepruebacomida.add(latLng5);
        latLngsdepruebacomida.add(latLng6);
        if (filtros) {

            if (banderaotro) {
                for (int i = 0; i < latLngsdeprueba.size(); i++) {
                    getdirecciondetalleslongclick(latLngsdeprueba.get(i).latitude, latLngsdeprueba.get(i).longitude);
                }
            }
            if (banderacomida) {
                for (int i = 0; i < latLngsdepruebacomida.size(); i++) {
                    getdirecciondetalleslongclick(latLngsdepruebacomida.get(i).latitude, latLngsdepruebacomida.get(i).longitude);
                }
            }
            if (banderacaminata) {
                for (int i = 0; i < latLngsdepruebacaminatas.size(); i++) {
                    getdirecciondetalleslongclick(latLngsdepruebacaminatas.get(i).latitude, latLngsdepruebacaminatas.get(i).longitude);
                }
            }

        } else {
            for (int i = 0; i < latLngsdeprueba.size(); i++) {
                getdirecciondetalleslongclick(latLngsdeprueba.get(i).latitude, latLngsdeprueba.get(i).longitude);
            }

            for (int i = 0; i < latLngsdepruebacomida.size(); i++) {
                getdirecciondetalleslongclick(latLngsdepruebacomida.get(i).latitude, latLngsdepruebacomida.get(i).longitude);
            }


            for (int i = 0; i < latLngsdepruebacaminatas.size(); i++) {
                getdirecciondetalleslongclick(latLngsdepruebacaminatas.get(i).latitude, latLngsdepruebacaminatas.get(i).longitude);
            }

        }
        //mostrar o no mostrar información

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //buscardireccionn.set;
            }
        });

        //utilizamos este metodo para agregar marcadores con la pantalla presionada
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (banderamostrar) {
                    //no agregamos marcadores porque banderamostrar es false
                } else {
                    //agregamos marcadores en el mapa ya que banderamostrar es true
                    getdirecciondetalleslongclick(latLng.latitude, latLng.longitude);

                }
            }
        });

    }

    private boolean validarpermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Toast.makeText(this, "Version M", Toast.LENGTH_SHORT).show();
            return true;
        }
        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkCallingPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) ||
                shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            cargardialogorecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION}, 100);
        }

        return false;

    }


    //permisos para mostrar mi direccion
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // contacts-related task you need to do.
            } else {
                solicitarpermisosmanual();
            }
        }
    }


    private void solicitarpermisosmanual() {

        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertaopciones = new AlertDialog.Builder(MapsActivity.this);
        alertaopciones.setTitle("¿desea configurar los permisos de forma manual?");
        alertaopciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                try {
                    if (opciones[i].equals("si")) {
                        Intent intentar = new Intent();
                        intentar.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Uri uri = Uri.fromParts("package:", getPackageName(), null);
                        //intentar.setData(uri.parse("package: "+ ContactsContract.Directory.PACKAGE_NAME));
                        intentar.setData(uri);
                        startActivity(intentar);
                    } else {
                        Toast.makeText(MapsActivity.this, "los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "No se pudo acceder a la configuración", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertaopciones.show();

    }

    private void cargardialogorecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MapsActivity.this);
        dialogo.setTitle("permisos desactivados");
        dialogo.setMessage("debe aceptar los permisos para el correcto funcionamiento de la APP");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION}, 100);
            }
        });
        dialogo.show();

    }


    public void mostrarmiubicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }


            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if ((checkCallingPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(getApplicationContext(), "No hay pemisos", Toast.LENGTH_SHORT).show();
                } else {
                    if (posicion) {
                        latitud = location.getLatitude();
                        longitud = location.getLongitude();
                        posicion = false;
                        getdirecciondetalles(latitud, longitud);
                    }
                }

            }
        });
    }


    // con este metodo convertimos una latitud y una longitud a direccion:
    public void getdirecciondetalles(double lat, double lon) {

        double latitud = lat;
        double longitud = lon;
        final MarkerOptions usermarkeroptions = new MarkerOptions();

        Geocoder geocoder;
        List<Address> address;
        geocoder = new Geocoder(this, Locale.getDefault());

        String addres = "";
        String city = "";
        String state = "";
        String country = "";
        String postalcode = "";
        String knonname = "";
        String valoress = "";

        try {
            address = geocoder.getFromLocation(latitud, longitud, 1);

            addres = address.get(0).getAddressLine(0);
            city = address.get(0).getLocality();
            state = address.get(0).getAdminArea();
            country = address.get(0).getCountryName();
            postalcode = address.get(0).getPostalCode();
            knonname = address.get(0).getFeatureName();


            valoress += addres + ", " + city + ", " + state + ", " + country + ", " + postalcode + ", " + knonname;

        } catch (IOException e) {
            e.printStackTrace();
        }

        miposicion = new LatLng(latitud, longitud);

        usermarkeroptions.position(miposicion);
        usermarkeroptions.title(city);
        usermarkeroptions.snippet(city + " - " + country + " - " + state);
        usermarkeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacion));

        final Marker marcador = mMap.addMarker(new MarkerOptions().
                position(miposicion).
                title(addres).
                snippet(postalcode).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacion)));

        marcadoresm.add(marcador);
        marcadorprincipal = marcador;
        pruebam.add(usermarkeroptions);

        onMarkerClick(marcador);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(miposicion, 17);
        mMap.animateCamera(cameraUpdate);

    }

    //prueba para agregar marcadores en un click largo
    public void getdirecciondetalleslongclick(double lat, double lon) {

        double latitud = lat;
        double longitud = lon;
        final MarkerOptions usermarkeroptions = new MarkerOptions();

        Geocoder geocoder;
        List<Address> address;
        geocoder = new Geocoder(this, Locale.getDefault());

        String addres = "";
        String city = "";
        String state = "";
        String country = "";
        String postalcode = "";
        String knonname = "";
        String valoress = "";

        try {
            address = geocoder.getFromLocation(latitud, longitud, 1);
            addres = address.get(0).getAddressLine(0);
            city = address.get(0).getLocality();
            state = address.get(0).getAdminArea();
            country = address.get(0).getCountryName();
            postalcode = address.get(0).getPostalCode();
            knonname = address.get(0).getFeatureName();

            valoress += addres + ", " + city + ", " + state + ", " + country + ", " + postalcode + ", " + knonname;

        } catch (IOException e) {
            e.printStackTrace();
        }

        miposicion = new LatLng(latitud, longitud);
        miposicion = new LatLng(latitud, longitud);

        usermarkeroptions.position(miposicion);
        usermarkeroptions.snippet(postalcode);
        usermarkeroptions.title(addres);
        usermarkeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f);

        /*mMap.addMarker(new MarkerOptions().position(miposicion).title(addres)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f));*/

        final Marker marcador = mMap.addMarker(new MarkerOptions().position(miposicion).title(addres).
                snippet(postalcode).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f));

        marcadoresm.add(marcador);
        pruebam.add(usermarkeroptions);

        onMarkerClick(marcador);

        deletemarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletemarker.setVisibility(View.INVISIBLE);
            }
        });

        //Toast.makeText(this, "Has creado un marcador", Toast.LENGTH_SHORT).show();

    }

    //metodo para eliminar marcadores y mostrar la informacion de un marcador
    @Override
    public boolean onMarkerClick(final Marker marker) {
        mMap.setInfoWindowAdapter(new Informacion_marker(LayoutInflater.from(this)));
        //mMap.setOnInfoWindowClickListener(new Informacion_marker(LayoutInflater.from(this)));
        return false;
    }

}


