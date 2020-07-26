package com.example.mp3playerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LikeMusicLIstAdapter extends BaseAdapter {
    //화면을 받을 변수
    private Context context;
    private ArrayList<MusicData>musicData;

    public LikeMusicLIstAdapter(Context context) {
        this.context = context;
    }

    // 보여주는 갯수
    @Override
    public int getCount() {
        return musicData.size();
    }
    //몇번째를 보여줄 것 인지
    @Override
    public Object getItem(int i) {
        return musicData.get(i);
    }
    // 몇번째로 보여줄 것인지
    @Override
    public long getItemId(int i) {
        return i;
    }
    // 내가 만든 화면 객체화 시키는 곳
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //내가 만든 화면 객체화 시키기
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //보여주는 부분만 만들어놓고 나머지 데이터만 가지고 있게 해 퍼포먼스 수를 줄인다.
        if(view == null){
            view = layoutInflater.inflate(R.layout.music_data_layout,null);
        }
        //아이디 찾기
        ImageView imgAlbum = view.findViewById(R.id.imgAlbum);
        TextView tvMusicName = view.findViewById(R.id.tvMusicName);
        TextView tvArtist = view.findViewById(R.id.tvArtist);
        TextView tvCount = view.findViewById(R.id.tvCount);

        MusicData musicData1 = musicData.get(i);

        imgAlbum.setImageBitmap(musicData1.getMusicAlbum());
        tvMusicName.setText(musicData1.getMusicName());
        tvArtist.setText(musicData1.getArtist());
        tvCount.setText(String.valueOf(musicData1.getCount()));
        return view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<MusicData> getMusicData() {
        return musicData;
    }

    public void setMusicData(ArrayList<MusicData> musicData) {
        this.musicData = musicData;
    }
}
