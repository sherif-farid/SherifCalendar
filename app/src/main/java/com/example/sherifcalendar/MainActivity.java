package com.example.sherifcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Globals g;
    Calendar mCalendar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m);
        g = new Globals(this);
        mCalendar = findViewById(R.id.mCalendar);
        mCalendar.setClick(new Calendar.MyListener() {
            @Override
            public void onRecyclerClick(CalendarClass calendarClass) {
                g.myToast("currentDate="+calendarClass.getFullDate());
            }
        });


    }

}
