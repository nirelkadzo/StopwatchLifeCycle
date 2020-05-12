package com.kadzo.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    private void runTimer(){
        final TextView timeView=findViewById(R.id.time_view);

        final Handler handler=new Handler();
        handler.post(new Runnable() {


            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null) {
        seconds=savedInstanceState.getInt("seconds");
        running=savedInstanceState.getBoolean("running");
        wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    @Override
    public void onSavedInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

    }


    @Override
    protected void onStop(){
        super.onStop();
        wasRunning=running;
        running=false;
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(wasRunning){
            running=true;
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running=false;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }
    public void onClickStart(View view) {
        running=true;
    }

    public void onClickStop(View view) {
        running=false;
    }

    public void onClickReset(View view) {
        running=false;
        seconds=0;
    }
}
