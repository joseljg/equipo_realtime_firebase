package es.joseljg.equiporealtimefirebase.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.joseljg.equiporealtimefirebase.R;


public class ListaViajesAdapter extends RecyclerView.Adapter<ViajeViewHolder>{
    private Context c;
    private List<Viaje> listaViajes;
    private List<String> keys;
    private LayoutInflater mInflater;

    public void setC(Context c) {
        this.c = c;
        this.listaViajes = new ArrayList<Viaje>();
    }
    public ListaViajesAdapter(Context c, List<Viaje> listaViajes,List<String> keys) {
        this.c = c;
        this.listaViajes = listaViajes;
        this.keys = keys;
        mInflater = LayoutInflater.from(c);
    }

    public Context getC() {
        return c;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Viaje> getListaViajes() {
        return listaViajes;
    }

    public void setListaViajes(List<Viaje> listaViajes) {
        this.listaViajes = listaViajes;
        notifyDataSetChanged();
    }

    public ListaViajesAdapter(Context c) {
        this.c = c;
        mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_recyclerview_viaje, parent, false);
        return new ViajeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        if(listaViajes!=null) {
            Viaje viaje_actual = listaViajes.get(position);
            holder.txt_rv_viaje_idviaje.setText("IdViaje: " + viaje_actual.getIdviaje());
            holder.txt_rv_viaje_origen.setText(String.valueOf("origen: " + viaje_actual.getOrigen()));
            holder.txt_rv_viaje_destino.setText(String.valueOf("destino: " + viaje_actual.getDestino()));
            if (viaje_actual.getFoto() != null) {
                holder.img_rv_viaje_foto.setImageBitmap(viaje_actual.getFoto());
            }
            else{
               // holder.img_rv_viaje_foto.setImageResource(R.drawable.foto_viaje);
               // holder.img_rv_viaje_foto.setBackgroundResource(R.drawable.foto_viaje);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (listaViajes != null)
            return listaViajes.size();
        else return 0;
    }
}
