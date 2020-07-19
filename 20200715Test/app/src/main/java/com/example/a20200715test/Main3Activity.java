package com.example.a20200715test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibZoomIn;
    private ImageButton ibZoomOut;
    private ImageButton ibRotate;
    private ImageButton ibBright;
    private ImageButton ibDark;
    private ImageButton ibGray;
    private ImageButton ibBlur;
    private LinearLayout linearLayout;
    private MyPhotoShopView myPhotoShopView;
    private int flag = 1;
    private int style= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //아이디를 매치시키는 함수
        findViewByIdFunction();
        //내가 만든 이미지뷰를 불러온다.
        myPhotoShopView = new MyPhotoShopView(getApplicationContext());
        //LinearLayOut에 내가 만든 이미지 뷰를 넣는다.
        linearLayout.addView(myPhotoShopView);
        //중앙배치를한다.
        linearLayout.setGravity(Gravity.CENTER);

        //버튼에 관한 이벤트를 한번에 정리
        ibZoomIn.setOnClickListener(this);
        ibZoomOut.setOnClickListener(this);
        ibRotate.setOnClickListener(this);
        ibBright.setOnClickListener(this);
        ibDark.setOnClickListener(this);
        ibGray.setOnClickListener(this);

    }
    // 아이디를 매치 시키는 함수
    private void findViewByIdFunction() {
        ibZoomIn = findViewById(R.id.ibZoomIn);
        ibZoomOut = findViewById(R.id.ibZoomOut);
        ibRotate = findViewById(R.id.ibRotate);
        ibBright = findViewById(R.id.ibBright);
        ibDark = findViewById(R.id.ibDark);
        ibGray = findViewById(R.id.ibGray);
        ibBlur = findViewById(R.id.ibBlur);
        linearLayout = findViewById(R.id.linearLayout);
    }
    //원클릭 이벤트를 한번에 처리하는 함수
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //사진을 확대하는 이벤트
            case R.id.ibZoomIn :
                myPhotoShopView.sx += 0.1f;
                myPhotoShopView.sy += 0.1f;
                break;
            // 사진을 축소하는 이벤트
            case R.id.ibZoomOut :
                myPhotoShopView.sx -= 0.1f;
                myPhotoShopView.sy -= 0.1f;
                break;
            //사진을 회전하는 이벤트
            case R.id.ibRotate : myPhotoShopView.angle +=10.f; break;
            //사진을 밝게하는 이벤트
            case R.id.ibBright : myPhotoShopView.color +=0.2f; break;
            //사진을 어둡게 하는 이벤트
            case R.id.ibDark : myPhotoShopView.color -=0.2f; break;
            //사진을 흑백사진으로 바꾸는 이벤트
            case R.id.ibGray : if(flag == 1){
                myPhotoShopView.sature = 0.0f;
                flag= 0;
            }else if (flag == 0){
                myPhotoShopView.sature = 1.0f;
                flag = 1;
            }
            break;
            //사진에 블러링 효과 주기
            case R.id.ibBlur :
                switch (style){
                    case 1 : myPhotoShopView.style = 2;
                        style = 2;
                        break;
                    case 2 : myPhotoShopView.style = 3;
                        style = 3;
                        break;
                    case 3 : myPhotoShopView.style = 4;
                        style = 4;
                        break;
                    case 4 : myPhotoShopView.style = 1;
                        style = 1;
                        break;
            }break;

            default: break;
        }
        myPhotoShopView.invalidate();
    }
}
