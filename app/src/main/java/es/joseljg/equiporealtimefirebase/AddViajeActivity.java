package es.joseljg.equiporealtimefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.joseljg.equiporealtimefirebase.clases.Viaje;
import es.joseljg.equiporealtimefirebase.controladores.ViajeFirebaseController;

public class AddViajeActivity extends AppCompatActivity {

    EditText edt_add_idviaje;
    EditText edt_add_origen;
    EditText edt_add_destino;
    EditText edt_add_precio;
    ImageView img_add_foto;

    Viaje v;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viaje);
        edt_add_idviaje = (EditText) findViewById(R.id.edt_add_idviaje);
        edt_add_origen = (EditText) findViewById(R.id.edt_add_origen);
        edt_add_destino = (EditText) findViewById(R.id.edt_add_destino);
        edt_add_precio = (EditText) findViewById(R.id.edt_add_precio);
        img_add_foto = (ImageView) findViewById(R.id.img_add_foto);
    }

    public void insertar_viaje(View view) {
        int idviaje = Integer.valueOf(String.valueOf(edt_add_idviaje.getText()));
        String origen = String.valueOf(edt_add_origen.getText());
        String destino = String.valueOf(edt_add_destino.getText());
        Double precio = Double.valueOf(String.valueOf(edt_add_precio.getText()));

        if(imagen_seleccionada != null)
        {
            //----------- convierto el imageView a Bitmap
            img_add_foto.buildDrawingCache();
            Bitmap bm_foto = img_add_foto.getDrawingCache();
            v = new Viaje(origen, destino, precio, bm_foto);
        }
        else{
            v = new Viaje(idviaje,origen, destino, precio);
        }
        new ViajeFirebaseController().insertarViaje(new ViajeFirebaseController.ViajeStatus() {
            @Override
            public void viajeIsLoaded(List<Viaje> viajes, List<String> keys) {

            }

            @Override
            public void viajeIsAdd() {
                // aquí hay que poner cuando se haya insertado bien qué hacer
                Toast.makeText(AddViajeActivity.this,"insercion correcta",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void viajeIsUpdate() {

            }

            @Override
            public void viajeIsDelete() {

            }
        },v);

    }
//------------------------------------------------------------------------------------------------
    //--------CODIGO PARA CAMBIAR LA IMAGEN----------------
    public void cambiar_imagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                img_add_foto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}