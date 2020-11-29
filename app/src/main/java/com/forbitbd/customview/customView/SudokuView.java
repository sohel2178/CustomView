package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class SudokuView extends View {
    private int size;
    private float side;

    private Box[][] boxes = new Box[9][9];

    private BigBox [][] bigBoxes = new BigBox[3][3];

    private int selectedi=-1;
    private int selectedj=-1;

    private int[][] results = new int[9][9];
    private int[][] finalArr = new int[9][9];

    private float textSize = 80f;

    private SudokuListener listener;


    public SudokuView(Context context) {
        super(context);
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SudokuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setListener(SudokuListener listener){
        this.listener = listener;
    }

    /*private void initList(){
        valueList = new ArrayList<>();
        for(int i=0;i<9;i++){
            valueList.add(i+1);
        }
    }

    private int getRandomValuesFromSet(){
        return valueList.get(new Random().nextInt(valueList.size()));
    }

    private void removeFromList(int value){
        int position = valueList.indexOf(value);
        if(position!=-1){
            valueList.remove(position);
        }
    }*/

    private void init(){

        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        this.finalArr = sudokuGenerator.generateResultArry();

        int[][] basic = sudokuGenerator.basicLevel();

        for(int i=0;i<results.length;i++){
            for(int j=0;j<results[i].length;j++){
                results[i][j]=finalArr[i][j]*basic[i][j];
            }
        }

        //printResult(sudokuGenerator.basicLevel());


    }


    private boolean isInRow(int row,int column,int value){
        for(int i=0;i<9;i++){
            if(i!=column && boxes[row][i].getValue()==value){
                return true;
            }
        }
        return false;
    }

    private boolean isInColumn(int row,int column,int value){
        for(int i=0;i<9;i++){
            if(row!=i && boxes[i][column].getValue()==value){
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int column,int value){
        int refX =0;
        int refY =0;

        if(row<=2){
            refX =0;
        }else if(row>2 && row<=5){
            refX =3;
        }else {
            refX = 6;
        }

        if(column<=2){
            refY =0;
        }else if(column>2 && column<=5){
            refY =3;
        }else {
            refY = 6;
        }

        //Log.d("HHHHHH",refX+","+refY);

        for(int i=refX;i<refX+3;i++){
            for(int j=refY;j<refY+3;j++){
               //Log.d("REF","("+i+","+j+")");
                if( i!=row && j!=column && boxes[i][j].getValue()==value){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(int row,int column,int value){
        if(isInRow(row,column,value)){
            return false;
        }else {
            if(isInColumn(row,column,value)){
                return false;
            }else {
                if(isInBox(row,column,value)){
                    return false;
                }
            }
        }

        return true;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //printResult();

        for(int i=0;i<boxes.length;i++){
            for(int j=0;j<boxes[i].length;j++){
                Box box = boxes[i][j];
                box.draw(canvas);
            }
        }



        for(int i=0;i<bigBoxes.length;i++){
            for(int j=0;j<bigBoxes[i].length;j++){
                BigBox bigBox = bigBoxes[i][j];
                bigBox.draw(canvas);
            }
        }





    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.size = Math.min(getMeasuredWidth(),getMeasuredHeight());
        this.side = this.size/9f;

        for(int i=0;i<boxes.length;i++){
            for(int j=0;j<boxes[i].length;j++){
                Box box = new Box(i*side,j*side,side);
                box.setValue(results[i][j]);
                if(results[i][j]!=0){
                    box.setEditable(false);
                }else{
                    box.setEditable(true);
                }
                boxes[i][j] = box;
            }
        }

        for(int i=0;i<bigBoxes.length;i++){
            for(int j=0;j<bigBoxes[i].length;j++){
                bigBoxes[i][j] = new BigBox(i*side*3,j*side*3,side*3);
            }
        }

        setMeasuredDimension(size,size);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                selectedi = (int) (x/side);
                selectedj = (int) (y/side);

                invalidate();
                return true;


            default:
                return false;
        }
    }

    public void setValue(int value) {
        if(selectedi!=-1 && selectedj!=-1){
            Box box = boxes[selectedi][selectedj];
            if(box.isEditable()){
                box.setValue(value);
                if(!isValid(selectedi,selectedj,value)){
                    box.setState(1);
                }else {
                    if(value!=finalArr[selectedi][selectedj]){
                        box.setState(2);
                    }else {
                        box.setState(0);
                    }
                }


                invalidate();
            }

            if(countNumber(value)==9){
                if(listener!=null){
                    listener.disable(value);
                }

            }

            Log.d("HHHHHHH",countNumber(value)+"");
            Log.d("HHHHHHH",isComplete()+"");

        }
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    private boolean isComplete(){
        for(int i=0;i<finalArr.length;i++){
            for (int j=0;j<finalArr[i].length;j++){


                if(boxes[i][j].getValue()!=finalArr[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private int countNumber(int value){
        int count =0;

        for(int i=0;i<boxes.length;i++){
            for(int j=0;j<boxes[i].length;j++){
                if(boxes[i][j].getValue()==value){
                    count++;
                }
            }
        }

        return count;
    }


    class Box{
        private float x;
        private float y;
        private float side;
        private Paint paint;
        private int value;

        private int normal= Color.parseColor("#212121");
        private int shaded= Color.parseColor("#616161");
        private int selected= Color.parseColor("#9e9e9e");
        private int validTextColor= Color.parseColor("#388e3c");
        private int errorTextColor= Color.parseColor("#d32f2f");
        private int inValidTextColor= Color.parseColor("#0097a7");

        private int state;
        private boolean isEditable;

        public Box(float x, float y, float side) {
            this.x = x;
            this.y = y;
            this.side = side;
            this.paint = new Paint();
        }

        public void draw(Canvas canvas){
            int relativeIndexX = ((int) selectedi/3)*3;
            int relativeIndexY = ((int) selectedj/3)*3;


            if(selectedi==getXIndex() && selectedj==getYIndex()){
                paint.setColor(selected);
            }else if(selectedi==getXIndex() || selectedj==getYIndex()){
               paint.setColor(shaded);
            }else if((getXIndex()-relativeIndexX>=0 && getXIndex()-relativeIndexX<=2)
                    && (getYIndex()-relativeIndexY>=0 && getYIndex()-relativeIndexY<=2)
                    && selectedi!=-1 && selectedj!=-1){
                paint.setColor(shaded);
            }else if(selectedi!=-1 && selectedj!=-1 && boxes[selectedi][selectedj].getValue()==value && value!=0){
                paint.setColor(selected);
            }else{
                paint.setColor(normal);
            }

            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x,y,x+side,y+side,paint);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,x+side,y+side,paint);

            // Text Color Logic
            if(isEditable){
                if(state==0){
                    paint.setColor(Color.GREEN);
                }else if(state==1){
                    paint.setColor(Color.RED);
                }else {
                    paint.setColor(inValidTextColor);
                }
            }else{
                paint.setColor(Color.WHITE);
            }


            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(textSize);
            paint.setStyle(Paint.Style.FILL);

            if(value!=0){
                canvas.drawText(String.valueOf(value),x+side/2f,y+side/2f+textSize/2f,paint);
            }
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getSide() {
            return side;
        }

        public void setSide(float side) {
            this.side = side;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getXIndex(){
            return (int) (this.x/side);
        }

        public int getYIndex(){
            return (int) (this.y/side);
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isEditable() {
            return isEditable;
        }

        public void setEditable(boolean editable) {
            isEditable = editable;
        }
    }

    class BigBox{
        private float x;
        private float y;
        private float side;
        private Paint paint;

        public BigBox(float x, float y, float side) {
            this.x = x;
            this.y = y;
            this.side = side;
            this.paint = new Paint();
        }

        public void draw(Canvas canvas){
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3f);
            canvas.drawRect(x,y,x+side,y+side,paint);


        }
    }


    public interface SudokuListener{
        void disable(int value);
        void complete();
    }


}


