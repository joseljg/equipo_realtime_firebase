package es.joseljg.equiporealtimefirebase.controladores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.joseljg.equiporealtimefirebase.clases.Viaje;

public class ViajeFirebaseController {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Viaje> viajes;

    public interface ViajeStatus
    {
        void viajeIsLoaded(List<Viaje> viajes, List<String> keys);
        void viajeIsAdd();
        void viajeIsUpdate();
        void viajeIsDelete();
    }

    public ViajeFirebaseController() {
        this.mDatabase  = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("viajes");
        this.viajes  = new ArrayList<Viaje>();
    }

    public void obtener_viajes(final ViajeStatus viajeStatus)
    {
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viajes.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keynode: snapshot.getChildren())
                {
                    keys.add(keynode.getKey());
                    Viaje v = keynode.getValue(Viaje.class);
                    viajes.add(v);
                }
                viajeStatus.viajeIsLoaded(viajes,keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //---------------------------------------------------------------------------------
    public void insertarViaje(final ViajeStatus viajeStatus, Viaje v)
    {
        //this.myRef.child(v.getIdviaje()).setValue(v)
        this.myRef.push().setValue(v).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                viajeStatus.viajeIsAdd();
                Log.i("firebasedb", "insercion correcta2");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "insercion incorrecta2");
                    }
                });
    }
    //---------------------------------------------------------------------------------
    public void borrarViaje(final ViajeStatus viajeStatus, String key)
    {
        this.myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                viajeStatus.viajeIsDelete();
                Log.i("firebasedb", "borrado correcto2");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "borrado incorrecto2");
                    }
                });
    }
    //---------------------------------------------------------------------------------
    public void actualizarViaje(final ViajeStatus viajeStatus, String key, Viaje v)
    {
        Map<String, Object> nuevoViaje = new HashMap<String,Object>();
        nuevoViaje.put(key,v);
        myRef.updateChildren(nuevoViaje).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                viajeStatus.viajeIsUpdate();
                Log.i("firebasedb", "actualizacion correcta2");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "actualizacion incorrecta2");
                    }
                });
    }
    //---------------------------------------------------------------------------------
}
