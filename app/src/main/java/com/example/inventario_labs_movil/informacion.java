package com.example.inventario_labs_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class informacion extends AppCompatActivity {
    TextView tvDes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        tvDes = findViewById(R.id.tvDes);
        tvDes.setClickable(true);
        tvDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/file/d/1OLaxkW3dQLddjr6yOfk0j-l6RaRd2ZH0/view?usp=sharing");
            }
        });

        SpannableString miText = new SpannableString("Descargar Aqui");
        miText.setSpan(new UnderlineSpan(),0,miText.length(),0);
        tvDes.setText(miText);

    }

    private void gotoUrl(String s) {
            Uri uri = Uri.parse(s);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }

}