package com.example.flo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;



public class MainActivity extends AppCompatActivity {

    private final String URL = "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-flo/song.json";
    private RequestQueue queue;

    private TextView musicTitle;
    private TextView musicSinger;
    private ImageView musicImage;
    private TextView musicLyrics;
    private SeekBar musicSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicTitle = findViewById(R.id.music_title);
        musicSinger = findViewById(R.id.music_singer);
        musicImage = findViewById(R.id.music_image);
        musicLyrics = findViewById(R.id.music_lyrics);
        musicSeekBar = findViewById(R.id.music_seekbar);

        queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Song song = new Song(jsonObject);
                            musicTitle.setText(song.getTitle());
                            musicSinger.setText(song.getSinger());
                            Glide.with(getApplicationContext())
                                    .load(song.getImageURL())
                                    .into(musicImage);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(stringRequest);

    }
}