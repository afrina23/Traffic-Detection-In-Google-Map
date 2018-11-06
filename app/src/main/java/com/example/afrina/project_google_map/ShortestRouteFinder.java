package com.example.afrina.project_google_map;

import java.util.ArrayList;

/**
 * Created by farb on 1/21/18.
 */

/*


All you need to do is setTimeCost, then findShortestPath. :)
Careful when you set time costs. You have to pass the arraylist with the times sequentially. T0, T1...like
I drew in the notepad.

*/

public class ShortestRouteFinder {

    private static final int N = 6;
    private static final int INFINITY = 99999;

    public ArrayList<ArrayList<Integer>> neighbours=new ArrayList<>();
    public double[][] cost = new double[N][N];



    private int[] parent = new int [N];
    private double[] shortestDistance = new double [N];


    public ShortestRouteFinder (){
        for(int i=0;i<7;i++){
            neighbours.add(new ArrayList<Integer>());

        }

        neighbours.get(0).add(1); neighbours.get(0).add(5);
        neighbours.get(1).add(0); neighbours.get(1).add(2);
        neighbours.get(2).add(1); neighbours.get(2).add(5); neighbours.get(2).add(3);
        neighbours.get(3).add(2); neighbours.get(3).add(4);
        neighbours.get(4).add(3); neighbours.get(4).add(5);
        neighbours.get(5).add(0); neighbours.get(5).add(2); neighbours.get(5).add(4);

        for (int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) cost[i][j] = 0;
                else cost[i][j] = INFINITY;
            }

            parent[i] = -1;
            shortestDistance[i] = INFINITY;
        }
    }


    public void setTimeCosts (ArrayList<Double> timeCosts){
        cost[0][5] = cost[5][0] = timeCosts.get(0);
        cost[1][0] = cost[0][1] = timeCosts.get(1);
        cost[1][2] = cost[2][1] = timeCosts.get(2);
        cost[2][3] = cost[3][2] = timeCosts.get(3);
        cost[3][4] = cost[4][3] = timeCosts.get(4);
        cost[4][5] = cost[5][4] = timeCosts.get(5);
        cost[2][5] = cost[5][2] = timeCosts.get(6);

        System.out.println("The cost table is - ");
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }

    }



    public ArrayList<Integer> findShortestPath (int source, int destination){
        ArrayList<Integer> Q = new ArrayList<>();
        int u;


        for (int i=0; i<N; i++){
            Q.add(i);
            if (i==source) {
                shortestDistance[i] = 0;
                parent[i] = i;
            }
            else {
                shortestDistance[i] = INFINITY;
                parent[i] = -1;
            }
        }

        while (!Q.isEmpty()){
            //get u = min distant vertex from source
            u = Q.get(0);
            int uIndex = 0;

            for (int i=0; i<Q.size(); i++){
                if (shortestDistance[Q.get(i)] < shortestDistance[u]) {
                    u = Q.get(i);
                    uIndex = i;
                }
            }

            if (u==destination) break;
            Q.remove(uIndex);

            for (int i:neighbours.get(u)){
                System.out.println(i + " is a neighbour of " + u);


                if (shortestDistance[i] > shortestDistance[u] + cost[u][i]) {
                    System.out.println("Previously distance to go to " + i + " was " + shortestDistance[i]);
                    shortestDistance[i] = shortestDistance[u] + cost[u][i];
                    parent[i] = u;
                    System.out.println("Now distance to go to " + i + " is " + shortestDistance[i]);
                }
            }

        }

        System.out.println("Here is the shortest path - ");
        ArrayList<Integer> shortestPath = new ArrayList<>();
        for (int i=destination; ; i = parent[i]){
            if (i==source) break;
            shortestPath.add(getRoadBetween2Nodes(i, parent[i]));
            shortestPath.get(shortestPath.size()-1);
        }

        System.out.println();

        return shortestPath;
    }


    private int getRoadBetween2Nodes (int n1, int n2){
        int roadNo = -1;

        if ((n1 == 0 && n2 ==5) || (n1 == 5 && n2 == 0)) roadNo = 0;
        else if ((n1 == 0 && n2 ==1) || (n1 == 1 && n2 == 0)) roadNo = 1;
        else if ((n1 == 1 && n2 ==2) || (n1 == 2 && n2 == 1)) roadNo = 2;
        else if ((n1 == 2 && n2 ==3) || (n1 == 3 && n2 == 2)) roadNo = 3;
        else if ((n1 == 3 && n2 ==4) || (n1 == 4 && n2 == 3)) roadNo = 4;
        else if ((n1 == 4 && n2 ==5) || (n1 == 5 && n2 == 4)) roadNo = 5;
        else if ((n1 == 2 && n2 ==5) || (n1 == 5 && n2 == 2)) roadNo = 6;

        return roadNo;
    }

}
