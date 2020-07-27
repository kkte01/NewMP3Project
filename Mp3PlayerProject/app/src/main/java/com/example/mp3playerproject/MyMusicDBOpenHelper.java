package com.example.mp3playerproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MyMusicDBOpenHelper extends SQLiteOpenHelper {
    private Context context;
    //DB생성을 위한 생성자
    public MyMusicDBOpenHelper(@Nullable Context context, int version) {
        super(context, "myMusicTBL", null, version);
        this.context = context;
    }
    //테이블을 만드는 함수
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table myMusicTBL("+
                "id VARCHAR(60) PRIMARY KEY,"+
                "artist VARCHAR(20),"+
                "title VARCHAR(20),"+
                "albumArt VARCHAR(20),"+
                "duration VARCHAR(20),"+
                "click INTEGER,"+
                "liked INTEGER);";
        sqLiteDatabase.execSQL(query);
    }
    //테이블을 삭제하는 함수
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "drop table if exists myMusicTBL;";
        sqLiteDatabase.execSQL(query);
        //콜백 함수 끼리는 서로 콜을 할 수 가 있다. 재생성을 위해 부른다.
        onCreate(sqLiteDatabase);
    }

    //DB에 음악리스트를 저장하는 함수
    public boolean insertMusicDatabase(){
        boolean returnValue = false;
        String[] data ={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION};
        //"ASC" 오름차순 정렬
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                data,null,null,MediaStore.Audio.Media.TITLE+" ASC");
        //db 열기
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            //db에 넣기
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //음악안에 있는 데이터들 가져오기
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    String query = "insert into myMusicTBL valuse(" +
                            "'" + id + "'," +
                            "'" + artist + "'," +
                            "'" + title + "'," +
                            "'" + albumArt + "'," +
                            "'" + duration + "'," +
                            0 + "," + 0 + ",);";
                    sqLiteDatabase.execSQL(query);
                }
            }
            returnValue = true;
            return returnValue;
        }catch (Exception e){
            Log.d("DBOpenHelper insert",e.getMessage());
            returnValue = false;
            return returnValue;
        }finally {
            //자원 반납
            sqLiteDatabase.close();
            cursor.close();
        }

    }
    //DB에 있는 음악 리스트를 RecycleView에 저장하는 함수
    public ArrayList<MusicData> setMusicDataRecycleView(){
        ArrayList<MusicData>musicData = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from myMusicTBL",null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String artist = cursor.getString(1);
            String title = cursor.getString(2);
            String albumArt = cursor.getString(3);
            String duration = cursor.getString(4);
            int click = cursor.getInt(5);
            int liked = cursor.getInt(6);

            MusicData music = new MusicData(id,artist,title,albumArt,duration,click,liked);
            musicData.add(music);
        }
        //자원 반납
        sqLiteDatabase.close();
        cursor.close();

        return musicData;
    }
    //DB에 있는 좋아요를 누른 리스트만 불러오는 함수
    public ArrayList<MusicData> setLikeMusicDataList(){
        ArrayList<MusicData>musicData = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query =  "select * from myMusicTBL where liked = 1;";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String artist = cursor.getString(1);
            String title = cursor.getString(2);
            String albumArt = cursor.getString(3);
            String duration = cursor.getString(4);
            int click = cursor.getInt(5);
            int liked = cursor.getInt(6);

            MusicData musicData1 = new MusicData(id,artist,title,albumArt,duration,click,liked);
            musicData.add(musicData1);
        }
        //자원 반납
        sqLiteDatabase.close();
        cursor.close();

        return musicData;
    }
    //데이터 베이스에서 노래를 삭제하는 함수
    public void deleteFromDB(ArrayList<MusicData>musicData,int i){
        //db열기
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //db에서 음악 삭제
        String query = "delete from myMusicTBL where id = '"+
                musicData.get(i).getId()+"';";
        sqLiteDatabase.execSQL(query);
        //자원반납
        sqLiteDatabase.close();
    }
    //좋아요 수 증감소에 관한 함수
    public void increaseOrDicreaseDatabase(ArrayList<MusicData>musicData,int i){
        //db열기
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "update myMusicTBL set liked = "+musicData.get(i).getLiked()+"where id = '"+
                musicData.get(i).getId()+"';";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }
    //카운트 수에 관한 함수
    public void increaseClickCount(ArrayList<MusicData>musicData,int i){
        //db열기
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "update myMusicTBL set click = "+musicData.get(i).getClick()+"where id = '"+
                musicData.get(i).getId()+"';";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }
}
