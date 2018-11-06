package com.example.afrina.project_google_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by farb on 11/20/17.
 */

public class HomePage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
      //  retrieveRoadData(0);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }
    public void startUsingApp(View v){
        System.out.println("Start button clicked");
        startActivity(new Intent(HomePage.this, MapsActivity.class));

    }
    public ArrayList<FirebaseEntry> retrieveRoadData(int roadNo){
        final ArrayList<FirebaseEntry> roadData= new ArrayList<>();
        DatabaseReference roadRef=databaseReference.child(String.valueOf(roadNo));
        roadRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseEntry road = dataSnapshot.getValue(FirebaseEntry.class);
                roadData.add(road);
                System.out.println("Road info "+road.toString());
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
        return roadData;
    }
}
