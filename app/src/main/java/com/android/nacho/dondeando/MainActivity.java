package com.android.nacho.dondeando;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.android.nacho.dondeando.servicio.CapturaCoords;
import com.android.nacho.dondeando.utils.Constantes;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mFirebaseRef;
    private TextView txtNombre;
    private FloatingActionButton fab;
    private boolean guardando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseRef = new Firebase(Constantes.FIREBASE_URL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("DondeAndo");
        setSupportActionBar(toolbar);

        AuthData authData = mFirebaseRef.getAuth();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.green));
        fab.setOnClickListener(this);

        txtNombre = (TextView)findViewById(R.id.txtNombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            if (guardando == false) {
                // si es falso, empieza a guardar la ruta (inicia el servicio)
                startService(new Intent(this, CapturaCoords.class));
                txtNombre.setText("Guardando ruta...");
                guardando = true;
            }
            else {
                // Si ya esta guardando, para el servicio.
                stopService(new Intent(this, CapturaCoords.class));
                txtNombre.setText("");

            }
        }
    }
}
