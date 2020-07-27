package com.example.mp3playerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView, recyclerLike;
    private RecyclerMusicListAdapter musicListAdapter;
    private RecyclerMusicListAdapter LikemusicListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutLikeManager;
    private ArrayList<MusicData>arrayList;
    private ArrayList<MusicData>arrayLikeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIdFunction();

        //sdcard 외부접근권한 설정
        ActivityCompat.requestPermissions(this
                , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , MODE_PRIVATE);

        //어댑터 생성
        musicListAdapter = new RecyclerMusicListAdapter(getApplicationContext());
        LikemusicListAdapter = new RecyclerMusicListAdapter(getApplicationContext());
        //어댑터 리스트 연결해주기
        musicListAdapter.setDataArrayList(arrayList);
        LikemusicListAdapter.setDataArrayList(arrayLikeList);
        //LinearLayoutManager 설정
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutLikeManager = new LinearLayoutManager(getApplicationContext());

        //RecycleView에 넣어주기
        recyclerView.setAdapter(musicListAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerLike.setAdapter(LikemusicListAdapter);
        recyclerLike.setLayoutManager(linearLayoutLikeManager);
        //frameLayout drag event
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            float x1,x2,y1,y2,dx,dy;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();

                        dx = x2-x1;
                        dy  = y2-y1;
                        if(Math.abs(dx) > Math.abs((dy))){
                            if(dx > 0){
                                drawerLayout.openDrawer(Gravity.LEFT,true);
                            }else{
                                drawerLayout.openDrawer(Gravity.RIGHT,true);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    //아이디 찾는 함수
    private void findViewByIdFunction() {
        frameLayout = findViewById(R.id.frameLayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerLike = findViewById(R.id.recyclerLike);
    }
}
