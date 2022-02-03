package es.joseljg.equiporealtimefirebase.clases;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Objects;

public class Viaje implements Serializable {
    private int idviaje;
    private String origen;
    private String destino;
    private double precio;
    private String foto;
    //---------------------------------------

    public Viaje(String origen, String destino, double precio) {
        this.idviaje = 0;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.foto = null;
    }
    public Viaje(int idviaje, String origen, String destino, double precio) {
        this.idviaje = idviaje;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.foto = null;
    }

    public Viaje() {
        this.idviaje = 0;
        this.origen = "";
        this.destino = "";
        this.precio = 0.0;
        this.foto = null;
    }

    public Viaje(String origen, String destino, double precio, String foto) {
        this.idviaje = 0;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.foto = foto;
    }
    public Viaje(int idviaje,String origen, String destino, double precio, String foto) {
        this.idviaje = idviaje;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.foto = foto;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Viaje)) return false;
        Viaje viaje = (Viaje) o;
        return idviaje == viaje.idviaje;
    }

    public int getIdviaje() {
        return idviaje;
    }

    public void setIdviaje(int idviaje) {
        this.idviaje = idviaje;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idviaje);
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "idviaje=" + idviaje +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", precio=" + precio +
                ", foto='" + foto + '\'' +
                '}';
    }
}
