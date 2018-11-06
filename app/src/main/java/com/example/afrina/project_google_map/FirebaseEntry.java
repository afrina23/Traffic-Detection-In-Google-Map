package com.example.afrina.project_google_map;

import java.util.Date;

/**
 * Created by afrina on 12/4/17.
 */


public class FirebaseEntry {
    public int personId;
    //public float velocity;
    public double startLocationLat;
    public double startLocationLog;
    public double endLocationLat;
    public double endLocationLog;
    public Date startTime;
    public Date endTime;
    FirebaseEntry(){

    }
    FirebaseEntry(int p_id,double strtX,double strtY,double endX,double endY,Date s_time,Date e_time){
        personId=p_id;
        startLocationLat=strtX;
        startLocationLog =strtY;
        endLocationLat =endX;
        endLocationLog =endY;
        startTime=s_time;
        endTime=e_time;
    }


}
