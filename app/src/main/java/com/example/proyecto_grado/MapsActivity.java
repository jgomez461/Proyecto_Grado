package com.example.proyecto_grado;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.Homedialogos.HomedialogCaminatas;
import com.example.proyecto_grado.Homedialogos.HomedialogCicloRutas;
import com.example.proyecto_grado.entidades.TiposDeporteLugar;
import com.example.proyecto_grado.entidades.VariablesGlobales;
import com.example.proyecto_grado.Homedialogos.Homedialogo;
import com.example.proyecto_grado.Homedialogos.HomedialogDeportivo;
import com.example.proyecto_grado.Homedialogos.HomendialogComidas;
import com.example.proyecto_grado.complementos.FiltrosMarcadores_Mapsactivity_principal;
import com.example.proyecto_grado.complementos.SliderPageAdapter_Comidas;
import com.example.proyecto_grado.entidades.Lugar;
import com.example.proyecto_grado.entidades.Usuario;
import com.example.proyecto_grado.fragments.fragments.AdaptadorCategorias_principal;
import com.example.proyecto_grado.fragments.fragments.InformacionMarker;
import com.example.proyecto_grado.fragments.fragments.Informacion_marker;
import com.example.proyecto_grado.fragments.fragments.Informacion_markers;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.proyecto_grado.R.drawable.estilo_borde_editext_satelite;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, Response.Listener<JSONObject>, Response.ErrorListener{


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

    //variables para mostrar las imagenes
    private List<String> imagenes_marcadores = new ArrayList<>() ;
    private List<String> imagenes_marcadores_seleccionados = new ArrayList<>();
    private List<TiposDeporteLugar> tiposDeporteLugars = new ArrayList<>();

    private Bundle extra;
    private SupportMapFragment mapFragment;
    private GoogleMap mapa;
    private Address address;
    private String loongitud;
    private String laatitud;
    private int codigo_rutas = 0;

    //variables para el click en  el mapa
    private boolean banderaclick = false;

    private boolean ba = false;
    private ArrayList<LatLng> lugares_usuario = new ArrayList<>();
    private List<Lugar> lista_lugares_usuario = new ArrayList<>();
    private List<Lugar> lugares_agregados = new ArrayList<>();

    //bandera para dejar solo el mapa
    private boolean banderamostrar = false;

    //listas para almacenar los filtros
    private ArrayList<String> nombrestext = new ArrayList<>();
    private ArrayList<Integer> urldelicono = new ArrayList<>();
    private TextView textocambiaidioma;
    private int posicion_filtro = 0;

    //filtro desde el mapa
    RecyclerView recyclerView;
    SliderPageAdapter_Comidas viewPager_comidas;
    //Declare HashMap to store mapping of marker to Activity
    HashMap<String, String> mMarkers = new HashMap<String, String>();

    Lugar lugar = new Lugar();
    Informacion_markers informacion_markers;
    ViewPager2 imagenesinformacion;
    Informacion_marker adapter;
    InformacionMarker informacionMarker;
    ViewPager2 viewPager2;
    int posiscion_marcador = 0;
    String codigo_marcador = "";

    //variables para trazar rutas
    private LatLng latLng1 = null;
    private LatLng latLng2 = null;
    private String codigo_1 = "";
    private String codigo_2 = "";
    private String direccion_1 = "";
    private String direccion_2 = "";
    private int tipo_buqueda_lugar = 0;

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
        imagenesinformacion = (ViewPager2) findViewById(R.id.informacion_markers);
        //viewPager2 = (ViewPager2) findViewById(R.id.informacion_markers);

        deletemarker.setVisibility(View.INVISIBLE);
        //con este codigo nos devolvemos de recyclerview a Mapsactivity y traemos datos
        Bundle parametros = this.getIntent().getExtras();
        //con esto sabremos si hay filtros
        if (parametros != null) {
            filtros = true;
            int datos = parametros.getInt("datos");
            if (datos == R.string.filtocomida) {
                posicion_filtro = 1;
                filtrosalamamohome(recyclerView, posicion_filtro);
                banderacomida = true;
                Toast.makeText(this, "el marcado es comida", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtocaminata) {
                posicion_filtro = 3;
                filtrosalamamohome(recyclerView, posicion_filtro);
                banderacaminata = true;
                Toast.makeText(this, "el marcado es caminatas", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtoejercicio) {
                posicion_filtro = 2;
                filtrosalamamohome(recyclerView, posicion_filtro);
                banderaotro = true;
                Toast.makeText(this, "el marcado es deporte", Toast.LENGTH_SHORT).show();
            } else if (datos == R.string.filtociclorutas) {
                posicion_filtro = 4;
                filtrosalamamohome(recyclerView, posicion_filtro);
                Toast.makeText(this, "el marcado es ciclorutas", Toast.LENGTH_SHORT).show();
            }
        }

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

        buscardireccionn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenesinformacion.setVisibility(View.INVISIBLE);
            }
        });

        //con este metodo mostramos un barra despegable
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menudeportivo) {
                HomedialogDeportivo homedialog_deportivo = new HomedialogDeportivo();
                homedialog_deportivo.show(getSupportFragmentManager(), "boton deportivo");
            } else if (item.getItemId() == R.id.menucomida) {
                HomendialogComidas homendialogComidas = new HomendialogComidas();
                homendialogComidas.show(getSupportFragmentManager(), "boton comida");
            } else if (item.getItemId() == R.id.menuhome) {
                Homedialogo homedialogo = new Homedialogo();
                homedialogo.show(getSupportFragmentManager(), "ejemplo boton");
            } else if (item.getItemId() == R.id.menucaminatas) {
                HomedialogCaminatas homedialogCaminatas = new HomedialogCaminatas();
                homedialogCaminatas.show(getSupportFragmentManager(), "boton caminatas");
            } else if (item.getItemId() == R.id.menuciclorutas) {
                HomedialogCicloRutas homedialogCicloRutas = new HomedialogCicloRutas();
                homedialogCicloRutas.show(getSupportFragmentManager(), "boton ciclorutas");
            }
            return true;
            }
        });

        mtypebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (banderatipo) {
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

    VariablesGlobales variablesGlobales = new VariablesGlobales();

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
            if (contarsalir == 2) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir")
                        .setMessage("Estás seguro?")
                        .setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Salir
                                moveTaskToBack(true);
                            }
                        })
                        .show();
            } else if (contarsalir == 3) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Estás seguro que deseas cerrar sesión?")
                        .setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Salir
                                salirSesion();
                                Intent intent = new Intent(MapsActivity.this, Login_users.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        })
                        .show();
                contarsalir = 0;
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
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.quitarfiltros, R.drawable.princ_quitar_filtro, R.drawable.princ_quitar_filtro_color));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtocomida, R.drawable.princ_comida_filtro, R.drawable.princ_comida_filtro_color));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtoejerciciopri, R.drawable.princ_ejercicio_filtro, R.drawable.princ_ejercicio_filtro_color));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtocaminata, R.drawable.princ_correr_filtro, R.drawable.princ_correr_filtro_color));
        texto.add(new FiltrosMarcadores_Mapsactivity_principal(R.string.filtociclorutaspri, R.drawable.princ_cicloruta_filtro, R.drawable.princ_cicloruta_filtro_color));

        adaptador = new AdaptadorCategorias_principal(MapsActivity.this, texto, new AdaptadorCategorias_principal.Conocerposicion() {
            @Override
            public void metodoParalaposiciondelclick(int posicion) {
                Toast.makeText(MapsActivity.this, "Posicion: " + posicion, Toast.LENGTH_SHORT).show();
                if (posicion == 0) {
                    Toast.makeText(MapsActivity.this, "click a la posicición: " + posicion, Toast.LENGTH_SHORT).show();
                    filtros = false;
                    recyclerView.setVisibility(View.GONE);
                    if (mMap != null) {
                        mMap.clear();
                    }
                    tipo_buqueda_lugar = posicion;
                    consultaLugaresUsuario();
                } else {
                    tipo_buqueda_lugar = posicion;
                    if (mMap != null) {
                        mMap.clear();
                    }
                    consultaLugaresUsuario();
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
                        listadireccion = geocoder.getFromLocationName(direccion, 1);

                        if (listadireccion != null) {
                            for (int i = 0; i < listadireccion.size(); i++) {
                                bander = true;
                                Address useradres = listadireccion.get(i);
                                LatLng latilog = new LatLng(useradres.getLatitude(), useradres.getLongitude());
                                usermarkeroptions.position(latilog);
                                usermarkeroptions.title(direccion);
                                usermarkeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

                                tiposDeporteLugars.clear();
                                lugares_agregados.add(new Lugar(0, variablesGlobales.generate_code_random(10), 0, direccion, "N.A", "Ubicación por agregar", 0, 0, "N.A", useradres.getLatitude(), useradres.getLongitude(), "0", "0", 0 ,0, imagenes_marcadores, tiposDeporteLugars));;
                                consultaLugaresUsuario();
                            }

                            if (!bander) {
                                Toast.makeText(this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();
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
                    consultaLugaresUsuario();
                }
            }
        });

    }

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    JsonObjectRequest jsonObjectRequest;
    JsonObjectRequest jsonObjectRequest2;

    private void consultaLugaresUsuario() {
        SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
        int id = usario_datos.getInt("id", 0);
        if (id != 0) {
            requestQueue = Volley.newRequestQueue(this);
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Lugares");
            progressDialog.setMessage("Cargando lugares, por favor espere...");
            progressDialog.show();

            String variablesDB = variablesGlobales.getUrl_DB();
            String url = variablesDB + "ws_consulta_lugares.php?usuario=" + id + "&tipo="+tipo_buqueda_lugar+"";
            url = url.replace(" ", "%20");

            Log.d("url", url);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest); //volver a llamar esto
        } else {

        }
    }

    String codigo_marcador_mostrar = "";
    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        JSONArray jsonArray = response.optJSONArray("lugares");
        try {
            lista_lugares_usuario.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                Usuario usuario = new Usuario();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);

                lista_lugares_usuario.add(new Lugar(jsonObject.getInt("id"), jsonObject.getString("codigo"),
                        jsonObject.getInt("usuario"), jsonObject.getString("direccion"),
                        jsonObject.getString("nombre_lugar"), jsonObject.getString("descripcion_lugar"),
                        jsonObject.getInt("tipo_lugar"), jsonObject.getInt("tipo_lugar_principal"), jsonObject.getString("fecha_creacion"),
                        Double.parseDouble(jsonObject.getString("latitud")), Double.parseDouble(jsonObject.getString("longitud")),
                        jsonObject.getString("codigo_2"), jsonObject.getString("direccion_2"), Double.parseDouble(jsonObject.getString("latitud_2")),
                        Double.parseDouble(jsonObject.getString("longitud_2")), imagenes_marcadores_seleccionados, tiposDeporteLugars));

                if( jsonObject.getInt("tipo_lugar_principal") == 3 || jsonObject.getInt("tipo_lugar_principal") == 4 ){
                    lista_lugares_usuario.add( new Lugar( jsonObject.getInt("id"), jsonObject.getString("codigo"),
                            jsonObject.getInt("usuario"), jsonObject.getString("direccion_2"),
                            jsonObject.getString("nombre_lugar"), jsonObject.getString("descripcion_lugar"),
                            jsonObject.getInt("tipo_lugar"), jsonObject.getInt("tipo_lugar_principal"), jsonObject.getString("fecha_creacion"),
                            Double.parseDouble(jsonObject.getString("latitud_2")), Double.parseDouble(jsonObject.getString("longitud_2")),
                            jsonObject.getString("codigo_2"), jsonObject.getString("direccion_2"), Double.parseDouble(jsonObject.getString("latitud_2")),
                            Double.parseDouble(jsonObject.getString("longitud_2")), imagenes_marcadores_seleccionados, tiposDeporteLugars));
                }
            }
            if( lugares_agregados.size() > 0 ){
                lista_lugares_usuario.addAll(lugares_agregados);
                pintarLugares(lista_lugares_usuario);
            }else{
                pintarLugares(lista_lugares_usuario);
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String actionId = mMarkers.get(marker.getId());
                    for (int i=0; i<lista_lugares_usuario.size(); i++){
                        String codigo = lista_lugares_usuario.get(i).getCodigo();
                        if(codigo.equals(actionId)){
                            posiscion_marcador = i;
                            codigo_marcador = codigo;
                            cargarVistasMarcadores(posiscion_marcador);
                            break;
                        }
                    }
                }
            });
            /*
            //adapter = new Informacion_marker(lista_lugares_usuario, this);
            adapter = new Informacion_marker( lista_lugares_usuario, this, new Informacion_marker.Conocerposicion() {
                @Override
                public void posisicionDelMarcador(String codigo_marcador, int id, LatLng latLng, String direccion_lugar, int codigo_accion) {
                    if( codigo_accion == 1 ){
                        mostrarInformacion(lista_lugares_usuario, codigo_marcador);
                    }else if( codigo_accion == 2 ){
                        Toast.makeText(MapsActivity.this, "si se agrega..." + codigo_marcador, Toast.LENGTH_SHORT).show();
                        agregarLugar(latLng, direccion_lugar, codigo_marcador);
                    }
                }
            });*/
            imagenesinformacion.setAdapter( new InformacionMarker(lista_lugares_usuario, imagenesinformacion, this, new InformacionMarker.posicion() {
                @Override
                public void posicionMarcador(String posicion, int id, LatLng latLng, String direccion_lugar, int codigo_accion) {
                    Log.d("CANTIDAD_POSICION", "Lugares: " +  lista_lugares_usuario.toString());
                    if( codigo_accion == 1 ){
                        mostrarInformacion(lista_lugares_usuario, posicion);
                    }else if( codigo_accion == 2 ){
                        Toast.makeText(MapsActivity.this, "si se agrega..." + codigo_marcador, Toast.LENGTH_SHORT).show();
                        agregarLugar(latLng, direccion_lugar, posicion);
                    }
                }
            }));
            cargarVistasMarcadores(posiscion_marcador);
            Log.d("CANTIDAD", "Lugares: " +  lista_lugares_usuario.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Intent intent = new Intent(MapsActivity.this, Informacion_marker.class);
        startActivity(intent);*/
    }

    private Collection<? extends TiposDeporteLugar> cargarTiposLugar(final int id, final String codigo_lugar) {
        final List<TiposDeporteLugar> tipo_lugar = new ArrayList<>();

        String variablesDB = variablesGlobales.getUrl_DB();
        String url3 = variablesDB + "ws_consulta_tipos_deporte_lugar.php?lugar=" + id + " ";
        Log.d("url", url3);
        requestQueue2 = Volley.newRequestQueue(this);
        jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray3 = response.optJSONArray("tipo_deporte_lugar" );
                for (int j=0; j< jsonArray3.length(); j++){
                    JSONObject jsonObject3 = null;
                    try {
                        jsonObject3 = jsonArray3.getJSONObject(j);
                        tipo_lugar.add( new TiposDeporteLugar( id, codigo_lugar,
                                jsonObject3.getInt("id_tipo_lugar"), jsonObject3.getString("nombre_tipo") ) );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tiposDeporteLugars.clear();
            }
        });
        requestQueue2.add(jsonObjectRequest2);

        return tipo_lugar;
    }

    private List<String> cargarImagenesLugar( int id ) {
        List<String> imagenes_lugar = new ArrayList<>();
        requestQueue2 = Volley.newRequestQueue(this);

        //consulta para traer a información de las imagenes de los lugares
        String variablesDB = variablesGlobales.getUrl_DB();
        String url2 = variablesDB + "ws_consulta_imagenes_lugares.php?id_lugar=" + id + "";

        Log.d("url", url2);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray2 = response.optJSONArray("imagenes_lugar");
                if( jsonArray2.length() > 0 ){
                    Log.d("SIDATA", "si hay"+ jsonArray2);
                    try {
                        for (int j=0; j< jsonArray2.length(); j++){
                            JSONObject jsonObject2 = null;
                            jsonObject2 = jsonArray2.getJSONObject(j);
                            imagenes_marcadores_seleccionados.add(jsonObject2.getString("ruta_imagen"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("NODATA", "No hay imagenes");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //imagenes_marcadores_seleccionados.clear();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //fin de imagenes de los lugares

        return imagenes_lugar;
    }

    private void cargarVistasMarcadores(int posiscion_marcador) {
        imagenesinformacion.setVisibility(View.VISIBLE);
        //imagenesinformacion.setAdapter(adapter);
        imagenesinformacion.setPadding(5, 0, 5, 0);
        imagenesinformacion.setCurrentItem( posiscion_marcador );
    }

    List<String> codigos_rutas = new ArrayList<>();
    private void mostrarInformacion(List<Lugar> lista_lugares_usuario, String codigo) {

        for( int i=0; i<lista_lugares_usuario.size(); i++ ){
            LatLng latLng = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
            final Marker marcador = mMap.addMarker(new MarkerOptions().
                    position(latLng).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20);
            mMap.animateCamera(cameraUpdate);
        }

        for( int i=0; i<lista_lugares_usuario.size(); i++ ){
            if( codigo == lista_lugares_usuario.get(i).getCodigo() || codigo_marcador_mostrar == lista_lugares_usuario.get(i).getCodigo() ){
                LatLng latLng = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
                final Marker marcador = mMap.addMarker(new MarkerOptions().
                        position(latLng).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20);
                mMap.animateCamera(cameraUpdate);
            }
        }
    }

    private void pintarLugares(List<Lugar> lista_lugares_usuario) {
        for ( int i=0; i<lista_lugares_usuario.size(); i++ ){
            LatLng latLng = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
            if( lista_lugares_usuario.get(i).getTipo_lugar_principal() == 1 ){
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).
                        title("marker: " + lista_lugares_usuario.get(i).getDireccion()).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                String id_marker = marker.getId();
                mMarkers.put( id_marker , lista_lugares_usuario.get(i).getCodigo());
            }else if( lista_lugares_usuario.get(i).getTipo_lugar_principal() == 2 ){
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).
                        title("marker: " + lista_lugares_usuario.get(i).getDireccion()).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                String id_marker = marker.getId();
                mMarkers.put( id_marker , lista_lugares_usuario.get(i).getCodigo());
            }else if( lista_lugares_usuario.get(i).getTipo_lugar_principal() == 3 ){
                codigo_rutas = 1;
                if( !codigos_rutas.isEmpty() ){
                    for ( int j=0; j<codigos_rutas.size(); j++ ){
                        if( codigos_rutas.get(j).equals(lista_lugares_usuario.get(j).getCodigo()) ){
                            break;
                        }else{
                            LatLng latLng1 = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
                            LatLng latLng2 = new LatLng(lista_lugares_usuario.get(i).getLatitud2(), lista_lugares_usuario.get(i).getLongitud2());
                            codigos_rutas.add( lista_lugares_usuario.get(i).getCodigo() );
                            cargarInformacionRuta( latLng1, latLng2 );
                        }
                    }
                }else{
                    LatLng latLng1 = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
                    LatLng latLng2 = new LatLng(lista_lugares_usuario.get(i).getLatitud2(), lista_lugares_usuario.get(i).getLongitud2());
                    codigos_rutas.add( lista_lugares_usuario.get(i).getCodigo() );
                    cargarInformacionRuta( latLng1, latLng2 );
                }
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).
                        title("marker: " + lista_lugares_usuario.get(i).getDireccion()).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                String id_marker = marker.getId();
                mMarkers.put( id_marker , lista_lugares_usuario.get(i).getCodigo());
            }else if( lista_lugares_usuario.get(i).getTipo_lugar_principal() == 4 ){
                codigo_rutas = 1;
                for ( int j=0; j<codigos_rutas.size(); j++ ){
                    if( codigos_rutas.get(j).equals(lista_lugares_usuario.get(j).getCodigo()) ){
                        break;
                    }else{
                        LatLng latLng1 = new LatLng(lista_lugares_usuario.get(i).getLatitud(), lista_lugares_usuario.get(i).getLongitud());
                        LatLng latLng2 = new LatLng(lista_lugares_usuario.get(i).getLatitud2(), lista_lugares_usuario.get(i).getLongitud2());
                        codigos_rutas.add( lista_lugares_usuario.get(j).getCodigo() );
                        cargarInformacionRuta( latLng1, latLng2 );
                    }
                }
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).
                        title("marker: " + lista_lugares_usuario.get(i).getDireccion()).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                String id_marker = marker.getId();
                mMarkers.put( id_marker , lista_lugares_usuario.get(i).getCodigo());
            }else{
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).
                        title("marker: " + lista_lugares_usuario.get(i).getDireccion()).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                String id_marker = marker.getId();
                mMarkers.put( id_marker , lista_lugares_usuario.get(i).getCodigo());
            }

        }
    }

    HomendialogComidas homendialog = new HomendialogComidas();
    HomedialogDeportivo homedialogo_dep = new HomedialogDeportivo();
    HomedialogCaminatas homedialog_cam = new HomedialogCaminatas();
    HomedialogCicloRutas homedialog_ciclo = new HomedialogCicloRutas();

    //con este metodo lo que hacemos es cargar por medio de la interface de Informacion_marker traemos la latlng y la direccion para poder agregarlo en la base de datos
    private void agregarLugar(final LatLng latLng, final String direccion_lugar, final String posicion) {
        try {
            final CharSequence[] items = {"Comida Saludable", "Comida intermedia", "Comida no Saludable", "Deporte público", "Deporte privado", "Caminatas", "Ciclorutas", "Cancelar"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccione una opción");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        homendialog = new HomendialogComidas(latLng.latitude, latLng.longitude, direccion_lugar, 0, posicion);
                        homendialog.show(getSupportFragmentManager(), "boton comida");
                        break;
                    case 1:
                        homendialog = new HomendialogComidas(latLng.latitude, latLng.longitude, direccion_lugar, 1, posicion);
                        homendialog.show(getSupportFragmentManager(), "boton comida");
                        break;
                    case 2:
                        homendialog = new HomendialogComidas(latLng.latitude, latLng.longitude, direccion_lugar, 2, posicion);
                        homendialog.show(getSupportFragmentManager(), "boton comida");
                        break;
                    case 3:
                        homedialogo_dep = new HomedialogDeportivo(latLng.latitude, latLng.longitude, direccion_lugar, 0, posicion);
                        homedialogo_dep.show(getSupportFragmentManager(), "boton deportivo");
                        break;
                    case 4:
                        homedialogo_dep = new HomedialogDeportivo(latLng.latitude, latLng.longitude, direccion_lugar, 1, posicion);
                        homedialogo_dep.show(getSupportFragmentManager(), "boton deportivo");
                        break;
                    case 5:
                        if( latLng1 == null ){
                            latLng1 = latLng;
                            codigo_1 = posicion;
                            direccion_1 = direccion_lugar;
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                            builder1.setIcon(R.drawable.trazar_rutas).setTitle("Rutas").setMessage("En la sección de caminatas se necesitán 2 posiciones (Inicial y final), " +
                                    "para poder continuar tienes que agregar otra posición.");
                            builder1.setPositiveButton(
                                    "Agregar otro lugar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNegativeButton("Quitar lugar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            latLng1 = null;
                                            codigo_1 = "";
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }else{
                            if( codigo_1 != posicion ){
                                latLng2 = latLng;
                                codigo_2 = posicion;
                                direccion_2 = direccion_lugar;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                builder1.setIcon(R.drawable.trazar_rutas).setTitle("Rutas").setMessage("¿Desea trazar la ruta entre los dos lugares seleccionados?");
                                builder1.setPositiveButton(
                                        "Trazar ruta",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Log.d("Latng1", latLng1.toString() );
                                                Log.d("Latng2", latLng2.toString() );
                                                homedialog_cam = new HomedialogCaminatas(latLng1.latitude, latLng1.longitude, direccion_1, latLng2.latitude, latLng2.longitude, direccion_2, 0, codigo_1, codigo_2);
                                                homedialog_cam.show(getSupportFragmentManager(), "boton caminatas");
                                                codigo_rutas = 0;
                                                cargarInformacionRuta( latLng1, latLng2 );
                                                dialog.cancel();
                                            }
                                        });
                                builder1.setNegativeButton("Quitar lugares seleccionados",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        latLng1 = null;
                                        codigo_1 = "";
                                        latLng2 = null;
                                        codigo_2 = "";
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }else{
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                builder1.setIcon(R.drawable.advertencia).setTitle("Lugar erroneo").setMessage("No puede agregar el mismo lugar, intentar con otro.");
                                builder1.setPositiveButton(
                                    "Continuar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        }
                        break;
                    case 6:
                        if( latLng1 == null ){
                            latLng1 = latLng;
                            codigo_1 = posicion;
                            direccion_1 = direccion_lugar;
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                            builder1.setIcon(R.drawable.trazar_rutas).setTitle("Rutas").setMessage("En la sección de ciclorutas se necesitán 2 posiciones (Inicial y final), " +
                                    "para poder continuar tienes que agregar otra posición.");
                            builder1.setPositiveButton(
                                    "Agregar otro lugar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNegativeButton("Quitar lugar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            latLng1 = null;
                                            codigo_1 = "";
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }else{
                            if( codigo_1 != posicion ){
                                latLng2 = latLng;
                                codigo_2 = posicion;
                                direccion_2 = direccion_lugar;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                builder1.setIcon(R.drawable.trazar_rutas).setTitle("Rutas").setMessage("¿Desea trazar la ruta entre los dos lugares seleccionados?");
                                builder1.setPositiveButton(
                                        "Trazar ruta",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                homedialog_ciclo = new HomedialogCicloRutas(latLng1.latitude, latLng1.longitude, direccion_1, latLng2.latitude, latLng2.longitude, direccion_2, 0, codigo_1, codigo_2);
                                                homedialog_ciclo.show(getSupportFragmentManager(), "boton ciclorutas");
                                                codigo_rutas = 0;
                                                cargarInformacionRuta( latLng1, latLng2 );
                                                dialog.cancel();
                                            }
                                        });
                                builder1.setNegativeButton("Quitar lugares seleccionados",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        latLng1 = null;
                                        codigo_1 = "";
                                        latLng2 = null;
                                        codigo_2 = "";
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }else{
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                builder1.setIcon(R.drawable.advertencia).setTitle("Lugar erroneo").setMessage("No puede agregar el mismo lugar, intentar con otro.");
                                builder1.setPositiveButton(
                                        "Continuar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        }
                        break;
                    case 7:
                        dialog.dismiss();
                        break;
                }
                }
            });
            builder.show();
        } catch (Exception e) {

        }
    }

    private void cargarInformacionRuta( LatLng latLng1, LatLng latLng2 ) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+latLng1.latitude+","+latLng1.longitude+"&destination="+latLng2.latitude+","+latLng2.longitude+"&key=AIzaSyDq4c_ubweF2Oilne6EDr5wsQpHGZ1mI0k";
        Log.d("DATO", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jso = new JSONObject(response);
                    trazarRuta(jso);
                    Log.i("jsonRuta: ",""+response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest); //volver a llamar esto
    }


    private void trazarRuta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){
                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
                    for (int k = 0; k<jSteps.length();k++){
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        if( codigo_rutas == 1 ){
                            mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.rgb(5, 13, 108 )).width(10));
                        }else if( codigo_rutas == 2 ){
                            mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.rgb(238, 12, 9)).width(10));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(this, "Algo ocurrió con la consulta. intentarlo mas tarde" + error.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.hide();
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
                        if( posicion_filtro != 0 ){
                            if (mMap != null) {
                                mMap.clear();
                            }
                            tipo_buqueda_lugar = posicion_filtro;
                            consultaLugaresUsuario();
                        }else {
                            consultaLugaresUsuario();
                        }
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

        tiposDeporteLugars.clear();
        lugares_agregados.add(new Lugar(0, variablesGlobales.generate_code_random(10), 0, addres, "N.A", "Ubicación por geolocalización", 0, 0, "N.A", latitud, longitud, "0", "0", 0 ,0, imagenes_marcadores, tiposDeporteLugars));
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

        usermarkeroptions.position(miposicion);
        usermarkeroptions.snippet(postalcode);
        usermarkeroptions.title(addres);
        usermarkeroptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f);

        /*mMap.addMarker(new MarkerOptions().position(miposicion).title(addres)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f));*/

        lugares_agregados.add(new Lugar(0, variablesGlobales.generate_code_random(10), 0, addres, "N.A", "Lugares", 0, 0, "N.A", latitud, longitud, "0", "0", 0 ,0, imagenes_marcadores, tiposDeporteLugars));
/*
        final Marker marcador = mMap.addMarker(new MarkerOptions().position(miposicion).title(addres).
                snippet(postalcode).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadordeposicion)).anchor(0.0f, 1.0f));

        marcadoresm.add(marcador);
        pruebam.add(usermarkeroptions);

        //lista_lugares_usuario.add(new Lugar(0, 0, addres, knonname, "N.A", 0, "N.A", latitud, longitud));

        onMarkerClick(marcador);

        deletemarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletemarker.setVisibility(View.INVISIBLE);
            }
        });*/

        //Toast.makeText(this, "Has creado un marcador", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    //metodo para eliminar marcadores y mostrar la informacion de un marcador
    /*
    @Override
    public boolean onMarkerClick(final Marker marker) {
        mMap.setInfoWindowAdapter(new Informacion_marker(LayoutInflater.from(this)));
        //mMap.setOnInfoWindowClickListener(new Informacion_marker(LayoutInflater.from(this)));
        return false;
    }*/
}


