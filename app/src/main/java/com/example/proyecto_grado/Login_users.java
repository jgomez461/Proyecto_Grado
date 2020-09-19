package com.example.proyecto_grado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grado.Clases.VariablesGlobales;
import com.example.proyecto_grado.R;
import com.example.proyecto_grado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_users extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static final int MY_DEFAULT_TIMEOUT = 50000;
    private TextView registro;
    private EditText codigo;
    private EditText clave;
    private Button ingreso;
    private ImageButton icono;
    private boolean bandera = false;
    private String code_user = "";
    private String password = "";

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verficarsesion();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_users);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        registro = (TextView) findViewById(R.id.registro);
        codigo = (EditText) findViewById(R.id.codigo);
        clave = (EditText) findViewById(R.id.clave);
        ingreso = (Button) findViewById(R.id.ingreso);
        icono = (ImageButton) findViewById(R.id.key);
        registro.setPaintFlags(registro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        requestQueue = Volley.newRequestQueue( this );

        //minetras tanto trabajamos con esta
        icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //145 Texto sin Ocultar
                //129 Texto Oculto
                int n = clave.getInputType();
                if (n == 129) {
                    clave.setInputType(145);
                    icono.setImageResource(R.drawable.key_visibility_on);
                } else {
                    clave.setInputType(129);
                    icono.setImageResource(R.drawable.key_visibility_off);
                }
            }
        });
        //fin


        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        /*
        //esto lo usamos para verificar los datos del usuario y la contraseña
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( codigo.getText().length() > 0 && clave.getText().length() > 0 ){
                    code_user = codigo.getText().toString();
                    code_user = codigo.getText().toString();
                    password = clave.getText().toString();
                    String prueba_u = "hola";
                    String prueba_p = "123";
                    if( code_user.equals(prueba_u) && password.equals(prueba_p) ){
                        Intent intent = new Intent(Login_users.this, MapsActivity.class);
                        intent.putExtra("code", codigo.getText().toString());
                        startActivity(intent);
                    }else{
                        alert_dialog(R.string.wrong, R.string.login_wrong, R.string.try_again);
                    }
                }else{
                    alert_dialog(R.string.wrong, R.string.empty_fields, R.string.try_again);
                }
            }
        });*/

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_users.this, Register_users.class);
                startActivity(intent);
            }
        });

    }

    VariablesGlobales variablesGlobales = new VariablesGlobales();

    private void loginUser() {
        if( codigo.getText().length() > 0 && clave.getText().length() > 0 ){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Ingreso App");
            progressDialog.setMessage("Verificando datos...");
            progressDialog.show();

            String variablesDB = variablesGlobales.getUrl_DB();
            String url = variablesDB + "ws_consulta_usuario.php?usuario="+
                    codigo.getText().toString() +"&codigo_acceso="+
                    codigo.getText().toString() +"&clave="+
                    clave.getText().toString()+"";
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{
            alert_dialog(R.string.wrong, R.string.empty_fields, R.string.try_again);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        alert_dialog(R.string.wrong, R.string.login_wrong, R.string.try_again);
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();

        JSONArray jsonArray = response.optJSONArray("usuario");
        if( guardarUsuario(jsonArray) ){
            Intent intent = new Intent(Login_users.this, MapsActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, R.string.login_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    public void alert_dialog( int titulo, int contendio, int boton){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(contendio);
        builder.setPositiveButton(boton, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean guardarUsuario(JSONArray jsonArray) {
        Usuario usuario_info = new Usuario();
        JSONObject jsonObject = null;
        boolean bandera = false;
        try {
            jsonObject = jsonArray.getJSONObject(0);

            if( ( codigo.getText().toString().equals(jsonObject.optString("codigo_acceso")) ||
                    codigo.getText().toString().equals(jsonObject.optString("usuario")))
                    && clave.getText().toString().equals(jsonObject.optString("clave")) ){
                bandera = true;
                SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
                SharedPreferences.Editor editor = usario_datos.edit();
                editor.putString("Usuario", jsonObject.optString("usuario"));
                editor.putString("nombre", jsonObject.optString("nombre"));
                editor.putString("codigo_acceso", jsonObject.optString("codigo_acceso"));
                editor.putInt("registro", 0);
                if( codigo.getText().toString().equals(jsonObject.optString("codigo_acceso")) ){
                    editor.putInt("tipo_usuario", 2);// 2 es para los que no son el principal
                }else{
                    editor.putInt("tipo_usuario", 1);// 1 es para el usuario principal
                }
                editor.putInt("id", jsonObject.optInt("id"));
                editor.commit();
            }else {
                bandera = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bandera;
    }

    public boolean verficarsesion(){

        boolean bandera = false;

        SharedPreferences usario_datos = getSharedPreferences("Usuario_info", this.MODE_PRIVATE);
        int id = usario_datos.getInt("id", 0);
        if( !(id == 0) ){
            Intent intent = new Intent(Login_users.this, MapsActivity.class);
            startActivity(intent);
        }

        return bandera;
    }
}