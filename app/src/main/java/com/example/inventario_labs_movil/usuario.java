package com.example.inventario_labs_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class usuario extends AppCompatActivity {
    Button btn_rest;
    EditText etCorreo;
    private String correo = "";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        btn_rest = (Button) findViewById(R.id.btn_rest);
        etCorreo = (EditText) findViewById(R.id.etCorreo);

        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = etCorreo.getText().toString();
                if(!correo.isEmpty()){
                    mDialog.setMessage("Espera un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPass();
                }else{
                    Toast.makeText(usuario.this, "Debe ingresar el correo", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void resetPass() {
        mAuth.setLanguageCode("es");
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
        });
    }
}