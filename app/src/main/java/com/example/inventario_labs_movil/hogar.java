package com.example.inventario_labs_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class hogar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hogar);
    }
    public void btn_inv (View vista){
        Intent intent= new Intent (hogar.this, inventario.class);
        startActivity(intent);
    }
    public void btn_ord (View vista){
        Intent intent= new Intent (hogar.this, ordenes.class);
        startActivity(intent);
    }
    public void btn_usu (View vista){
        Intent intent= new Intent (hogar.this, usuario.class);
        startActivity(intent);
    }
    public void btn_info (View vista){
        Intent intent= new Intent (hogar.this, informacion.class);
        startActivity(intent);
    }
}