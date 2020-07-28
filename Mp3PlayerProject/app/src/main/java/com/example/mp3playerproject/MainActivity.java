package com.example.mp3playerproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    private MyMusicDBOpenHelper myMusicDBOpenHelper;
    private Fragment fragment;
    private int position;


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
        //DB생성
        myMusicDBOpenHelper = new MyMusicDBOpenHelper(getApplicationContext(),1);
        myMusicDBOpenHelper.setContext(MainActivity.this);

        //어댑터 생성
        musicListAdapter = new RecyclerMusicListAdapter(getApplicationContext(),arrayList);
        musicListAdapter.setContext(getApplicationContext());
        LikemusicListAdapter = new RecyclerMusicListAdapter(getApplicationContext(),arrayLikeList);
        LikemusicListAdapter.setContext(getApplicationContext());

        //LinearLayoutManager 설정
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutLikeManager = new LinearLayoutManager(getApplicationContext());


        //음악파일을 가져와서 DB에 저장
        //boolean flag = myMusicDBOpenHelper.insertMusicDatabase();
        /*if(flag != false){
            Toast.makeText(getApplicationContext(),"데이터불러오기 성공",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"데이터불러오기 실패",Toast.LENGTH_SHORT).show();
        }*/

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new MusicPlayerFragment();
            ft.replace(R.id.frameLayout,fragment);
            ft.commit();

        //arrayList에 넣기
        arrayList = myMusicDBOpenHelper.compareArrayList();
        arrayLikeList = myMusicDBOpenHelper.setLikeMusicDataList();

        //RecycleView에 넣어주기
        recyclerView.setAdapter(musicListAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerLike.setAdapter(LikemusicListAdapter);
        recyclerLike.setLayoutManager(linearLayoutLikeManager);

        //어댑터 리스트 연결해주기
        musicListAdapter.setDataArrayList(arrayList);
        //LikemusicListAdapter.setDataArrayList(arrayLikeList);


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

        //RecycleView에 클릭에 관한 이벤트 등록
        /*musicListAdapter.setItemClickListener(new RecyclerMusicListAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, int pos) {
                MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                musicPlayerFragment.selectedMusicPlayAndScreenSetting(arrayList,musicListAdapter.getAdapterPosition());
            }
        });*/
        musicListAdapter.setOnItemClickListener(new RecyclerMusicListAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, int pos) {
                ((MusicPlayerFragment)fragment).selectedMusicPlayAndScreenSetting(pos);
                Toast.makeText(getApplicationContext(),"위치 = "+pos,Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public ArrayList<MusicData> getArrayList() {
        return arrayList;
    }

    public ArrayList<MusicData> getArrayLikeList() {
        return arrayLikeList;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MyMusicDBOpenHelper getMyMusicDBOpenHelper() {
        return myMusicDBOpenHelper;
    }

    public void setMyMusicDBOpenHelper(MyMusicDBOpenHelper myMusicDBOpenHelper) {
        this.myMusicDBOpenHelper = myMusicDBOpenHelper;
    }
}
