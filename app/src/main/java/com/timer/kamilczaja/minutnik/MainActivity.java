package com.timer.kamilczaja.minutnik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {




    boolean pause_mode = false;

    TimerAttr timer;

    Button  s1p,s1m,
            s2p,s2m,
            m1p,m1m,
            m2p,m2m,
            start,pause,stop;

    TextView s1,s2,m1,m2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetComponentsLayout();
        timer = new TimerAttr(s1,s2,m1,m2,this);
    }

    private void GetComponentsLayout(){

        s1p = (Button)findViewById(R.id.s1p);
        s1p.setOnClickListener(this);
        s2p = (Button)findViewById(R.id.s2p);
        s2p.setOnClickListener(this);
        s1m = (Button)findViewById(R.id.s1m);
        s1m.setOnClickListener(this);
        s2m = (Button)findViewById(R.id.s2m);
        s2m.setOnClickListener(this);

        m1p = (Button)findViewById(R.id.m1p);
        m1p.setOnClickListener(this);
        m2p = (Button)findViewById(R.id.m2p);
        m2p.setOnClickListener(this);
        m1m = (Button)findViewById(R.id.m1m);
        m1m.setOnClickListener(this);
        m2m = (Button)findViewById(R.id.m2m);
        m2m.setOnClickListener(this);

        s1 = (TextView) findViewById(R.id.s1);
        s2 = (TextView) findViewById(R.id.s2);
        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);




        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);
        pause = (Button)findViewById(R.id.pause);
        pause.setOnClickListener(this);
        stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(this);

        pause.setEnabled(false);
        stop.setEnabled(false);


    }

    private void ToggleChangeButtons(){
        s1p.setEnabled(!s1p.isEnabled());
        s1m.setEnabled(!s1m.isEnabled());
        s2p.setEnabled(!s2p.isEnabled());
        s2m.setEnabled(!s2m.isEnabled());
        m1p.setEnabled(!m1p.isEnabled());
        m1m.setEnabled(!m1m.isEnabled());
        m2p.setEnabled(!m2p.isEnabled());
        m2m.setEnabled(!m2m.isEnabled());

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.s1p:{
                timer.Add10Second();
                break;
            }

            case R.id.s2p:{
                timer.AddSecond();
                break;
            }

            case R.id.s1m:{
                timer.Sub10Second();
                break;
            }

            case R.id.s2m:{
                timer.SubSecond();
                break;
            }

            case R.id.m1p:{
                timer.Add10Minute();
                break;
            }

            case R.id.m2p:{
                timer.AddMinute();
                break;
            }

            case R.id.m1m:{
                timer.Sub10Minute();
                break;
            }

            case R.id.m2m:{
                timer.SubMinute();
                break;
            }
            case R.id.start:{

                    timer.CountStart();
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    stop.setEnabled(true);
                    ToggleChangeButtons();


                break;
            }
            case R.id.stop:{
                timer.CountStop();
                start.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
                ToggleChangeButtons();
                break;
            }
            case R.id.pause:{

                timer.CountPause();
                ToggleChangeButtons();
                if(!start.isEnabled()){
                    start.setEnabled(true);
                    pause.setEnabled(false);
                    stop.setEnabled(false);
                }
                else{
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    stop.setEnabled(true);
                }
                break;
            }
        }

        timer.SetTextView();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        boolean[] buttons = {start.isEnabled(),pause.isEnabled(),stop.isEnabled()};

        outState.putIntArray("time", timer.serialize());
        outState.putBooleanArray("buttons", buttons);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        timer.deserialize(savedInstanceState.getIntArray("time"));
        timer.SetTextView();

        boolean[] buttons =  savedInstanceState.getBooleanArray("buttons");
        start.setEnabled(buttons[0]);
        pause.setEnabled(buttons[1]);
        stop.setEnabled(buttons[2]);

        if(!start.isEnabled())
            ToggleChangeButtons();

        super.onRestoreInstanceState(savedInstanceState);
    }



}
