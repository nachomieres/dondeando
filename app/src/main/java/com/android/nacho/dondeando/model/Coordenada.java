package com.android.nacho.dondeando.model;

import android.location.Location;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cris on 24/04/2016.
 */
public class Coordenada {
    private String lat;
    private String lon;
    private String hora;

    public Coordenada() {
    }

    public Coordenada(Location location) {
        this.lat = String.valueOf(location.getLatitude());
        this.lon = String.valueOf(location.getLongitude());
        //this.hora = String.valueOf(location.getTime());
        this.hora = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(location.getTime()));
    }
    /**
     *
     * @param lat
     * @param lon
     * @param hora
     */
    public Coordenada(String lat, String lon, String hora) {
        this.lat = lat;
        this.lon = lon;
        this.hora = hora;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getHora() {
        return hora;
    }

}
