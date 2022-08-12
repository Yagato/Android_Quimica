package com.example.inventario_labs_movil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Agregar_react_inv extends DialogFragment {

    String id_produc;
    Button btn_aceptar;
    EditText nombre, cantidad, laboratorio;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id_produc = getArguments().getString("id_produc");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_agregar_react_inv, container, false);
        mfirestore = FirebaseFirestore.getInstance();

        nombre = v.findViewById(R.id.nombre);
        cantidad = v.findViewById(R.id.cantidad);
        laboratorio = v.findViewById(R.id.laboratorio);
        btn_aceptar = v.findViewById(R.id.btn_aceptar);

        if(id_produc==null || id_produc==""){
            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreReactivo = nombre.getText().toString().trim();
                    String cantidadReactivo = cantidad.getText().toString().trim();
                    String labResponsable = laboratorio.getText().toString().trim();

                    if (nombreReactivo.isEmpty() && cantidadReactivo.isEmpty() && labResponsable.isEmpty()) {
                        Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                    }else{
                        postReact(nombreReactivo,cantidadReactivo,labResponsable);
                    }

                }
            });
        }else{
            getReact();
            btn_aceptar.setText("Actualizar");
            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreReactivo = nombre.getText().toString().trim();
                    String cantidadReactivo = cantidad.getText().toString().trim();
                    String labResponsable = laboratorio.getText().toString().trim();


                    if (nombreReactivo.isEmpty() && cantidadReactivo.isEmpty() && labResponsable.isEmpty()) {
                        Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                    }else{
                        updateReact(nombreReactivo,cantidadReactivo,labResponsable);
                    }

                }
            });
        }




        return v;
    }

    private void updateReact(String nombreReactivo, String cantidadReactivo, String labResponsable) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", nombreReactivo);
        map.put("Cantidad", cantidadReactivo);
        map.put("Id", labResponsable);

        mfirestore.collection("Productos").document(id_produc).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Actualizado correctamente.",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al actualizar.",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),"Agregado correctamente.",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al ingresar.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReact(){
        mfirestore.collection("Productos").document(id_produc).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                Toast.makeText(getContext(),"Error al obtener los datos.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}