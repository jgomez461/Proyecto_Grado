package com.example.proyecto_grado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.entidades.VariablesGlobales;
import com.example.proyecto_grado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register_users extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static final int MY_DEFAULT_TIMEOUT = 50000;
    private EditText nombre;
    private EditText usuario;
    private EditText codigo_acceso;
    private EditText clave;
    private Button registro;
    private ImageButton random;
    private TextView ingreso;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        nombre = (EditText) findViewById(R.id.name);
        usuario = (EditText) findViewById(R.id.user);
        codigo_acceso = (EditText) findViewById(R.id.code);
        clave = (EditText) findViewById(R.id.clave_r);
        registro = (Button) findViewById(R.id.registro_usuario) ;
        random = (ImageButton) findViewById(R.id.code_random) ;
        ingreso = (TextView) findViewById(R.id.ingreso) ;
        ingreso.setPaintFlags(registro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        requestQueue = Volley.newRequestQueue( this);

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_users.this, Login_users.class);
                startActivity(intent);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aleatorio = generate_code_random((int) Math.floor(Math.random()*(12-9+1)+9));
                codigo_acceso.setText("");
                codigo_acceso.setText(aleatorio);
            }
        });
        Context context = this;
        SharedPreferences usario_datos = getSharedPreferences("datos_usuario", context.MODE_PRIVATE);
    }

    private String generate_code_random(int size) {

        String number = "";

        for (int i = 1; i <= size ; i++){
            int Ra = (int)(Math.random()*9);
            number += String.valueOf(Ra);
        }

        return number;
    }

    VariablesGlobales variablesGlobales = new VariablesGlobales();
    private void cargarWebService() {
        progressDialog = new ProgressDialog(this);

        if( !nombre.getText().toString().isEmpty() && !usuario.getText().toString().isEmpty() ){

            progressDialog.setMessage("Cargando, por favor espere...");
            progressDialog.show();

            String variablesDB = variablesGlobales.getUrl_DB();
            String url = variablesDB + "ws_registro_user.php?nombre="+
                    nombre.getText().toString()+
                    "&usuario="+usuario.getText().toString()+
                    "&codigo_acceso="+codigo_acceso.getText().toString()
                    +"&clave="+clave.getText().toString()+" ";

            Log.d("url", url);

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        Toast.makeText(this, R.string.registro_correcto, Toast.LENGTH_SHORT).show();

        JSONArray jsonArray = response.optJSONArray("usuario");
        guardarUsuario(jsonArray);

        nombre.setText("");
        usuario.setText("");
        codigo_acceso.setText("");
        clave.setText("");

        Intent intent = new Intent(Register_users.this, MapsActivity.class);
        startActivity(intent);
    }

    private void guardarUsuario(JSONArray jsonArray) {
        Usuario usuario_info = new Usuario();
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = usario_datos.edit();
            editor.putString("Usuario", usuario.getText().toString());
            editor.putString("nombre", usuario.getText().toString());
            editor.putString("codigo_acceso", jsonObject.optString("codigo_acceso"));
            editor.putInt("registro", 1);
            editor.putInt("tipo_usuario", 1);
            editor.putInt("id", jsonObject.optInt("id"));
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        error.printStackTrace();
        Toast.makeText(this, "No se pudo registrar un error: " + error.getMessage(), Toast.LENGTH_SHORT).show();


    }
}