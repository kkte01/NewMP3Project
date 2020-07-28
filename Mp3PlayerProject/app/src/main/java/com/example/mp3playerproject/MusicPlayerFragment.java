package com.example.mp3playerproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayerFragment extends Fragment implements View.OnClickListener {
    private ImageView imgFragment;
    private TextView tvFragCount,tvFragMusicName,tvFragArtist,musicCurrent,musicDuration;
    private SeekBar seekBar;
    private ImageButton ibLike,ibPrevious,ibStart,ibNext;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<MusicData>arrayList;
    private MainActivity mainActivity;
    private RecyclerMusicListAdapter recyclerMusicListAdapter;
    private MyMusicDBOpenHelper myMusicDBOpenHelper;
    private int index;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mainActivity = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musicplayfragment1,container,false);
        //아이디 찾는 함수
        findViewByIdFunction(view);
        //버튼 클릭에 대한 함수
        btnClickMethod();
        //시크바 변경에 관한 함수
        seekBarChangeMethod();



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
    //버튼 클릭에 대한 함수
    private void btnClickMethod() {
        ibStart.setOnClickListener(this);
        ibPrevious.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibLike.setOnClickListener(this);
    }

    //버튼 클릭 이벤트처리 함수
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibStart :
                if(ibStart.isActivated()){
                    mediaPlayer.pause();
                    ibStart.setActivated(false);
                }else{
                    mediaPlayer.start();
                    ibStart.setActivated(true);
                    setSeekBarThread();
                }
                break;
            case R.id.ibPrevious :
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                try {
                    if(index == 0){
                        index = mainActivity.getArrayList().size();
                    }
                    index--;
                    selectedMusicPlayAndScreenSetting(index);

                } catch (Exception e) {
                    Log.d("ubPrevious",e.getMessage());
                }
                break;
            case R.id.ibNext :
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    if(index == mainActivity.getArrayList().size()-1){
                        index= -1;
                    }
                    index++;
                    selectedMusicPlayAndScreenSetting(index);
                } catch (Exception e) {
                    Log.d("ibNext",e.getMessage());
                }
                break;
            case R.id.ibLike :
                break;
            default:break;
        }
    }
    //시크바 변경에 관한 함수
    private void seekBarChangeMethod() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //선택된 음악재생및 화면 처리에 관한 함수
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectedMusicPlayAndScreenSetting(final int position) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        index = position;
        final ArrayList<MusicData> musicData = mainActivity.getArrayList();
        MusicData data = musicData.get(position);
        recyclerMusicListAdapter = new RecyclerMusicListAdapter(mainActivity,musicData);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

        Bitmap bitmap = recyclerMusicListAdapter.getAlbumImg(mainActivity,Integer.parseInt(data.getAlbumArt()),200);
        if(bitmap != null){
            imgFragment.setImageBitmap(bitmap);
        }
        tvFragMusicName.setText(data.getTitle());
        tvFragArtist.setText(data.getArtist());
        tvFragCount.setText(String.valueOf(data.getClick()));
        musicDuration.setText(simpleDateFormat.format(Integer.parseInt(data.getDuration())));

        if(data.getLiked() ==1){
            ibLike.setActivated(true);
        }
        Uri musicURI = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,data.getId());
        try {
            mediaPlayer.setDataSource(mainActivity,musicURI);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(Integer.parseInt(data.getDuration()));
            ibStart.setActivated(true);

            setSeekBarThread();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ibNext.callOnClick();
                    myMusicDBOpenHelper = mainActivity.getMyMusicDBOpenHelper();
                    myMusicDBOpenHelper.increaseClickCount(mainActivity.getArrayList(),position);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //시크바 스레드 에 관한 함수
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setSeekBarThread(){
        Thread thread = new Thread(new Runnable() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

            @Override
            public void run() {
                while(mediaPlayer.isPlaying()){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            musicCurrent.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                        }
                    });
                    SystemClock.sleep(100);
                }
            }
        });
        thread.start();
    }
}
