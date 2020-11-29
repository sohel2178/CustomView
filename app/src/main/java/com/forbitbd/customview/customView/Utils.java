package com.forbitbd.customview.customView;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static int[][] results = new int[9][9];

    private static List<Integer> valueList =  new ArrayList<>();

    private static void initList(){
        valueList = new ArrayList<>();
        for(int i=0;i<9;i++){
            valueList.add(i+1);
        }
    }


    private static int getRandomValuesFromSet(){
        return valueList.get(new Random().nextInt(valueList.size()));
    }

    private static void removeFromList(int value){
        int position = valueList.indexOf(value);
        if(position!=-1){
            valueList.remove(position);
        }
    }

    public static int[][] getResults(){
        initList();

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                int value = getRandomValuesFromSet();
                results[i][j]= value;
                removeFromList(value);
            }
        }


        for (int i=0;i<6;i++){
            for (int j=0;j<3;j++){
                results[3+i][j]=results[i][(j+1)%3];
            }
        }

        for (int j=0;j<6;j++){
            for(int i=0;i<3;i++){
                results[i+6][j+3]=results[6+(2+i)%3][j];
            }
        }

        for (int i =0;i<6;i++){

        }

        results[5][6]=results[8][8];
        results[4][6]=results[7][8];
        results[3][6]=results[6][8];

        results[5][8]=results[8][7];
        results[4][8]=results[7][7];
        results[3][8]=results[6][7];
        results[5][7]=results[8][6];
        results[4][7]=results[7][6];
        results[3][7]=results[6][6];

        results[2][7]=results[5][6];
        results[1][7]=results[4][6];
        results[0][7]=results[3][6];
        results[2][6]=results[5][8];
        results[1][6]=results[4][8];
        results[0][6]=results[3][8];
        results[2][8]=results[5][7];
        results[1][8]=results[4][7];
        results[0][8]=results[3][7];

       /* for (int i=0;i<3;i++){
            for (int j=0;j<6;j++){
                results[i+6][j+3] = results[6+(i+2)%3][j];
                //Log.d("TTTTTTT","results["+(i+6)+"]["+(j+3)+"]=results["+(6+(i+2)%3)+"]["+j+"]");
            }
        }*/

        /*results[6][3]=results[8][0];
        results[7][3]=results[6][0];
        results[8][3]=results[7][0];
        results[6][4]=results[8][1];
        results[7][4]=results[6][1];
        results[8][4]=results[7][1];
        results[6][5]=results[8][2];
        results[7][5]=results[6][2];
        results[8][5]=results[7][2];
        results[6][6]=results[8][3];
        results[7][6]=results[6][3];
        results[8][6]=results[7][3];
        results[6][7]=results[8][4];
        results[7][7]=results[6][4];
        results[8][7]=results[7][4];
        results[6][8]=results[8][5];
        results[7][8]=results[6][5];
        results[8][8]=results[7][5];*/







        /*
        results[3][0]=results[0][1];
        results[3][1]=results[0][2];
        results[3][2]=results[0][0];
        results[4][0]=results[1][1];
        results[4][1]=results[1][2];
        results[4][2]=results[1][0];
        results[5][0]=results[2][1];
        results[5][1]=results[2][2];
        results[5][2]=results[2][0];
        results[6][0]=results[3][1];
        results[6][1]=results[3][2];
        results[6][2]=results[3][0];
        results[7][0]=results[4][1];
        results[7][1]=results[4][2];
        results[7][2]=results[4][0];
        results[8][0]=results[5][1];
        results[8][1]=results[5][2];
        results[8][2]=results[5][0];

        */







        return results;

    }

}
