package com.example.proyecto_grado;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.proyecto_grado.R;

public class Login_users extends AppCompatActivity {

    private TextView registro;
    private EditText codigo;
    private EditText clave;
    private Button ingreso;
    private ImageButton icono;
    private boolean bandera = false;
    private String code_user = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_users);

        registro = (TextView) findViewById(R.id.registro);
        codigo = (EditText) findViewById(R.id.codigo);
        clave = (EditText) findViewById(R.id.clave);
        ingreso = (Button) findViewById(R.id.ingreso);
        icono = (ImageButton) findViewById(R.id.key);

        // esta sección la usamos para cambiar el icono del password pero no está funcionando aún
        clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera = true;
            }
        });

        if( bandera ){
            icono.performClick();
            icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    icono.setImageResource(R.drawable.cambio_icono_password);
                }
            });
        }
        // hasta aquí va

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( codigo.getText().length() > 0 && clave.getText().length() > 0 ){
                    Toast.makeText(Login_users.this, "hay daatos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login_users.this, "No hay daatos", Toast.LENGTH_SHORT).show();
                }
            }
        });



        registro.setPaintFlags(registro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}