package com.example.a20200715test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private int flag = 1;
    private static final int LINE = 1;
    private static final int CIRCLE = 2;
    private static final int SQUARE = 3;
    Paint paint = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyDrawBorad(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.drawLine : flag = 1;  break;
            case R.id.drawCircle : flag = 2; break;
            case R.id.drawSqure : flag = 3; break;
            case R.id.changeRed : paint.setColor(Color.RED); break;
            case R.id.changeBlue : paint.setColor(Color.BLUE); break;
            case R.id.changeGreen : paint.setColor(Color.GREEN); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Data {
        private float x = -1.0f;
        private float y = -1.0f;

        public Data(float x, float y) {
            this.x = -1.0f;
            this.y = -1.0f;
        }
    }
    private class MyDrawBorad extends View {

        private float startx;
        private float starty;
        private float stopx;
        private float stopy;
        private float movex;
        private float movey;
        private ArrayList<Data>list = new ArrayList<Data>();
        public MyDrawBorad(Context context) {
            super(context);
            this.startx = -1; starty = -1; stopx = -1; stopy = -1;
            list = new ArrayList<Data>();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            

            switch (flag){
                case LINE : canvas.drawLine(startx,starty,stopx,stopy,paint); break;
                case CIRCLE : int radius = (int)Math.sqrt(Math.pow((stopx-startx),2)+(Math.pow((stopy-starty),2)));
                    canvas.drawCircle(startx,starty,radius,paint); break;
                case SQUARE : canvas.drawRect(startx,starty,stopx,stopy,paint);
                    default: break;

            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    startx = event.getX();
                    starty = event.getY();
                    list.clear();
                    break;
                case  MotionEvent.ACTION_MOVE :
                    movex = event.getX();
                    movey = event.getY();
                    Data data = new Data(movex,movey);
                    list.add(data);
                    break;
                case  MotionEvent.ACTION_UP :
                    stopx = event.getX();
                    stopy = event.getY();
                    this.invalidate();
                    break;
                 default:  break;
            }

            return true;
        }
    }

}

