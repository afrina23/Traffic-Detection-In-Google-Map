package com.example.afrina.project_google_map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by farb on 12/16/17.
 */

public class Road {

    private int roadId;
    private ArrayList<LatLng> points;
    private int jammedPortionEndIndex;

    private float jamProbability;
    private float jamProbabilityMultiplier;
    private boolean jamStatus;


    private Random randGenerator;


    public  Road (int _roadId, ArrayList<LatLng> _points, int _jammedPortionEndIndex, float _jamProbability){
        roadId = _roadId;
        points = _points;
        jammedPortionEndIndex = _jammedPortionEndIndex;
        jamProbability = _jamProbability;

        jamProbabilityMultiplier = 1f;
        jamStatus = false;

        randGenerator = new Random();
    }




    public LatLng getNextPoint (int currPointIndex){
        if (isInJam(currPointIndex)) {
            return points.get(currPointIndex);
        }
        else {
            return points.get(currPointIndex + 1);
        }
    }


    public boolean isItTheEndOfTheRoad (int currPointIndex){
        return points.size() == currPointIndex+1;
    }


    public void induceTrafficJam (){
        int tossedValue = randGenerator.nextInt(101);
        if (tossedValue < jamProbability * jamProbabilityMultiplier * 100){
            jamStatus = true;
            System.out.println("Road " + roadId + " is in jam.");
        }
        else {
            jamStatus = false;
            System.out.println("Road " + roadId + " is free.");
        }
    }


    ////////////////// private functions /////////////

    private boolean isInJam (int positionIndex){
        return (positionIndex >= jammedPortionEndIndex && jamStatus == true);
    }


    ////////////////// getters and setters ///////////////////

    public int getRoadId() {
        return roadId;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public int getJammedPortionEndIndex() {
        return jammedPortionEndIndex;
    }

    public float getJamProbability() {
        return jamProbability;
    }

    public void setJamProbabilityMultiplier(float jamProbabilityMultiplier) {
        this.jamProbabilityMultiplier = jamProbabilityMultiplier;
    }

}
