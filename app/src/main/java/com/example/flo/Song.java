package com.example.flo;

import android.media.MediaPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Song {
    private String singer;
    private String album;
    private String title;
    private String duration;
    private String imageURL;
    private String fileURL;
    private ArrayList<String> lyrics = new ArrayList<>();
    private ArrayList<Integer> lyricsTime = new ArrayList<>();

    private Map<Integer, String> printLyrics = new HashMap<>();

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
            ArrayList<String> lyricsTimeSrc = new ArrayList<>();
            for(int i = 0; i < src.length; i++) {
                src[i] = src[i].replaceAll("\\[", "");
                String[] ls = src[i].split("]");
                lyrics.add(ls[1]);
                lyricsTimeSrc.add(ls[0]);
            }
            printLyrics.put(0, "전주");
            for(int i = 0; i < lyricsTimeSrc.size(); i++) {
                int k = 0;
                String[] s = lyricsTimeSrc.get(i).split(":");
                k += Integer.parseInt(s[0])*60*1000;
                k += Integer.parseInt(s[1])*1000;
                k += Integer.parseInt(s[2]);
                lyricsTime.add(k);
                printLyrics.put(k/1000, lyrics.get(i));
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

    public ArrayList<String> getLyrics() {
        return lyrics;
    }

    public String getLyricsForDialog() {
        String lSrc = "";
        for(String x : lyrics) {
            lSrc += x + "\n";
        }
        return lSrc;
    }

    public void setLyrics(ArrayList<String> lyrics) {
        this.lyrics = lyrics;
    }

    public ArrayList<Integer> getLyricsTime() {
        return lyricsTime;
    }

    public void setLyricsTime(ArrayList<Integer> lyricsTime) {
        this.lyricsTime = lyricsTime;
    }

    public Map<Integer, String> getPrintLyrics() {
        return printLyrics;
    }

    public void setPrintLyrics(Map<Integer, String> printLyrics) {
        this.printLyrics = printLyrics;
    }
}