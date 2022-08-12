package com.example.inventario_labs_movil.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventario_labs_movil.Agregar_react_inv;
import com.example.inventario_labs_movil.R;
import com.example.inventario_labs_movil.agregar_inv;
import com.example.inventario_labs_movil.model.producto;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class productoAdapter extends FirestoreRecyclerAdapter<producto, productoAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FragmentManager fm;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public productoAdapter(@NonNull FirestoreRecyclerOptions<producto> options, Activity activity, FragmentManager fm) {
        super(options);
        this.activity = activity;
        this.fm = fm;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull producto producto) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String Id = documentSnapshot.getId();

        viewHolder.Nombre.setText(producto.getNombre());
        viewHolder.Id.setText(producto.getId());
        viewHolder.Cantidad.setText(producto.getCantidad());

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, agregar_inv.class);
                i.putExtra("Id_produc", Id);
                //activity.startActivity(i);
               Agregar_react_inv Agregar_react_inv = new Agregar_react_inv();
                Bundle bundle = new Bundle();
                bundle.putString("Id_produc",Id);
                Agregar_react_inv.setArguments(bundle);
                Agregar_react_inv.show(fm,"open");
            }
        });

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            deleteProduct(Id);
            }
        });
    }

    private void deleteProduct(String id) {
        mFirestore.collection("Productos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Nombre, Id, Cantidad;
        ImageView btn_delete, btn_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.nombre);
            Id = itemView.findViewById(R.id.laboratorio);
            Cantidad = itemView.findViewById(R.id.cantidad);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
