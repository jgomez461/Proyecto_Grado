package com.example.proyecto_grado.fragments.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.Lugar;
import com.example.proyecto_grado.entidades.TipoDeporte;
import com.example.proyecto_grado.entidades.TiposDeporteLugar;
import com.example.proyecto_grado.entidades.VariablesGlobales;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InformacionMarker extends RecyclerView.Adapter<InformacionMarker.SliderViewHolder> {

    private ViewPager2 viewPager2;
    private List<Lugar> informacion_lugares;
    private Context context;
    private boolean imagenes_bool = false;
    InformacionMarker.posicion miposicion;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue2;
    JsonObjectRequest jsonObjectRequest2;
    VariablesGlobales variablesGlobales = new VariablesGlobales();
    List<String> imagenes_marcadores_seleccionados = new ArrayList<>();

    public InformacionMarker(List<Lugar> informacion_lugares, ViewPager2 viewPager2, Context context, posicion miposicion) {
        this.informacion_lugares = informacion_lugares;
        this.viewPager2 = viewPager2;
        this.context = context;
        this.miposicion = miposicion;
    }

    @NonNull
    @Override
    public InformacionMarker.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion_markers, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InformacionMarker.SliderViewHolder holder, final int position) {
        requestQueue = Volley.newRequestQueue(this.context);
        requestQueue2 = Volley.newRequestQueue(this.context);
        holder.textViewTitulo.setText(informacion_lugares.get(position).getNombre_lugar());
        holder.textViewTipoLugar.setText(ObtenerTipoDeporte(informacion_lugares.get(position).getTipo_lugar_principal(), position));
        holder.textViewDireccion.setText(informacion_lugares.get(position).getDireccion());
        holder.textViewContenido.setText(informacion_lugares.get(position).getDescripcion_lugar());

        for (int i = 0; i < informacion_lugares.size(); i++) {
            Log.d("DATA_I", i + informacion_lugares.get(i).getImagenes().size() + "");
            Log.d("DATA_TI", i + informacion_lugares.get(i).getTiposDeporteLugars().size() + "");
        }

        if (informacion_lugares.get(position).getId() != 0) {
            holder.buttonAgregar.setVisibility(View.GONE);
        } else {
            holder.buttonAgregar.setVisibility(View.VISIBLE);
        }

        informacion_lugares.get(position).setImagenes(cargarImagenesLugar(informacion_lugares.get(position).getId()));
        Log.d("IMAGENES_LUGAR", informacion_lugares.get(position).toString() );
        Log.d("IMAGENES_LUGAR_ID", informacion_lugares.get(position).getImagenes().toString() );

        if( !informacion_lugares.get(position).getImagenes().isEmpty() ){
            cargarimagenreciclerimagenes_comida( position, holder.recylcerimagenes_lugares, informacion_lugares);
        }

        holder.buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(informacion_lugares.get(position).getLatitud(), informacion_lugares.get(position).getLongitud());
                miposicion.posicionMarcador(informacion_lugares.get(position).getCodigo(), informacion_lugares.get(position).getId(), latLng, informacion_lugares.get(position).getDireccion(), 2);
            }
        });

        informacion_lugares.get(position).setTiposDeporteLugars(cargarTiposLugar(informacion_lugares.get(position).getId(), informacion_lugares.get(position).getCodigo()));
        Log.d("CANTI", informacion_lugares.get(position).getTiposDeporteLugars().toString());
        if( informacion_lugares.get(position).getTipo_lugar_principal() == 2 && !informacion_lugares.get(position).getTiposDeporteLugars().isEmpty() ){
            cargarTiposDeporteLugar( position, holder.recylcertiposdeporte, informacion_lugares);
        }

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LatLng latLng = new LatLng(informacion_lugares.get(position).getLatitud(), informacion_lugares.get(position).getLongitud());
                miposicion.posicionMarcador(informacion_lugares.get(position).getCodigo(), informacion_lugares.get(position).getId(), latLng, informacion_lugares.get(position).getDireccion(), 1);
                return false;
            }
        });

    }

    private String ObtenerTipoDeporte(int tipo_lugar_principal, int position) {
        String tipo_lugar = "";
        if( informacion_lugares.get(position).getTipo_lugar_principal() == 1 ){
            tipo_lugar = "Comida";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 2 ){
            tipo_lugar = "Deporte";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 3 ){
            tipo_lugar = "Caminatas";
        }else if( informacion_lugares.get(position).getTipo_lugar_principal() == 4 ){
            tipo_lugar = "Ciclorutas";
        }

        return tipo_lugar;
    }

    @Override
    public int getItemCount() {
        return informacion_lugares.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recylcerimagenes_lugares, recylcertiposdeporte;
        TextView textViewTitulo, textViewContenido, textViewTipoLugar, textViewDireccion;
        Button buttonAgregar;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            recylcerimagenes_lugares = itemView.findViewById(R.id.recyvlerimagenes_marker);
            recylcertiposdeporte = itemView.findViewById(R.id.recyclerinformacion_tipodeporte);
            textViewTitulo = itemView.findViewById(R.id.nombre_lugar);
            textViewTipoLugar = itemView.findViewById(R.id.tipolugar);
            textViewContenido = itemView.findViewById(R.id.descripcion_lugar);
            textViewDireccion = itemView.findViewById(R.id.direccion_lugar);
            buttonAgregar = itemView.findViewById(R.id.agregar);
        }
    }

    RecyclerViewAdapter_imagenes_lugares adaptador;
    RecyclerViewAdapter_tipos_deporte adapter_tipos_deporte;
    private void cargarTiposDeporteLugar( int posicion, RecyclerView recylcertiposdeporte, List<Lugar> informacion_lugares) {
        recylcertiposdeporte.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter_tipos_deporte = new RecyclerViewAdapter_tipos_deporte(informacion_lugares.get(posicion).getTiposDeporteLugars(), context);
        recylcertiposdeporte.setAdapter(adapter_tipos_deporte);
        Log.d( "tipo_deporte",  posicion + "y  " +informacion_lugares.get(posicion).getTiposDeporteLugars()+"");
    }

    private void cargarimagenreciclerimagenes_comida( int posicion, RecyclerView recylcerimagenes_lugares, List<Lugar> informacion_lugares) {
        recylcerimagenes_lugares.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adaptador = new RecyclerViewAdapter_imagenes_lugares(informacion_lugares.get(posicion).getImagenes(), context);
        recylcerimagenes_lugares.setAdapter(adaptador);
        recylcerimagenes_lugares.setPadding(0,0,30, 0);
        Log.d( "Problem_Type2",  posicion + "y  " + informacion_lugares.get(posicion).getImagenes()+"");
    }

    public interface posicion{
        void posicionMarcador(String posicion, int id, LatLng latLng, String direccion_lugar, int codigo_accion);
    }

    private List<String> cargarImagenesLugar( int id ) {
        final List<String> imagenes_lugar = new ArrayList<>();
        imagenes_marcadores_seleccionados.clear();
        //consulta para traer a información de las imagenes de los lugares
        String variablesDB = variablesGlobales.getUrl_DB();
        final String url2 = variablesDB + "ws_consulta_imagenes_lugares.php?id_lugar=" + id + "";

        Log.d("url", url2);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray2 = response.optJSONArray("imagenes_lugar");
                if( jsonArray2.length() > 0 ){
                    try {
                        JSONObject jsonObject2 = null;
                        for (int j=0; j< jsonArray2.length(); j++){
                            Log.d("SIDATA", "si hay "+ url2 + ": " + jsonArray2);
                            jsonObject2 = jsonArray2.getJSONObject(j);
                            imagenes_lugar.add(jsonObject2.getString("ruta_imagen"));
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
        Log.d( "MISIMAGENES", imagenes_marcadores_seleccionados.size()+"");

        if( imagenes_lugar.size() <= 0 ){
            String urlImagenDefecto = "https://firebasestorage.googleapis.com/v0/b/proyectogradogallery.appspot.com/o/imagendefecto%2F5d50d17e9f55d.jpeg?alt=media&token=0d07188c-3a2e-4b13-8012-999896005c13";
            imagenes_lugar.add(urlImagenDefecto);
        }

        return imagenes_lugar;
    }

    private List<TiposDeporteLugar> cargarTiposLugar(final int id, final String codigo_lugar) {
        final List<TiposDeporteLugar> tipo_lugar = new ArrayList<>();

        String variablesDB = variablesGlobales.getUrl_DB();
        final String url3 = variablesDB + "ws_consulta_tipos_deporte_lugar.php?lugar=" + id + " ";
        Log.d("url", url3);
        jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray2 = response.optJSONArray("tipo_deporte_lugar");
                if( jsonArray2.length() > 0 ){
                    try {
                        JSONObject jsonObject2 = null;
                        for (int j=0; j< jsonArray2.length(); j++){
                            jsonObject2 = jsonArray2.getJSONObject(j);
                            tipo_lugar.add( new TiposDeporteLugar( id, codigo_lugar,
                                    jsonObject2.getInt("id_tipo_lugar"), jsonObject2.getString("nombre_tipo") ) );
                            Log.d("SIDATA", "si hay " + url3 + ": " + jsonObject2);
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
        requestQueue2.add(jsonObjectRequest2);

        Log.d("VARIABLES", tipo_lugar.toString() );

        return tipo_lugar;
    }

}
