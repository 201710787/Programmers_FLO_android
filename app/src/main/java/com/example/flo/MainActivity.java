package com.example.flo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;



public class MainActivity extends AppCompatActivity {

    private final String URL = "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/2020-flo/song.json";
    private String musicURL;
    private RequestQueue queue;

    private TextView musicTitle;
    private TextView musicSinger;
    private ImageView musicImage;
    private TextView musicLyrics;
    private SeekBar musicSeekBar;

    private ImageButton musicPlay;
    private ImageButton musicStop;
    private ImageButton musicPost;
    private ImageButton musicPre;

    private ImageButton showLyrics;

    private boolean playButton = false;
    private boolean init = false;
    private int position = 0;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicTitle = findViewById(R.id.music_title);
        musicSinger = findViewById(R.id.music_singer);
        musicImage = findViewById(R.id.music_image);
        musicLyrics = findViewById(R.id.music_lyrics);
        musicSeekBar = findViewById(R.id.music_seekbar);

        musicPlay = findViewById(R.id.music_play_button);
        musicPost = findViewById(R.id.music_post_button);
        musicPre = findViewById(R.id.music_pre_button);

        showLyrics = findViewById(R.id.music_show_lyrics);

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
                            musicURL = song.getFileURL();
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(musicURL);
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                            currentPosition = mediaPlayer.getCurrentPosition();
                            musicPlay.setOnClickListener(new ImageButton.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!playButton) {
                                        musicPlay.setImageResource(R.drawable.stop);
                                        if(!init) {
                                            mediaPlayer.start();
                                            init = true;
                                        } else {
                                            mediaPlayer.seekTo(position);
                                            mediaPlayer.start();
                                        }
                                        playButton = true;
                                    } else {
                                        musicPlay.setImageResource(R.drawable.play);
                                        position = mediaPlayer.getCurrentPosition();
                                        mediaPlayer.pause();
                                        playButton = false;
                                    }
                                }
                            });
                            showLyrics.setOnClickListener(new ImageButton.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder lyricsDialog = new AlertDialog.Builder(MainActivity.this);
                                    lyricsDialog.setTitle("가사"); //제목
                                    lyricsDialog.setMessage(song.getLyrics()); // 메시지
                                    lyricsDialog.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    lyricsDialog.show();
                                }
                            });
                        } catch (JSONException | IOException e) {
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