package com.example.inventario_labs_movil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventario_labs_movil.model.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class usuario extends AppCompatActivity {

    Button btn_rest;
    EditText etCorreo, etPass, etNuevaPass, etPassConfirm;
    private String password = "", email = "", nuevaPass = "", passConfirm = "";
    //private FirebaseAuth mAuth;
    //private ProgressDialog mDialog;
    private Usuarios user = null;
    private String userID = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        /*mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);*/
        btn_rest = findViewById(R.id.Btn_reset);
        etCorreo = findViewById(R.id.Edt_Email);
        etPass = findViewById(R.id.Edt_PassActual);
        etNuevaPass = findViewById(R.id.Edt_NuevoPass);
        etPassConfirm = findViewById(R.id.Edt_NuevoPassConfirm);

        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etCorreo.getText().toString().trim();
                password = etPass.getText().toString().trim();
                nuevaPass = etNuevaPass.getText().toString().trim();
                passConfirm = etPassConfirm.getText().toString().trim();

                if(email.equals("") || password.equals("") || nuevaPass.equals("") || passConfirm.equals("")){
                    Toast.makeText(usuario.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!nuevaPass.equals(passConfirm)){
                    Toast.makeText(usuario.this, "Las nuevas contrasenias no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchUser(email, password);

                if(userID.equals(""))
                    return;

                if(user.getContraseña().equals(nuevaPass)){
                    Toast.makeText(usuario.this, "Contrasenia nueva igual a la actual", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> update = new HashMap<>();
                update.put("contraseña", nuevaPass);

                db.collection("Usuarios")
                        .document(userID)
                        .update(update);

                Toast.makeText(usuario.this, "Contrasenia actualizada", Toast.LENGTH_SHORT).show();

                finish();

                /*correo = etCorreo.getText().toString();
                if(!correo.isEmpty()){
                    mDialog.setMessage("Espera un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPass();
                }else{
                    Toast.makeText(usuario.this, "Debe ingresar el correo", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }

    private void searchUser(String email, String password){
        db.collection("Usuarios")
                .whereEqualTo("correo", email)
                .whereEqualTo("contraseña", password)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            System.err.println("Listen failed" + error);
                            return;
                        }

                        for(DocumentSnapshot doc : value){
                            user = doc.toObject(Usuarios.class);
                            userID = doc.getId();
                        }
                    }
                });
    }

    private void resetPass() {
        /*mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ( task.isSuccessful()){
                    Toast.makeText(usuario.this, "Se ha enviado un correo para restablecer tu contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(usuario.this, "No se pudo enviar el correo de restablecer contraseña", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });*/
    }
}