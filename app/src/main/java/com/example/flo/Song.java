package com.example.flo;

import android.media.MediaPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Song {
    private String singer;
    private String album;
    private String title;
    private String duration;
    private String imageURL;
    private String fileURL;
    private ArrayList<String> lyrics = new ArrayList<>();
    private ArrayList<String> lyricsTime = new ArrayList<>();

    MediaPlayer mediaPlayer;

    public Song(JSONObject jsonObject) {
        try {
            this.singer = jsonObject.getString("singer");
            this.album = jsonObject.getString("album");
            this.title = jsonObject.getString("title");
            this.duration = jsonObject.getString("duration");
            this.imageURL = jsonObject.getString("image");
            this.fileURL = jsonObject.getString("file");
            String[] src = jsonObject.getString("lyrics").split("\\n");
            for(int i = 0; i < src.length; i++) {
                src[i] = src[i].replaceAll("\\[", "");
                String[] ls = src[i].split("]");
                lyrics.add(ls[1]);
                lyricsTime.add(ls[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getLyrics() {
        String lSrc = "";
        for(String x : lyrics) {
            lSrc += x + "\n";
        }
        return lSrc;
    }

    public void setLyrics(ArrayList<String> lyrics) {
        this.lyrics = lyrics;
    }

    public ArrayList<String> getLyricsTime() {
        return lyricsTime;
    }

    public void setLyricsTime(ArrayList<String> lyricsTime) {
        this.lyricsTime = lyricsTime;
    }
}