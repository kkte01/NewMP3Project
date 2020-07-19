package com.example.a20200715test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout slidingLinear;
    private Button btnMoveFrag1;
    private Button btnMoveFrag2;
    private Button btnMoveFrag3;
    private Animation translate_right;
    private Animation translate_left;
    private Boolean flag = false;
    private FrameLayout frameLayout;
    public Fragment fragment1;
    public Fragment fragment2;
    public Fragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //아이디를 멤버변수와 연결시키는 함수
        findViewByIdFunction();
        //fragment를 지정하는 함수
        changeScreen(1);
        btnMoveFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlebtnMove(view);
            }
        });
        btnMoveFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlebtnMove(view);
            }
        });
        btnMoveFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlebtnMove(view);
            }
        });

    }
    // 슬라이딩 메뉴 클릭시 이벤트 핸들러 함수
    private void handlebtnMove(View view) {
        String text = ((Button)view).getText().toString();
        switch (text){
            case "FragmentScreen1" : changeScreen(1); slidingLinear.startAnimation(translate_left);
                flag = false; break;
            case "FragmentScreen2" : changeScreen(2);  slidingLinear.startAnimation(translate_left);
                flag = false; break;
            case "FragmentScreen3" : changeScreen(3);  slidingLinear.startAnimation(translate_left);
                flag = false; break;
        }
    }

    //fragment를 지정하는 함수
    public void changeScreen(int i) {
        switch (i){
            case 1 : getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment1).commit(); break;
            case 2 : getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment2).commit(); break;
            case 3 : getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment3).commit(); break;
        }
    }

    //아이디를 멤버변수와 연결시키는 함수
    private void findViewByIdFunction() {
        slidingLinear = findViewById(R.id.slidingLinear);
        slidingLinear.setVisibility(View.INVISIBLE);
        btnMoveFrag1 = findViewById(R.id.btnMoveFrag1);
        btnMoveFrag2 = findViewById(R.id.btnMoveFrag2);
        btnMoveFrag3 = findViewById(R.id.btnMoveFrag3);
        frameLayout = findViewById(R.id.frameLayout);
        translate_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right);
        translate_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
    }

    //옵션메뉴를 메모리에 올려 객체화 시키기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //버튼 클릭에 대한 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.oMSlide : moveSlide(); break;
            case R.id.oMchageScreen1 :
                Intent intent = new Intent(getApplicationContext(),Sub1Activity.class);
                startActivity(intent); finish(); break;
                case R.id.oMchageScreen2 :
                Intent intent1 = new Intent(getApplicationContext(),Sub2Activity.class);
                startActivity(intent1); finish(); break;
            case R.id.changeFragment1 : changeScreen(1); break;
            case R.id.changeFragment2 : changeScreen(2); break;
            case R.id.changeFragment3 : changeScreen(3); break;
            case R.id.oMMoveDrawBoard : Intent intent2 = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent2); finish(); break;
            case R.id.oMPhotoShop : Intent intent3 = new Intent(getApplicationContext(),Main3Activity.class);
                startActivity(intent3); finish(); break;

        }
        return super.onOptionsItemSelected(item);
    }
    //슬라이딩 애니메이션에 관한 이벤트 처리
    private void moveSlide(){
        slidingLinear.bringToFront();
        if(flag == false){
            slidingLinear.startAnimation(translate_right);
            flag = true;
        }else if(flag == true){
            slidingLinear.startAnimation(translate_left);
            flag = false;
        }
    }
}
