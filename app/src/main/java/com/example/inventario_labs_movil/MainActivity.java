package com.example.inventario_labs_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText correo, contra;
    FirebaseAuth mAuth;
    private FirebaseFirestore mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.Correo);
        contra = findViewById(R.id.Contra);
        btn_login = findViewById(R.id.btn_login);

        mfirestore = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correoUsr = correo.getText().toString().trim();
                String contraUsr = contra.getText().toString().trim();

                if(!correoUsr.isEmpty() && !contraUsr.isEmpty()){
                    loginUsr(correoUsr,contraUsr);
                }else{
                    Toast.makeText(MainActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,hogar.class));
                }


            }
        });

    }

    private void loginUsr(String correoUsr, String contraUsr) {
        /*if (correoUsr == mfirestore.collection("Usuarios").document("correo").getPath() || contraUsr ==
                mfirestore.collection("Usuarios").document("contraseña").getPath()){
                    startActivity(new Intent(MainActivity.this,hogar.class));
                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    finish();
        } else {
            Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
        }*/
        correoUsr = "carlos.3312@hotmail.es";
        contraUsr = "carlos1234";


        mAuth.signInWithEmailAndPassword(correoUsr,contraUsr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,hogar.class));
                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}