package com.example.a20200715test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;



public class MyPhotoShopView extends View {
    public float sx,sy,angle,color,sature;
    public int style;
    private BlurMaskFilter blurMaskFilter1;
    private BlurMaskFilter blurMaskFilter2;
    private BlurMaskFilter blurMaskFilter3;
    private BlurMaskFilter blurMaskFilter4;
    public MyPhotoShopView(Context context) {
        super(context);
        this.sx = 1.0f;
        this.sy = 1.0f;
        this.angle = 0.0f;
        this.color = 1.0f;
        this.sature = 1.0f;
        this.style= 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //5. canvas의 크기를 결정한다. px,py는 중심좌표를 구하기 sx,sy는 버튼에의해 확대 축소가 되도록 만든다.
        int px = this.getWidth()/2;
        int py = this.getHeight()/2;
        canvas.scale(sx,sy,px,py);
        //6. canvas에 각도 조절 angle에 의해 돌아간다.
        canvas.rotate(angle,px,py);
        //Paint 만들기
        Paint paint = new Paint();
        //7. 색상 밝기를 조절하는 행렬
        float[] array ={color,0,0,0,0,
                0,color,0,0,0,
                0,0,color,0,0,
                0,0,0,1,0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        //8. 흑백과 컬러가 계속 나오게 하기0.0f 는 흑백 1.0f 는 컬러
        if(sature == 0){
            colorMatrix.setSaturation(sature);
        }
        //style에 따라 blur의 변화를 주기
        switch (style){
            case 1 : blurMaskFilter1 = new BlurMaskFilter(80.0f, BlurMaskFilter.Blur.NORMAL);
                break;
            case 2 : blurMaskFilter2 = new BlurMaskFilter(80.0f, BlurMaskFilter.Blur.INNER);
                break;
            case 3 : blurMaskFilter3 = new BlurMaskFilter(80.0f, BlurMaskFilter.Blur.OUTER);
                break;
            case 4 : blurMaskFilter4 = new BlurMaskFilter(80.0f, BlurMaskFilter.Blur.SOLID);
                break;
            default:break;
        }
        //페인트에 설정하기
        switch (style){
            case 1 : paint.setMaskFilter(blurMaskFilter1);
                break;
            case 2 : paint.setMaskFilter(blurMaskFilter2);
                break;
            case 3 : paint.setMaskFilter(blurMaskFilter3);
                break;
            case 4 : paint.setMaskFilter(blurMaskFilter4);
                break;
            default:break;
        }
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        //1. 해당된 이미지를 가져와서 그린다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.charizardx);
        //2. 그 이미지를 캔버스 중앙에 오게 한다.(그 전에 중앙의 좌표를 그린다.)
        float centerx = (this.getWidth() - bitmap.getWidth())/2;
        float centery = (this.getHeight() - bitmap.getHeight())/2;
        //3. 좌표를 가지고 이제 화면을 그린다. 캔버스안에 비트맵을 넣는다.
        canvas.drawBitmap(bitmap,centerx,centery,paint);
        //4. 메모리 누적 방지를 위해 그림 올라갔으면 지워준다.
        bitmap.recycle();


    }


}
