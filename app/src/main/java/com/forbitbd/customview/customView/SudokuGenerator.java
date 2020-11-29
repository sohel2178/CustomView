package com.forbitbd.customview.customView;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    private int [][] results;
    private List<Integer> valueList =  new ArrayList<>();

    public SudokuGenerator() {
        initList();
        this.results = new int[3][3];

        results[0][0]=8;
        results[0][1]=2;
        results[0][2]=1;
        results[1][0]=4;
        results[1][1]=5;
        results[1][2]=6;
        results[2][0]=7;
        results[2][1]=9;
        results[2][2]=3;

        /*for (int i=0;i<results.length;i++){
            for (int j=0;j<results[i].length;j++){
                int value = getRandomValuesFromSet();
                results[i][j]= value;
                removeFromList(value);
            }
        }*/
    }

    public int[][] getResults(){
       return this.results;
    }

    private void initList(){
        valueList = new ArrayList<>();
        for(int i=0;i<9;i++){
            valueList.add(i+1);
        }
    }

    public int[][] generateResultArry(){
        int[][] retArr = new int[9][9];

        int[][] main = getResults();

        fill(0,0,main,retArr);

        fill(0,3,rotateClockinX(main),retArr);
        fill(0,6,rotateAntiClockinX(main),retArr);
        fill(3,6,rotateClockinY(rotateAntiClockinX(main)),retArr);
        fill(6,6,rotateAntiClockinY(rotateAntiClockinX(main)),retArr);

        fill(6,3,rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main))),retArr);
        fill(6,0,rotateClockinX(rotateAntiClockinY(rotateAntiClockinX(main))),retArr);

        fill(3,0,rotateAntiClockinY(rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main)))),retArr);

        fill(3,3,rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main))))),retArr);
//        fill(6,3,rotateClockinX(rotateAntiClockinY(rotateAntiClockinX(main))),retArr);
//        fill(6,0,rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main))),retArr);
//        fill(3,0,rotateAntiClockinY(rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main)))),retArr);
//        fill(3,3,rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(rotateAntiClockinY(rotateAntiClockinX(main))))),retArr);

        randomize(retArr);

        return randomize(retArr);
    }

    private int[][] randomize(int[][] arr){
        int [][] frst = new int[3][9];
        int [][] second = new int[3][9];
        int [][] third = new int[3][9];

        for (int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(i<=2){
                    frst[i][j]=arr[i][j];
                }else if(i<=5) {
                    second[i-3][j]=arr[i][j];
                }else{
                    third[i-6][j]=arr[i][j];
                }
            }
        }

       /* printResult(frst);
        printResult(second);
        printResult(third);*/

        printResult(arr);
        int f = new Random().nextInt(3);
        int s = new Random().nextInt(3);
        int t = new Random().nextInt(3);

       if(f==0){
           frst= rotateClockinX(frst);
       }else if(f==1){
           frst= rotateAntiClockinX(frst);
       }

        if(s==0){
            second= rotateClockinX(second);
        }else if(s==1){
            second= rotateAntiClockinX(second);
        }

        if(t==0){
            third= rotateClockinX(third);
        }else if(t==1){
            third= rotateAntiClockinX(third);
        }

        // Block Rotation
        int br = new Random().nextInt(3);

        if(br==0){
            fill(0,0,frst,arr);
            fill(3,0,second,arr);
            fill(6,0,third,arr);
        }else if(br==1){
            fill(0,0,second,arr);
            fill(3,0,third,arr);
            fill(6,0,frst,arr);

        }else if(br==2){
            fill(0,0,third,arr);
            fill(3,0,frst,arr);
            fill(6,0,second,arr);
        }


        // Column Wise Rotaton Start


        int [][] colfrst = new int[9][3];
        int [][] colsecond = new int[9][3];
        int [][] colthird = new int[9][3];

        for (int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(j<=2){
                    colfrst[i][j]=arr[i][j];
                }else if(j<=5) {
                    colsecond[i][j-3]=arr[i][j];
                }else{
                    colthird[i][j-6]=arr[i][j];
                }
            }
        }



       /* fill(0,0,frst,arr);
        fill(3,0,second,arr);
        fill(6,0,third,arr);*/
        printResult(arr);

        printResult(colfrst);
       /* printResult(colsecond);
        printResult(colthird);*/


        if(f==0){
            colfrst= rotateAntiClockinY(colfrst);
        }else if(f==1){
            colfrst= rotateClockinY(colfrst);
        }

        if(s==0){
            colsecond= rotateAntiClockinY(colsecond);
        }else if(f==1){
            colsecond= rotateClockinY(colsecond);
        }

        if(t==0){
            colthird= rotateAntiClockinY(colthird);
        }else if(f==1){
            colthird= rotateClockinY(colthird);
        }

        if(br==0){
            fill(0,0,colsecond,arr);
            fill(0,3,colthird,arr);
            fill(0,6,colfrst,arr);

        }else if(br==1){
            fill(0,0,colfrst,arr);
            fill(0,3,colsecond,arr);
            fill(0,6,colthird,arr);
        }else if(br==2){
            fill(0,0,colthird,arr);
            fill(0,3,colfrst,arr);
            fill(0,6,colsecond,arr);
        }



     /*  for (int i=0;i<10;i++){
           Log.d("JJJJJJ",new Random().nextInt(3)+"");
       }*/

     return arr;


    }

    private void printResult(int[][] results){
        String rr = "";
        for(int i=0;i<results.length;i++){
            for (int j=0;j<results[i].length;j++){
                rr.concat(String.valueOf(results[i][j])).concat(" ");
                //Log.d("RESULTS",results[i][j]+"");
                rr = rr+results[i][j]+" ";
            }
            rr = rr+"\n";
        }

        Log.d("RESULTS",rr);
    }


    private  int getRandomValuesFromSet(){
        return valueList.get(new Random().nextInt(valueList.size()));
    }

    private  void removeFromList(int value){
        int position = valueList.indexOf(value);
        if(position!=-1){
            valueList.remove(position);
        }
    }

    public int[][] rotateClockinX(int[][] source){
        int [][] returnArr = new int[source.length][source[0].length];
        for (int i=0;i<returnArr.length;i++){
            for (int j=0;j<returnArr[i].length;j++){
                returnArr[i][j]=source[(i+1)%returnArr.length][j];
            }
        }
        return returnArr;
    }

    public int[][] rotateAntiClockinX(int[][] source){
        int [][] returnArr = new int[source.length][source[0].length];
        for (int i=0;i<returnArr.length;i++){
            for (int j=0;j<returnArr[i].length;j++){
                returnArr[i][j]=source[(i+2)%returnArr.length][j];
            }
        }
        return returnArr;
    }

    public int[][] rotateClockinY(int[][] source){
        int [][] returnArr = new int[source.length][source[0].length];
        for (int i=0;i<returnArr.length;i++){
            for (int j=0;j<returnArr[i].length;j++){
                returnArr[i][j]=source[i][(j+1)%returnArr[i].length];
            }
        }
        return returnArr;
    }

    public int[][] rotateAntiClockinY(int[][] source){
        int [][] returnArr = new int[source.length][source[0].length];
        for (int i=0;i<returnArr.length;i++){
            for (int j=0;j<returnArr[i].length;j++){
                returnArr[i][j]=source[i][(j+2)%returnArr[i].length];
            }
        }
        return returnArr;
    }

    private void fill(int refX,int refY,int[][] source,int[][] dest){

        for (int i=0;i<source.length;i++){
            for(int j=0;j<source[i].length;j++){
                dest[refX+i][refY+j]= source[i][j];
            }
        }
    }


    public int[][] basicLevel(){
        int[][] retArr = new int[9][9];

        this.valueList = new ArrayList<>();
        this.valueList.add(1);
        this.valueList.add(0);
        this.valueList.add(0);
        this.valueList.add(0);
        this.valueList.add(1);

        for (int i=0;i<retArr.length;i++){
            for(int j=0;j<retArr[i].length;j++){
                retArr[i][j]= getRandomValuesFromSet();
            }
        }
        return retArr;
    }

}
