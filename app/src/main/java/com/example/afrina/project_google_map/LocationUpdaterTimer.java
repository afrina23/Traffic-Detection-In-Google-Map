package com.example.afrina.project_google_map;

import android.content.Context;

import java.util.TimerTask;



//take a LocationUpdaterTimer in our map class. then call startUpdatingLocation()

public class LocationUpdaterTimer extends TimerTask {

    private int deltaTime;
    private MapsActivity ma;
    private static Context cont;

    LocationUpdaterTimer(MapsActivity _ma){
        ma = _ma;
        cont = ma;
    }

    @Override
    public void run() {

         //   ma.pickCurrentLocation();
           // ma.insertLocation(ma.getmCurrentLocation().getLongitude(), ma.getmCurrentLocation().getLatitude());
            //ma.showCalculatedVelocity(5000);
           // ma.showToast(cont, "The current velocity is " + ma.getVelocity() + " m/s");

    }
}
