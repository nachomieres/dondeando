package com.android.nacho.dondeando;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogoRuta.DialogoRutaListener {
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
        setSupportActionBar(toolbar);
        AuthData authData = mFirebaseRef.getAuth();
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoRuta dialogo = new DialogoRuta();
                dialogo.show(fragmentManager, "Alerta");
                guardando = true;
            }
            else {
                // Si ya esta guardando, para el servicio.
                stopService(new Intent(this, CapturaCoords.class));
                txtNombre.setText("");
                fab.setImageResource(R.drawable.ic_play);
                guardando = false;

            }
        }
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        fab.setImageResource(R.drawable.ic_pausa);
        Log.i("DIALOGO", "onFinishEditDialog: OK ");
        txtNombre.setText("Guardando ruta "+ inputText);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
        }
        Intent intent = new Intent(this, CapturaCoords.class);
        intent.putExtra("nombre", inputText);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                //Si la petición es cancelada, el resultado estará vacío.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("PERMISO", "onRequestPermissionsResult: CONCEDIDO");

                } else
                {
                    //Permiso denegado. Desactivar la funcionalidad que dependía de dicho permiso.
                }
                return;
            }

            // A continuación, se expondrían otras posibilidades de petición de permisos.
        }
    }
}
