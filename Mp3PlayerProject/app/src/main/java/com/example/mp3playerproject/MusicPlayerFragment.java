package com.example.mp3playerproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayerFragment extends Fragment {
    private ImageView imgFragment;
    private TextView tvFragCount,tvFragMusicName,tvFragArtist,musicCurrent,musicDuration;
    private SeekBar seekBar;
    private ImageButton ibLike,ibPrevious,ibStart,ibNext;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String path;
    private ArrayList<MusicData>arrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musicplayfragment1,container,false);
        //아이디 찾는 함수
        findViewByIdFunction(view);
        //파일을 가져올 경로를 설정한다.
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Music/";
        //경로에서 MP3파일만 가져오는 함수
        loadSelectMp3FileFromPath();
        return view;
    }

    //아이디 찾는 함수
    private void findViewByIdFunction(View view) {
        imgFragment = view.findViewById(R.id.imgFragment);
        tvFragCount = view.findViewById(R.id.tvFragCount);
        tvFragMusicName = view.findViewById(R.id.tvFragMusicName);
        tvFragArtist = view.findViewById(R.id.tvFragArtist);
        musicCurrent = view.findViewById(R.id.musicCurrent);
        musicDuration = view.findViewById(R.id.musicDuration);
        seekBar = view.findViewById(R.id.seekBar);
        ibLike = view.findViewById(R.id.ibLike);
        ibPrevious = view.findViewById(R.id.ibPrevious);
        ibStart = view.findViewById(R.id.ibStart);
        ibNext = view.findViewById(R.id.ibNext);
    }

    //경로에서 MP3파일만 가져오는 함수
    private void loadSelectMp3FileFromPath() {
        //경로에 안에 있는 파일을 다 가져오기
        File[] files = new File(path).listFiles();
        //파일을 메타데이터를 가져오기 위해 설정
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        //확장자를 구하기 위한 MimeTypeMap 생성
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();


        /*//확장자 가져오기
        for(File file : files){
            String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String fileName = file.getName();
            //확장자가 mp3인 파일들만 걸러낸다.
            if(extension.equals("mp3")){
                //데이터 소스 지정
                media.setDataSource(path+fileName);
                //메타데이터에 있는 사진을 byte배열로 저장
                byte[] bytes=media.getEmbeddedPicture();
                //메타데이터에 있는 사진을 넣을 비트맵 설정
                Bitmap bitmap = null;
                if(bytes != null){
                    bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                }else{
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.musicimage);
                }
                String musicName = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artistName = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String duration = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                MusicData musicData = new MusicData(bitmap,musicName,artistName,0,false,duration);
                arrayList.add(musicData);
            }
        }*/
    }
}
