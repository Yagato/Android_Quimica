package com.example.inventario_labs_movil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inventario_labs_movil.adapter.productoAdapter;
import com.example.inventario_labs_movil.model.producto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class inventario extends AppCompatActivity {
Button btnAgregar;
RecyclerView mRecycler;
productoAdapter mAdapter;
FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("Productos");

        FirestoreRecyclerOptions<producto> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<producto>().setQuery(query, producto.class).build();

        mAdapter = new productoAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        this.setTitle("Inventarios");

        Button btnAgregar= (Button) findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Agregar_react_inv fm = new Agregar_react_inv();
                fm.show(getSupportFragmentManager(),"");
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}