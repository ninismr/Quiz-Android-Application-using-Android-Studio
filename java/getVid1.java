package com.example.quizjam;

import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class getVid1 extends AppCompatActivity {

    DownloadManager dm;
    MediaPlayer mpv;
    long dl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

        mpv = MediaPlayer.create(getVid1.this, Uri.parse(getExternalFilesDir(Environment.
                DIRECTORY_DOWNLOADS) + "/Text.mp4"));

        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("https://jam2021.000webhostapp.com/JAM/Text.mp4");
        videoView.setVideoURI(uri);
        MediaController mc = new MediaController(this);
        DownloadManager.Request r = new DownloadManager.Request(uri);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        videoView.setMediaController(mc);

        r.setDestinationUri(Uri.fromFile(new File(getVid1.this.getExternalFilesDir(Environment
                .DIRECTORY_DOWNLOADS), "/Text.mp4")));
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dl = dm.enqueue(r);
        Toast.makeText(getBaseContext(), "Downloading in Progress!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "Download Successful!", Toast.LENGTH_LONG).show();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mpv) {
                        mpv.setLooping(true);
                        videoView.start();
                    }
                });
            }
        }, 3000);
    }
}
