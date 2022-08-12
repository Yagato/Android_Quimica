package com.example.inventario_labs_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class agregar_inv extends AppCompatActivity {
    Button btn_aceptar;
    EditText nombre, cantidad, laboratorio;
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_inv);

        this.setTitle("Agregar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_produc");
        mfirestore = FirebaseFirestore.getInstance(); //Se apunta a la bas de datos

        nombre = findViewById(R.id.nombre);
        cantidad = findViewById(R.id.cantidad);
        laboratorio = findViewById(R.id.laboratorio);
        btn_aceptar = findViewById(R.id.btn_aceptar);

        if (id == null || id == ""){
            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreReactivo = nombre.getText().toString().trim();
                    String cantidadReactivo = cantidad.getText().toString().trim();
                    String labResponsable = laboratorio.getText().toString().trim();


                    if (nombreReactivo.isEmpty() && cantidadReactivo.isEmpty() && labResponsable.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                    }else{
                        postReact(nombreReactivo,cantidadReactivo,labResponsable);
                    }

                }
            });
        }else{
            btn_aceptar.setText("Update");
            getProduc(id);
            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreReactivo = nombre.getText().toString().trim();
                    String cantidadReactivo = cantidad.getText().toString().trim();
                    String labResponsable = laboratorio.getText().toString().trim();

                    if (nombreReactivo.isEmpty() && cantidadReactivo.isEmpty() && labResponsable.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                    }else{
                        updateReact(nombreReactivo,cantidadReactivo,labResponsable, id);
                    }
                }
            });
        }



    }

    private void updateReact(String nombreReactivo, String cantidadReactivo, String labResponsable, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", nombreReactivo);
        map.put("Cantidad", cantidadReactivo);
        map.put("Id", labResponsable);

        mfirestore.collection("Productos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Actualizado correctamente.",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al actualizar.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postReact(String nombreReactivo, String cantidadReactivo, String labResponsable) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", nombreReactivo);
        map.put("Cantidad", cantidadReactivo);
        map.put("Id", labResponsable);

        mfirestore.collection("Productos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Agregado correctamente.",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al ingresar.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduc (String id){
        mfirestore.collection("Productos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreReactivo = documentSnapshot.getString("Nombre");
                String cantidadReactivo = documentSnapshot.getString("Cantidad");
                String labResponsable = documentSnapshot.getString("Id");
                nombre.setText(nombreReactivo);
                cantidad.setText(cantidadReactivo);
                laboratorio.setText(labResponsable);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al obtener los datos.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}