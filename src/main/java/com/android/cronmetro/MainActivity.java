package com.android.cronmetro;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewHour;
    private TextView textViewMinute;
    private TextView textViewSecond;
    private ImageButton btnPlayPause;
    private ImageButton btnRefresh;
    private int seconds = 0;
    private boolean isPlaying = false;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seconds ++;
            changeText();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textViewHour = (TextView) findViewById(R.id.text_view_hour);
        textViewMinute = (TextView) findViewById(R.id.text_view_minute);
        textViewSecond = (TextView) findViewById(R.id.text_view_second);
        
        btnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        btnPlayPause.setOnClickListener(this);
        btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_pause:
                btnPlayPause();
                break;
            case R.id.btn_refresh:
                btnRefresh();
                break;
        }
    }

    private void btnRefresh() {
        this.seconds = -1;
    }

    private void btnPlayPause() {
        if(!isPlaying) start();
        else pause();
        isPlaying = !isPlaying;
        btnPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause_black_32dp : R.drawable.ic_play_arrow_black_32dp);
    }

    private void start() {
        handler.postDelayed(runnable, 1000);
    }

    private void pause() {
        handler.removeCallbacks(runnable);
    }

    private void changeText() {
        int seconds = this.seconds % 60;
        int minutes = (this.seconds / 60) % 60;
        int hour = this.seconds / 3600;

        String secondsFormatted = (seconds <= 9 ? "0" : "") + String.valueOf(seconds);
        String minutesFormatted = (minutes <= 9 ? "0" : "") + String.valueOf(minutes);
        String hoursFormatted = (hour <= 9 ? "0" : "") + String.valueOf(hour);

        textViewSecond.setText(secondsFormatted);
        textViewMinute.setText(minutesFormatted);
        textViewHour.setText(hoursFormatted);
    }
}
