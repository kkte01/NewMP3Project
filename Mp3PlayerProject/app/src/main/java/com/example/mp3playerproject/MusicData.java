package com.example.mp3playerproject;

import android.graphics.Bitmap;

public class MusicData {
    private Bitmap MusicAlbum;
    private String MusicName;
    private String Artist;
    private int count;
    private boolean liked;
    private String Duration;

    public MusicData(Bitmap musicAlbum, String musicName, String artist, int count, boolean liked, String duration) {
        MusicAlbum = musicAlbum;
        MusicName = musicName;
        Artist = artist;
        this.count = count;
        this.liked = liked;
        Duration = duration;
    }

    public Bitmap getMusicAlbum() {
        return MusicAlbum;
    }

    public void setMusicAlbum(Bitmap musicAlbum) {
        MusicAlbum = musicAlbum;
    }

    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
        MusicName = musicName;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
