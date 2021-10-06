package com.example.timerapp;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Declare object references
    SeekBar timer_sb;
    TextView timer_tv;
    Button start_btn;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    Boolean counterIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer_sb = findViewById(R.id.timer_sb);
        timer_tv = findViewById(R.id.timer_tv);
        start_btn = findViewById(R.id.start_btn);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.app_src_main_res_raw_alarm);

        timer_sb.setMax(300);

        timer_sb.setProgress(30);

        timer_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start_timer(View view) {

        if(counterIsActive == false){
            counterIsActive = true;
            timer_sb.setEnabled(false);
            start_btn.setText("STOP");

            countDownTimer = new CountDownTimer(timer_sb.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    update((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    reset();

                    if (mediaPlayer != null)
                        mediaPlayer.start();
                }
            }.start();
        }else{

            reset();
        }
    }

    // Define reset() method
    private void reset() {
        timer_tv.setText("0:30");
        timer_sb.setProgress(30);
        countDownTimer.cancel();
        start_btn.setText("START");
        timer_sb.setEnabled(true);
        counterIsActive = false;
    }


    private void update(int progress) {

        int minutes = progress / 60;

        int seconds = progress % 60;
        String secondsFinal = "";

        if(seconds <= 9){
            secondsFinal = "0" + seconds;
        }else{
            secondsFinal = "" + seconds;
        }

        timer_sb.setProgress(progress);
        timer_tv.setText("" + minutes + ":" + secondsFinal);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }
}