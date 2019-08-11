package com.example.sherifcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Globals g;
    Calendar mCalendar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m);
        g = new Globals(this);
        mCalendar = findViewById(R.id.mCalendar);


    }

}
