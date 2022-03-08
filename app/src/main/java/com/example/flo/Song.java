package com.example.flo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Song {
    String singer;
    String album;
    String title;
    String duration;
    String imageURL;
    String fileURL;
    ArrayList<Time> lyrics;

    public Song(JSONObject jsonObject) {
        try {
            this.singer = jsonObject.getString("singer");
            this.album = jsonObject.getString("album");
            this.title = jsonObject.getString("title");
            this.duration = jsonObject.getString("duration");
            this.imageURL = jsonObject.getString("image");
            this.fileURL = jsonObject.getString("file");
            String[] src = jsonObject.getString("lyrics").split("\\n");
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

    public ArrayList<Time> getLyrics() {
        return lyrics;
    }

    public void setLyrics(ArrayList<Time> lyrics) {
        this.lyrics = lyrics;
    }
}