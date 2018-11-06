package com.example.afrina.project_google_map;

/**
 * Created by afrina on 12/16/17.
 */

public class StrtEndLocationPair {
    com.google.maps.model.LatLng startLocation;
    com.google.maps.model.LatLng endLocation;
    StrtEndLocationPair(com.google.maps.model.LatLng prevLocation, com.google.maps.model.LatLng currLocation){
        startLocation=prevLocation;
        endLocation=currLocation;
    }
    /*StrtEndLocationPair(LatLng strt, LatLng end){
        startLocation=strt;
        endLocation=end;
    }*/
    public int hashCode(){
        System.out.println("In hashcode");
        int hashcode = 0;
        hashcode = startLocation.hashCode();
        hashcode += endLocation.hashCode();
        return hashcode;
    }

    public boolean equals(Object obj){
        System.out.println("In equals");
        if (obj instanceof StrtEndLocationPair) {
            StrtEndLocationPair pp = (StrtEndLocationPair) obj;
            return (pp.startLocation.equals(this.startLocation) && pp.endLocation.equals(this.endLocation));
        } else {
            return false;
        }
    }
}
