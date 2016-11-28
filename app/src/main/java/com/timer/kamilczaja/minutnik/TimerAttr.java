package com.timer.kamilczaja.minutnik;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Czajmen on 19.11.2016.
 */

public class TimerAttr {

    private CountDownTimer  countDownTimer;

    private int s1,s2;
    private int m1,m2;

    private int state;

    private  TextView s1t,s2t,m1t,m2t;
    private MediaPlayer mediaPlayer;

    public TimerAttr(TextView m1,TextView m2,TextView s1,TextView s2,Activity activity){
        s1t = s1;
        s2t = s2;
        m1t = m1;
        m2t = m2;
        state=0;
         mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), R.raw.alarm);
        setTime();
    }

    private void setTime(){
        s1= Integer.parseInt(s1t.getText().toString());
        s2= Integer.parseInt(s2t.getText().toString());

        m1= Integer.parseInt(m1t.getText().toString());
        m2= Integer.parseInt(m2t.getText().toString());
    }

    public void CountStart(){
        if((s1==0) && (s2==0 )&& (m1==0) && (m2==0) )
        {
            return;
        }
        if(state == 1)
            return;
        state=1;

          countDownTimer = new CountDownTimer((s1 * 1000) + (s2 * 10000) + (m1 * 600000) + (m2 * 6000000), 10) {
            @Override
            public void onTick(long l) {
                SubSecond();
                SetTextView();
            }

            @Override
            public void onFinish() {
            //    finish();
            }
        }.start();
    }

    public  void CountStop(){
        if(state!=1)
            return;

        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        countDownTimer.cancel();
        s1=0;s2=0;m1=0;m2=0;state=0;
        SetTextView();
    }

    public void CountPause(){
        if(state!=1)
            return;
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();

        state=2;
        countDownTimer.cancel();
    }

    public void SetTextView(){
        m1t.setText(s1+"");
        m2t.setText(s2+"");
        s1t.setText(m1+"");
        s2t.setText(m2+"");
    }


    public int[] serialize(){
        CountPause();
        int[] time = {s1,s2,m1,m2,state};
        return time;
    }

    public void deserialize(int[] time){
        s1=time[0];
        s2=time[1];
        m1=time[2];
        m2=time[3];
        state=time[4];

        if(state==1 || state==2)
            this.CountStart();
    }

    private void finish(){
        countDownTimer.cancel();
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    public void AddSecond(){
        if(s2 < 9){
           s2+=1;
        }
        else{
            s2=0;
            this.Add10Second();
        }
    }

    public void Add10Second(){

         if(s1 < 6){
            s1+=1;
        }
        else {
             s1=0;
             this.AddMinute();
         }
    }
    public void AddMinute(){

        if(m2 < 6){
            m2+=1;
        }
        else{
            m2=0;
            Add10Minute();
        }
    }

    public void Add10Minute(){
        if(m1 < 9){
            m1+=1;
        }
    }

    public void SubSecond(){


        if(s1==0 && s2==0 && m1==0 && m2==0)
        {

        }
        else if(s2 > 0){
            s2-=1;
        }
        else if(s2==0)
        {
            this.Sub10Second();
            s2=9;
        }
       if((s1==0) && (s2==0 )&& (m1==0) && (m2==0) && state==1)
       {
           finish();
       }
    }

    public void Sub10Second(){
        if(s1 > 0){
            s1-=1;
        }
        else if(s1==0 && m2>0){
            SubMinute();
            s1=6;
        }
        else if(s1==0 && m2==0 && m1!=0)
        {
            SubMinute();
            s1=6;
        }
    }

    public void  SubMinute(){
        if(m2 > 0){
            m2-=1;
        }
        else if(m2==0 && m1>0)
        {
            Sub10Minute();
            m2=6;

        }
    }

    public void Sub10Minute(){
        if(m1 > 0){
            m1-=1;
        }
        else{
        }
    }

}
