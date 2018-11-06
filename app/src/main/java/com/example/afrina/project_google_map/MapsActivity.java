package com.example.afrina.project_google_map;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.GeoApiContext;
import com.google.maps.RoadsApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GeoApiContext mContext;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference = database.getReference().child("7");
    ArrayMap<Integer,ArrayList<FirebaseEntry>> allRoadInfo;
    ArrayList<FirebaseEntry> entryList;
    ArrayList<DatabaseReference> roadReferences;
    private ProgressBar mProgressBar;
    Calendar currentTime;
    ArrayList<ArrayList<com.google.android.gms.maps.model.LatLng> >roadsLatlong;
    ArrayList<List<com.google.android.gms.maps.model.LatLng> > snappedRoadPoints;
    ArrayList<Double> roadTravarseTime;
    ArrayList<Double> roadVelocity;
    ArrayList<Integer> roadSequence;
    ShortestRouteFinder shortestRouteFinder;
    private static final int PAGE_SIZE_LIMIT = 100;

    private static final int PAGINATION_OVERLAP = 5;
    public  int RED_MARK=5;
    public  int RED_YELLOW_MARK=11;
    public  int YELL_MARK=16;




    private static final int GREEN = Color.rgb(39, 203, 88);
    private static final int GREENELLOW = Color.rgb(182, 217, 62);
    private static final int YELLOW = Color.rgb(244, 240, 70);
    private static final int REDELLOW = Color.rgb(240, 125, 21);
    private static final int RED = Color.rgb(241, 67, 67);


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MultiDex.install(this);
        Button getSnapped=findViewById(R.id.getCondition);

        allRoadInfo= new ArrayMap<>();
        entryList = new ArrayList<>();
        roadReferences = new ArrayList<>();
        roadVelocity=new ArrayList<Double>();
        roadsLatlong= new ArrayList<>();
        roadSequence=new ArrayList<>();
        shortestRouteFinder=new ShortestRouteFinder();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        snappedRoadPoints= new ArrayList<>();
        roadTravarseTime= new ArrayList<Double>(7);
        currentTime = Calendar.getInstance();
        System.out.println("Current time => "+currentTime.getTime());



        for(int i=0;i<7;i++){
            allRoadInfo.put(i,new ArrayList<FirebaseEntry>());
        }




        mContext = new GeoApiContext().setApiKey(getString(R.string.google_maps_web_services_key));

        System.out.println("Trying to get RoadData.........");
        mgetRoadData.execute();

        System.out.println("Got Road Data");



    }
    public void onGetCondition(View v){
        drawLine();
    }
    boolean inBetween(int roadHour, int currentHour){
        int road=roadHour%24;
        int curr=currentHour%24;
        if(road>=curr){
            int diff=road-curr;
            if(diff<=2){
                return true;
            }
            return false;
        }
        else{
            int diff=curr-road;
            if(diff<=2){
                return true;
            }
            return false;
        }
    }
    @SuppressLint("StaticFieldLeak")

            AsyncTask<Void, Void, ArrayMap<Integer,ArrayList<FirebaseEntry>>> mgetRoadData =
            new AsyncTask<Void, Void, ArrayMap<Integer,ArrayList<FirebaseEntry>>>() {
                @Override
                protected void onPreExecute() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setIndeterminate(true);
                }

                @Override
                protected ArrayMap<Integer,ArrayList<FirebaseEntry>>
                doInBackground(Void... params) {
                    try {

                        for (int i=0; i<7; i++) {
                            System.out.println("Getting road referrence");
                            roadReferences.add(databaseReference.child(String.valueOf(i)));
                            System.out.println("have got road referrence");

                            final int finalI = i;


                            roadReferences.get(i).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // get total available quest
                                    int size = (int) dataSnapshot.getChildrenCount();
                                    System.out.println("single value listener : size = " + size);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                            roadReferences.get(i).addChildEventListener(new ChildEventListener() {
                                //   ArrayList<FirebaseEntry> entryList=new ArrayList<FirebaseEntry>();
                                int j = finalI;

                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    FirebaseEntry road = dataSnapshot.getValue(FirebaseEntry.class);
                                    //entryList.add(new FirebaseEntry(98, 4f,3f,5f,4f, new Date(), new Date()));
                                    //roadData=entryList;
                                    //   int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                                    // Calendar roadTime= Calendar.getInstance();
                                    // roadTime.setTime(road.startTime);

//                    int roadDataHour=roadTime.get(Calendar.HOUR_OF_DAY);
                                    //                  if(inBetween(roadDataHour,currentHour)){
                                    entryList.add(road);

                                    allRoadInfo.get(j).add(entryList.get(entryList.size() - 1));
                                    System.out.println("Road info " + finalI + " Person Id " + entryList.get(entryList.size() - 1).personId + " location " + entryList.get(entryList.size() - 1).startLocationLat + " " + entryList.get(entryList.size() - 1).startLocationLog
                                            + " size of array " + allRoadInfo.get(j).size());
                                    System.out.println("Entry list er size : " + entryList.size());
//                    System.out.println("road " + j + " er child ase " + dataSnapshot.getChildrenCount() + " ta");

                                    //                }
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Thread.sleep(2000);
                        }


                        //mProgressBar.setVisibility(View.INVISIBLE);
                        return allRoadInfo;
                    } catch (final Exception ex) {
                        // toastException(ex);
                        ex.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(ArrayMap<Integer,ArrayList<FirebaseEntry>> roadInfo) {
                    //   mSnappedPoints = snappedPoints;
                    mProgressBar.setVisibility(View.INVISIBLE);
                   // drawLine();

                }
            };

    AsyncTask<Void, Void, ArrayList<List<com.google.android.gms.maps.model.LatLng>>> mTaskSnapToRoads =
            new AsyncTask<Void, Void, ArrayList<List<com.google.android.gms.maps.model.LatLng>>>() {
                @Override
                protected void onPreExecute() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setIndeterminate(true);
                }

                @Override
                protected ArrayList<List<com.google.android.gms.maps.model.LatLng>>
                doInBackground(Void... params) {
                    try {
                        System.out.println("Size or roadsLatLong "+roadsLatlong.size());
                        for(int i=0;i<roadsLatlong.size();i++){
                            System.out.println("Snapping road No  "+i);
                            snappedRoadPoints.add(snapToRoads(mContext,roadsLatlong.get(i)));

                        }
                        return snappedRoadPoints;
                    } catch (final Exception ex) {
                       // toastException(ex);
                        ex.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(ArrayList<List<com.google.android.gms.maps.model.
                        LatLng>> snappedPoints) {
                 //   mSnappedPoints = snappedPoints;
                    mProgressBar.setVisibility(View.INVISIBLE);

                   // findViewById(R.id.speed_limits).setEnabled(true);
                    PolylineOptions[] lineOptions = new PolylineOptions[roadsLatlong.size()];
                    //MarkerOptions markerOptions[] = new MarkerOptions[7];
                    for(int i=0;i<roadsLatlong.size();i++){
                        lineOptions[i]=new PolylineOptions();
                        // markerOptions[i]= new MarkerOptions();

                        try {
                            lineOptions[i].addAll(snappedPoints.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        lineOptions[i].width(16);
                        Double velocity=roadVelocity.get(i);
                        System.out.println("Velocity........of road "+i+" is "+velocity);
                        if(velocity<=RED_MARK){
                            lineOptions[i].color(Color.RED);
                            //drawAPath(snappedPoints.get(i),mMap,5.0f,RED);
                        }
                        else if(velocity<=RED_YELLOW_MARK){
                            lineOptions[i].color(REDELLOW);
                            //drawAPath(snappedPoints.get(i),mMap,5.0f,REDELLOW);
                        }
                        else if(velocity<=YELL_MARK){
                            lineOptions[i].color(YELLOW);
                            //drawAPath(snappedPoints.get(i),mMap,5.0f,YELLOW);
                        }
                        else{
                            lineOptions[i].color(Color.GREEN);
                          //  drawAPath(snappedPoints.get(i),mMap,5.0f,GREEN);
                        }
                        lineOptions[i].geodesic(true);
                        mMap.addPolyline(lineOptions[i]);
                    }
                    System.out.println("polylines are added");

                }
            };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at Polashi and move the camera
        com.google.android.gms.maps.model.LatLng polashi =
                new com.google.android.gms.maps.model.LatLng(23.727447, 90.389698);
       // mMap.addMarker(new MarkerOptions().position(polashi).title("Marker in Polashi Mor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(polashi));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
    public void drawLine(){
        for(int i=0;i<allRoadInfo.size();i++){
            roadsLatlong.add(new ArrayList<com.google.android.gms.maps.model.LatLng>());
        }
        for(int i=0;i<7;i++){
            calculateVelocityForOneRoad(i);
        }
        for(int i=0;i<allRoadInfo.size();i++){

            for(int j=0;j<allRoadInfo.get(i).size();j++){
                //PolylineOptions pl= new PolylineOptions();

                FirebaseEntry fe=allRoadInfo.get(i).get(j);
                roadsLatlong.get(i).add(new com.google.android.gms.maps.model.LatLng
                        (fe.startLocationLat,fe.startLocationLog));
             /*   mMap.addMarker(new MarkerOptions().position(new
                        com.google.android.gms.maps.model.LatLng(fe.startLocationLat,fe.startLocationLog))
                        .title("Marker in Polashi Mor"));
*/
                if(fe.startLocationLat!=fe.endLocationLat || fe.startLocationLog != fe.endLocationLog){
                    roadsLatlong.get(i).add(new com.google.android.gms.maps.model.LatLng
                            (fe.endLocationLat,fe.endLocationLog));

                   /* mMap.addMarker(new MarkerOptions().position(new
                            com.google.android.gms.maps.model.LatLng(fe.endLocationLat
                            ,fe.endLocationLog))
                            .title("Marker in Polashi Mor"));*/

                }

            }
        }

        mTaskSnapToRoads.execute();


    }
    public void retrieveRoadData(final int roadNo){
        }

    public void onSearch (View v){
       // drawLine();
        System.out.println("entry list er size shobar seshe" + entryList.size());
        shortestRouteFinder.setTimeCosts(roadTravarseTime);
        roadSequence=shortestRouteFinder.findShortestPath(0,2);
        System.out.println("Size of road Sequence "+roadSequence.size());
        double time=0;
        double distance=0;
        PolylineOptions[] lineOptions = new PolylineOptions[roadSequence.size()];
        int j=0;
        com.google.android.gms.maps.model.LatLng start=
                new com.google.android.gms.maps.model.LatLng(23.726953 ,90.386449);

        com.google.android.gms.maps.model.LatLng end=new
                com.google.android.gms.maps.model.LatLng(23.732530, 90.386836);


        for(int i=roadSequence.size()-1;i>=0;i--){
            int road_no=roadSequence.get(i);
            lineOptions[j]=new PolylineOptions();
            System.out.println("Drawing lone for road "+road_no+" Size of roads Latllong "
            +roadsLatlong.size());
            time+=roadTravarseTime.get(road_no);
            distance+=(roadTravarseTime.get(road_no)*roadVelocity.get(road_no));
            System.out.println("size of snapped points "+roadsLatlong.get(road_no).size());
           lineOptions[j].addAll(roadsLatlong.get(road_no));
            lineOptions[j].color(Color.BLUE);
            lineOptions[j].width(20.0f);
            lineOptions[j].geodesic(true);
            mMap.addPolyline(lineOptions[j]);
           // drawAPath(roadsLatlong.get(i),mMap,2.0f,Color.BLACK);

            j++;
        }
        Marker startMarker=mMap.addMarker(new MarkerOptions().position(start).title("Distance, Time")
        .snippet(distance+", "+time));
        Marker endMarker=mMap.addMarker(new MarkerOptions().position(end).title("Distance(m), Time(s)")
                .snippet(distance+", "+time));
        endMarker.setTag("Distance ="+distance+" Time required ="+time);
        endMarker.showInfoWindow();

        //                  .title("Marker in Polashi Mor"));



    }
    private void drawAPath(List<com.google.android.gms.maps.model.LatLng> path, GoogleMap googleMap, float strokeWidth, int color) {

        if(path == null || path.size() == 0) {
            return;
        }

        //**********************SET UP PATH***************************
        com.google.android.gms.maps.model.LatLng  startPoint = path.get(0);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(startPoint);

        polylineOptions.color(color);
        polylineOptions.width(strokeWidth);
        polylineOptions.visible(true);


        //Loop through all GeoPoints
        for (com.google.android.gms.maps.model.LatLng current : path) {
            //Convert GeoPoint to pixels and add to path
            if(current != null){
                polylineOptions.add(current);
            }
        }
        googleMap.addPolyline(polylineOptions);
    }



    public void calculateVelocityForOneRoad(int roadNo){
        ArrayList<FirebaseEntry> entryArrayList=allRoadInfo.get(roadNo);

        double total_time=0;
        float total_dis=0;
        System.out.println("entry for road "+roadNo+" is of size "
                +allRoadInfo.get(roadNo).size() );
        for(int i=0;i<entryArrayList.size();i++){
            FirebaseEntry entry=entryArrayList.get(i);
            LatLng prevLocation=new LatLng(entry.startLocationLat,entry.endLocationLog);
            LatLng currLocation=new LatLng(entry.endLocationLat,entry.endLocationLog);

            long diffInMs = entry.endTime.getTime() - entry.startTime.getTime();

            double diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            float distance;
            if((entry.startLocationLat==entry.endLocationLat)
                    &&(entry.startLocationLog==entry.endLocationLog)){
                distance=0;
            }
            else{
                distance=40;
            }
            total_time+=diffInSec;
            total_dis+=distance;
            //float timeDifference=


        }
        System.out.println("distance = "+total_dis+ "time " +total_time);
        double velocity=total_dis/total_time;
        roadTravarseTime.add(roadNo,total_time);
        roadVelocity.add(velocity);
        System.out.println("velocity is "+velocity+" size of roadVelocity" +roadVelocity.size()+" for road "+roadNo);


    }
    public float calculateVelocity(StrtEndLocationPair locationPair,ArrayList<Long> times){
        float distance=getDistance(locationPair.startLocation,locationPair.endLocation);
        long sumOfTimes=0;
        for(int i=0;i<times.size();i++){
            sumOfTimes+=times.get(i);
            System.out.println("iteration of getting time "+i+" sum of time is "+sumOfTimes);
        }
        float velocity=(distance*times.size())/sumOfTimes;
        com.google.android.gms.maps.model.LatLng latLng=
                new com.google.android.gms.maps.model.LatLng(locationPair.startLocation.lat,
                        locationPair.startLocation.lng);
        //Marker marker = mMap.addMarker(new MarkerOptions()
          //      .position(latLng)
            //    .title("Velocity")
              //  .snippet(String.valueOf(velocity)));
        return velocity;

    }
    public static float getDistance(com.google.maps.model.LatLng latlngA,
                                    com.google.maps.model.LatLng latlngB) {
        Location locationA = new Location("point A");

        locationA.setLatitude(latlngA.lat);
        locationA.setLongitude(latlngA.lng);

        Location locationB = new Location("point B");

        locationB.setLatitude(latlngB.lat);
        locationB.setLongitude(latlngB.lng);

        float distance = locationA.distanceTo(locationB);//To convert Meter in Kilometer
        return distance;
    }
    private List<com.google.android.gms.maps.model.LatLng> snapToRoads(GeoApiContext contex,
                                           ArrayList<com.google.android.gms.maps.model.LatLng> CapturedLocations) throws Exception {
        List<SnappedPoint> snappedPoints = new ArrayList<>();
        List<LatLng> mCapturedLocations= new ArrayList<>();
        for(int i=0;i<CapturedLocations.size();i++){
            mCapturedLocations.add(new LatLng(CapturedLocations.get(i).latitude,
                    CapturedLocations.get(i).longitude));
//            mMap.addMarker(new MarkerOptions().position(CapturedLocations.get(i))
  //                  .title("Marker in Polashi Mor"));

        }
        int offset = 0;
        while (offset < mCapturedLocations.size()) {
            // Calculate which points to include in this request. We can't exceed the APIs
            // maximum and we want to ensure some overlap so the API can infer a good location for
            // the first few points in each request.
            if (offset > 0) {
                offset -= PAGINATION_OVERLAP;   // Rewind to include some previous points
            }
            int lowerBound = offset;
            int upperBound = Math.min(offset + PAGE_SIZE_LIMIT, mCapturedLocations.size());

            // Grab the data we need for this page.
            LatLng[] page = mCapturedLocations
                    .subList(lowerBound, upperBound)
                    .toArray(new LatLng[upperBound - lowerBound]);
           //com.google.maps.model.LatLng[] page1=(com.google.maps.model.LatLng[])page;
            // Perform the request. Because we have interpolate=true, we will get extra data points
            // between our originally requested path. To ensure we can concatenate these points, we
            // only start adding once we've hit the first new point (i.e. skip the overlap).
            SnappedPoint[] points = RoadsApi.snapToRoads(contex, true,page).await();
          //  RoadsApi.snapToRoads(contex,page);
            boolean passedOverlap = false;
            for (SnappedPoint point : points) {
                if (offset == 0 || point.originalIndex >= PAGINATION_OVERLAP) {
                    passedOverlap = true;
                }
                if (passedOverlap) {
                    snappedPoints.add(point);
                }
            }

            offset = upperBound;
        }
        List<com.google.android.gms.maps.model.LatLng> to_return= new ArrayList<>();
        for(int i=0;i<snappedPoints.size();i++){
            to_return.add(new com.google.android.gms.maps.model.LatLng(
                    snappedPoints.get(i).location.lat,snappedPoints.get(i).location.lng
            ));
        }
        return to_return;
    }

}

