package com.example.afrina.project_google_map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by farb on 12/4/17.
 */

public class RoadMap {


    public static final int NUMBER_OF_ROADS = 7;

    //defines the points for the roads
    private static ArrayList<ArrayList<LatLng>> roadPolygons;
    //where can I go next if I'm at the end of i'th road
    private static ArrayList<ArrayList<Integer>> neighbourRoads;

    public static ArrayList<Road> roads;



    public static void initializeRoads(){
        roads = new ArrayList<>();
        roadPolygons = new ArrayList<>();
        neighbourRoads = new ArrayList<>();

        //road 0 - polashi mor to azimpur mor
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(0).add(new LatLng(23.727346, 90.389336));
        roadPolygons.get(0).add(new LatLng(23.727307, 90.388960));
        roadPolygons.get(0).add(new LatLng(23.727169, 90.388445));
        roadPolygons.get(0).add(new LatLng(23.727071, 90.387994));
        roadPolygons.get(0).add(new LatLng(23.727012, 90.387554));
        roadPolygons.get(0).add(new LatLng(23.726953, 90.387114));
        roadPolygons.get(0).add(new LatLng(23.726953, 90.386449));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(0).add(1);

        roads.add(new Road (0, roadPolygons.get(0), 5, 0.1f ));


        //road 1 - azimpur mor to new market
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(1).add(new LatLng(23.727032, 90.386240));
        roadPolygons.get(1).add(new LatLng(23.727975, 90.385993));
        roadPolygons.get(1).add(new LatLng(23.728581, 90.385880));
        roadPolygons.get(1).add(new LatLng(23.729276, 90.385834));
        roadPolygons.get(1).add(new LatLng(23.730108, 90.385705));
        roadPolygons.get(1).add(new LatLng(23.730811, 90.385529));
        roadPolygons.get(1).add(new LatLng(23.731497, 90.385309));
        roadPolygons.get(1).add(new LatLng(23.732087, 90.385137));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(1).add(2);

        roads.add(new Road (1, roadPolygons.get(1), 5, 1f ));


        //road 2 - new market to nilkhet mor
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(2).add(new LatLng(23.732366, 90.385209));
        roadPolygons.get(2).add(new LatLng(23.732369, 90.385568));
        roadPolygons.get(2).add(new LatLng(23.732408, 90.385927));
        roadPolygons.get(2).add(new LatLng(23.732430, 90.386115));
        roadPolygons.get(2).add(new LatLng(23.732457, 90.386343));
        roadPolygons.get(2).add(new LatLng(23.732496, 90.386579));
        roadPolygons.get(2).add(new LatLng(23.732530, 90.386836));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(2).add(3);
        neighbourRoads.get(2).add(6);

        roads.add(new Road (2, roadPolygons.get(2), 5, 0.1f ));



        //road 3 - nilkhet mor to Fulller road
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(3).add(new LatLng(23.732559, 90.387307));
        roadPolygons.get(3).add(new LatLng(23.732647, 90.388037));
        roadPolygons.get(3).add(new LatLng(23.732745, 90.388606));
        roadPolygons.get(3).add(new LatLng(23.732794, 90.389132));
        roadPolygons.get(3).add(new LatLng(23.732873, 90.389776));
        roadPolygons.get(3).add(new LatLng(23.732942, 90.390227));
        roadPolygons.get(3).add(new LatLng(23.732998, 90.390542));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(3).add(4);

        roads.add(new Road (3, roadPolygons.get(3), 5, 0.1f ));



        //road 4 - Fulller road
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(4).add(new LatLng(23.732801, 90.390871));
        roadPolygons.get(4).add(new LatLng(23.732564, 90.391076));
        roadPolygons.get(4).add(new LatLng(23.732186, 90.391320));
        roadPolygons.get(4).add(new LatLng(23.731969, 90.391529));
        roadPolygons.get(4).add(new LatLng(23.731697, 90.391727));
        roadPolygons.get(4).add(new LatLng(23.731456, 90.391893));
        roadPolygons.get(4).add(new LatLng(23.731093, 90.391877));
        roadPolygons.get(4).add(new LatLng(23.730823, 90.391866));
        roadPolygons.get(4).add(new LatLng(23.730342, 90.391834));
        roadPolygons.get(4).add(new LatLng(23.729895, 90.391888));
        roadPolygons.get(4).add(new LatLng(23.729440, 90.391987));
        roadPolygons.get(4).add(new LatLng(23.729085, 90.392243));
        roadPolygons.get(4).add(new LatLng(23.728355, 90.392846));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(4).add(5);

        roads.add(new Road (4, roadPolygons.get(4), 5, 0.1f ));



        //road 5 - buet school to polashi mor
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(5).add(new LatLng(23.728173, 90.392926));
        roadPolygons.get(5).add(new LatLng(23.728261, 90.391794));
        roadPolygons.get(5).add(new LatLng(23.728138, 90.391435));
        roadPolygons.get(5).add(new LatLng(23.728040, 90.391092));
        roadPolygons.get(5).add(new LatLng(23.727883, 90.390743));
        roadPolygons.get(5).add(new LatLng(23.727755, 90.390373));
        roadPolygons.get(5).add(new LatLng(23.727755, 90.390373));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(5).add(0);

        roads.add(new Road (5, roadPolygons.get(5), 5, 0.1f ));




        //road 6 - polashi mor to nilkhet
        roadPolygons.add(new ArrayList<LatLng>());
        roadPolygons.get(6).add(new LatLng(23.732277, 90.386972));
        roadPolygons.get(6).add(new LatLng(23.731786, 90.386940));
        roadPolygons.get(6).add(new LatLng(23.731285, 90.387047));
        roadPolygons.get(6).add(new LatLng(23.730912, 90.387111));
        roadPolygons.get(6).add(new LatLng(23.730372, 90.387218));
        roadPolygons.get(6).add(new LatLng(23.729979, 90.387433));
        roadPolygons.get(6).add(new LatLng(23.729596, 90.387701));
        roadPolygons.get(6).add(new LatLng(23.729115, 90.387958));
        roadPolygons.get(6).add(new LatLng(23.728614, 90.388462));
        roadPolygons.get(6).add(new LatLng(23.728290, 90.388795));
        roadPolygons.get(6).add(new LatLng(23.727701, 90.389192));

        neighbourRoads.add(new ArrayList<Integer>());
        neighbourRoads.get(6).add(0);

        roads.add(new Road (6, roadPolygons.get(6), 5, 1f ));

    }


    public static LatLng getNextPointOfARoad (int roadNo, int currPointIndex){
        return roads.get(roadNo).getNextPoint(currPointIndex);
    }


    public static boolean isItTheEndOfTheRoad (int roadNo, int currPointIndex){
        return roads.get(roadNo).isItTheEndOfTheRoad(currPointIndex);
    }

    public static int chooseNextRoad (int endedRoadNo){
        Random randGenerator = new Random();
        return neighbourRoads.get(endedRoadNo).get(randGenerator.nextInt(neighbourRoads.get(endedRoadNo).size()));
    }


    public static int getLengthOfRoad(int index){
        return (roads.get(index).getPoints().size()-1) * 40;
    }

}
