package es.joseljg.equiporealtimefirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import es.joseljg.equiporealtimefirebase.clases.ListaViajesAdapter;
import es.joseljg.equiporealtimefirebase.clases.Viaje;
import es.joseljg.equiporealtimefirebase.controladores.ViajeFirebaseController;

public class mostrarviajes extends AppCompatActivity {

    private RecyclerView rv_viajes = null;
    private ListaViajesAdapter mAdapter;
    private ArrayList<Viaje> viajes;
    private ArrayList<String> keys;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(mostrarviajes.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(mostrarviajes.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void salir(View view) {
        Intent intent = new Intent(mostrarviajes.this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarviajes);
        mAuth = FirebaseAuth.getInstance();
        rv_viajes = findViewById(R.id.rv_viajes);
        mAdapter = new ListaViajesAdapter(this);
        new ViajeFirebaseController().obtener_viajes(new ViajeFirebaseController.ViajeStatus() {
            @Override
            public void viajeIsLoaded(List<Viaje> viajes, List<String> keys) {
                mAdapter.setListaViajes(viajes);
                mAdapter.setKeys(keys);
            }

            @Override
            public void viajeIsAdd() {

            }

            @Override
            public void viajeIsUpdate() {

            }

            @Override
            public void viajeIsDelete() {

            }
        });

        //------------------------------------------------------------
        rv_viajes.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_viajes.setLayoutManager(new LinearLayoutManager(this));
        } else {
           // rv_viajes.setLayoutManager(new GridLayoutManager(this, NUMERO_DE_COLUMNAS));
        }
        // rv_viajes.setLayoutManager(new LinearLayoutManager(this));
        //------------------------------------------------------------
    }

    //------------------------------------------------------------------
    public void addviaje1(View view) {
        Intent intent = new Intent(this,AddViajeActivity.class);
        startActivity(intent);
    }
}