package com.forbitbd.customview.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.forbitbd.customview.R;
import com.forbitbd.customview.customView.MadView;
import com.forbitbd.customview.customView.SudokuView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SudokuView.SudokuListener {


    private MadView madView;
    List<String> stringList = new ArrayList<>();

    private Button btnOne,btnTwo,btnThree,btnFour,btnFive,btnSix,btnSeven,btnEight,btnNine,btnClear;


    private SudokuView sudokuView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuView = findViewById(R.id.sudokuView);
        sudokuView.setListener(this);

        btnOne = findViewById(R.id.one);
        btnTwo = findViewById(R.id.two);
        btnThree = findViewById(R.id.three);
        btnFour = findViewById(R.id.four);
        btnFive = findViewById(R.id.five);
        btnSix = findViewById(R.id.six);
        btnSeven = findViewById(R.id.seven);
        btnEight = findViewById(R.id.eight);
        btnNine = findViewById(R.id.nine);
        btnClear = findViewById(R.id.clear);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnClear.setOnClickListener(this);




        //cameraView.bindToLifecycle(this);

        /*madView = findViewById(R.id.madView);



        stringList.add("Sohel");
        stringList.add("Shakil");
        stringList.add("Robin");
        stringList.add("Saimul");
        stringList.add("Abid");
        stringList.add("Baki");
        stringList.add("Kashem");
        stringList.add("Shamim");
        stringList.add("Sifat");

        madView.setStringList(stringList);
        madView.setMyListener(this);
        madView.setRefrence("Hardware");*/
    }

   /* @Override
    public void itemTouch(int position) {
        //Toast.makeText(this, stringList.get(position)+" Clicked", Toast.LENGTH_SHORT).show();
    }*/



   /* @Override
    public void execute(Runnable runnable) {
        Log.d("YYYYY","execute Called");
    }*/


    @Override
    public void onClick(View view) {
        if(view==btnOne){
            this.sudokuView.setValue(1);
        }else if(view==btnTwo){
            this.sudokuView.setValue(2);
        }else if(view==btnThree){
            this.sudokuView.setValue(3);
        }else if(view==btnFour){
            this.sudokuView.setValue(4);
        }else if(view==btnFive){
            this.sudokuView.setValue(5);
        }else if(view==btnSix){
            this.sudokuView.setValue(6);
        }else if(view==btnSeven){
            this.sudokuView.setValue(7);
        }else if(view==btnEight){
            this.sudokuView.setValue(8);
        }else if(view==btnNine){
            this.sudokuView.setValue(9);
        }else if(view==btnClear){
            this.sudokuView.setValue(0);
        }
    }

    @Override
    public void disable(int value) {
        switch (value){
            case 1:
                btnOne.setTextColor(Color.parseColor("#212121"));
                break;

            case 2:
                btnTwo.setTextColor(Color.parseColor("#212121"));
                break;

            case 3:
                btnThree.setTextColor(Color.parseColor("#212121"));
                break;

            case 4:
                btnFour.setTextColor(Color.parseColor("#212121"));
                break;

            case 5:
                btnFive.setTextColor(Color.parseColor("#212121"));
                break;

            case 6:
                btnSix.setTextColor(Color.parseColor("#212121"));
                break;

            case 7:
                btnSeven.setTextColor(Color.parseColor("#212121"));
                break;

            case 8:
                btnEight.setTextColor(Color.parseColor("#212121"));
                break;

            case 9:
                btnNine.setTextColor(Color.parseColor("#212121"));
                break;
        }
    }
}