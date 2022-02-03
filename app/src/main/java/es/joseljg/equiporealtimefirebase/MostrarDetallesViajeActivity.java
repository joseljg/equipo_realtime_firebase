package es.joseljg.equiporealtimefirebase;

import static es.joseljg.equiporealtimefirebase.clases.ViajeViewHolder.EXTRA_OBJETO_IMG_VIAJE;
import static es.joseljg.equiporealtimefirebase.clases.ViajeViewHolder.EXTRA_OBJETO_VIAJE;
import static es.joseljg.equiporealtimefirebase.clases.ViajeViewHolder.EXTRA_OBJETO_VIAJE_KEY;
import static es.joseljg.equiporealtimefirebase.utilidades.ImagenesBlobBitmap.bytes_to_bitmap;
import static es.joseljg.equiporealtimefirebase.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import es.joseljg.equiporealtimefirebase.clases.Configuracion;
import es.joseljg.equiporealtimefirebase.clases.Viaje;
import es.joseljg.equiporealtimefirebase.controladores.ViajeFirebaseController;
import es.joseljg.equiporealtimefirebase.utilidades.ImagenesFirebase;

public class MostrarDetallesViajeActivity extends AppCompatActivity {

    private EditText edt_detalles_idviaje;
    private EditText edt_detalles_origen;
    private EditText edt_detalles_destino;
    private EditText edt_detalles_precio;
    private ImageView img_detalles_foto;
    private String key;
    private Viaje v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles_viaje);
        edt_detalles_idviaje = (EditText) findViewById(R.id.edt_detalles_idviaje);
        edt_detalles_origen = (EditText) findViewById(R.id.edt_detalles_origen);
        edt_detalles_destino = (EditText) findViewById(R.id.edt_detalles_destino);
        edt_detalles_precio = (EditText) findViewById(R.id.edt_detalles_precio);
        img_detalles_foto = (ImageView) findViewById(R.id.img_detalles_foto);
        Intent intent = getIntent();
        if (intent != null) {
            v = (Viaje) intent.getSerializableExtra(EXTRA_OBJETO_VIAJE);
            key = intent.getStringExtra(EXTRA_OBJETO_VIAJE_KEY);
            edt_detalles_idviaje.setText(String.valueOf(v.getIdviaje()));
            edt_detalles_origen.setText(v.getOrigen());
            edt_detalles_destino.setText(v.getDestino());
            edt_detalles_precio.setText(String.valueOf(v.getPrecio()));
            if (v.getFoto() != null) {
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            img_detalles_foto.setImageBitmap(fotob);
                        }
                        else{
                            Log.i("firebasedb","foto no descargada correctamente");
                        }
                    }
                    @Override
                    public void FotoIsUpload() {
                    }
                    @Override
                    public void FotoIsDelete() {
                    }
                },v.getFoto());

            }
            else{
                // holder.img_rv_viaje_foto.setImageResource(R.drawable.foto_viaje);
                // holder.img_rv_viaje_foto.setBackgroundResource(R.drawable.foto_viaje);
            }
        }


    }

    //-----------------------------------------------------------------------------------------------

    public void borrar_viaje(View view) {

        new ViajeFirebaseController().borrarViaje(new ViajeFirebaseController.ViajeStatus() {
            @Override
            public void viajeIsLoaded(List<Viaje> viajes, List<String> keys) {

            }

            @Override
            public void viajeIsAdd() {

            }

            @Override
            public void viajeIsUpdate() {

            }

            @Override
            public void viajeIsDelete() {
                // aquí hay que poner cuando se haya borrado bien qué hacer
                Toast.makeText(MostrarDetallesViajeActivity.this, "borrado correcto", Toast.LENGTH_LONG).show();
                finish();
            }
        }, key);
        new ImagenesFirebase().borrarFoto(new ImagenesFirebase.FotoStatus() {
            @Override
            public void FotoIsDownload(byte[] bytes) {
            }
            @Override
            public void FotoIsDelete() {
            }
            @Override
            public void FotoIsUpload() {
                Toast.makeText(MostrarDetallesViajeActivity.this, "foto eliminada correcto", Toast.LENGTH_LONG).show();
            }
        },v.getFoto());

    }
    //-----------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------
    public void Actualizar_viaje(View view) {
        int idviaje = Integer.valueOf(String.valueOf(edt_detalles_idviaje.getText()));
        String origen = String.valueOf(edt_detalles_origen.getText());
        String destino = String.valueOf(edt_detalles_destino.getText());
        Double precio = Double.valueOf(String.valueOf(edt_detalles_precio.getText()));
        Viaje v = new Viaje(idviaje,origen, destino, precio);
        new ViajeFirebaseController().actualizarViaje(new ViajeFirebaseController.ViajeStatus() {
            @Override
            public void viajeIsLoaded(List<Viaje> viajes, List<String> keys) {

            }

            @Override
            public void viajeIsAdd() {

            }

            @Override
            public void viajeIsUpdate() {
                // aquí hay que poner cuando se haya actualizado bien qué hacer
                Toast.makeText(MostrarDetallesViajeActivity.this,"actualizacion correcta",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void viajeIsDelete() {

            }
        },key,v);
    }

    public void salir(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
    //-----------------------------------------------------------------------------------------------


}