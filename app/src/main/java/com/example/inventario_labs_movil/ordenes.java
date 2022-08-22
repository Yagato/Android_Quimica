package com.example.inventario_labs_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import com.itextpdf.text.DocumentException;

public class ordenes extends AppCompatActivity {
    ArrayList<Material> material;
    EditText et1,et2,et3;
    Button btn_agregar, btn_editar, btn_elim, btn_generar;
    RecyclerView recyclerView_ordenes;
    AdaptadorMaterial ap;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Bitmap bmpHeader, bmpFooter;
    String path = "";
    String nombre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        this.setTitle("Ordenes");
        //path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            path = this.getExternalFilesDir(null).getAbsolutePath() + "/PDF";
        else
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        bmpHeader = BitmapFactory.decodeResource(getResources(), R.drawable.header);
        bmpFooter = BitmapFactory.decodeResource(getResources(), R.drawable.footer);
        btn_agregar = (Button) findViewById(R.id.btn_agregar);
        //btn_editar = (Button) findViewById(R.id.btn_edit);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        btn_elim = (Button) findViewById(R.id.btn_elim);
        btn_generar = (Button) findViewById(R.id.btn_generar);
        recyclerView_ordenes = (RecyclerView) findViewById(R.id.recyclerView_ordenes);
        material = new ArrayList<Material>();
        //material.add(new Material("Silicio","20 gr","C3"));
        LinearLayoutManager l=new LinearLayoutManager(this);
        recyclerView_ordenes.setLayoutManager(l);
        ap =new AdaptadorMaterial();
        recyclerView_ordenes.setAdapter(ap);
    }

    public void agregar(View v)
    {
        if(et1.getText().toString().isEmpty() && et2.getText().toString().isEmpty() && et3.getText().toString().isEmpty()){
            Toast.makeText(ordenes.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
        }else{
            Material material1=new Material(et1.getText().toString(),et2.getText().toString(),et3.getText().toString());
            material.add(material1);
            et1.setText("");
            et2.setText("");
            et3.setText("");
            ap.notifyDataSetChanged();
            recyclerView_ordenes.scrollToPosition(material.size()-1);
        }

    }

    public void editar(View v){
        for(int i = 0; i < material.size(); i++){
            if(material.get(i).getNombre().equals(nombre)){
                material.set(i, new Material(et1.getText().toString().trim(),
                        et2.getText().toString().trim(),
                        et3.getText().toString().trim()));
                et1.setText("");
                et2.setText("");
                et3.setText("");
                ap.notifyDataSetChanged();
                Toast.makeText(this,"Editado correctamente",Toast.LENGTH_SHORT).show();
                nombre = "";
                break;
            }
        }
    }

    public void eliminar(View v)
    {
        int pos=-1;
        for(int f=0;f<material.size();f++)
        {
            if(material.get(f).getNombre().equals(et1.getText().toString()))
                pos=f;
        }
        if (pos!=-1)
        {
            material.remove(pos);
            et1.setText("");
            et2.setText("");
            et3.setText("");
            ap.notifyDataSetChanged();
            Toast.makeText(this,"Se elimino correctamente",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"No existe el material/reactivo solicitado",Toast.LENGTH_SHORT).show();
    }

    public void generarPDF(View view) throws DocumentException, IOException {
        if(material.size() < 1){
            Toast.makeText(ordenes.this, "Lista vacia", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkPermisssion()){
            String[] laboratorio = new String[material.size()];
            String[] reactivo = new String[material.size()];
            String[] cantidad = new String[material.size()];

            for(int i = 0; i < material.size(); i++){
                laboratorio[i] = material.get(i).getLaboratorio();
                reactivo[i] = material.get(i).getNombre();
                cantidad[i] = material.get(i).getCantidad();
            }

            System.out.println(path);
            PdfController pdf = new PdfController(laboratorio, reactivo, cantidad, bmpHeader, bmpFooter, path);
            pdf.generatePDF();
            Toast.makeText(this, "Archivo PDF creado exitosamente", Toast.LENGTH_SHORT).show();
        }
        else{
            requestPermission();
        }
    }

    private boolean checkPermisssion(){
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void mostrar(int pos)
    {
        et1.setText(material.get(pos).getNombre());
        et2.setText(material.get(pos).getCantidad());
        et3.setText(material.get(pos).getLaboratorio());
        nombre = et1.getText().toString().trim();
    }

    private class AdaptadorMaterial extends RecyclerView.Adapter<AdaptadorMaterial.AdaptadorPersonaHolder> {

        @NonNull
        @Override
        public AdaptadorPersonaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorPersonaHolder(getLayoutInflater().inflate(R.layout.item_ordenes,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorPersonaHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return material.size();
        }


        class AdaptadorPersonaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView nombre,cantidad,laboratorio;
            public AdaptadorPersonaHolder(@NonNull View itemView) {
                super(itemView);
                nombre=itemView.findViewById(R.id.nombre);
                cantidad=itemView.findViewById(R.id.cantidad);
                laboratorio=itemView.findViewById(R.id.laboratorio);

                itemView.setOnClickListener(this);
            }

            public void imprimir(int position) {
                nombre.setText("Nombre : "+material.get(position).getNombre());
                cantidad.setText("Cantidad : "+material.get(position).getCantidad());
                laboratorio.setText("Id : "+material.get(position).getLaboratorio());

            }

            @Override
            public void onClick(View v) {
                mostrar(getLayoutPosition());
            }
        }
    }
}