package com.example.sherifcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class Calendar extends RecyclerView {
   private int startYear  ;
    private  int startAfter ;
    private boolean enableRange ;
    private String from = "", start_date ="",to = "", end_date ="";
    private int lastPosition = 0;
    private Globals g ;
    public Calendar(@NonNull Context context) {
        super(context);
        initAdapter();
    }

    public Calendar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
        initAdapter();

    }

    public Calendar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(attrs);
        initAdapter();
    }
   private void setAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Calendar);
        startYear = typedArray.getInt(R.styleable.Calendar_StartYear,2019);
        startAfter= typedArray.getInt(R.styleable.Calendar_StartAfter,3);
        enableRange = typedArray.getBoolean(R.styleable.Calendar_EnableRange,false);
        typedArray.recycle();
    }
    private void initAdapter (){
        g = new Globals(getContext());
        ArrayList<CalendarClass> arrayList = new ArrayList<>();
        for (int i = 0 ; i <=startAfter ; i++){
            arrayList.add(new CalendarClass("" , 0,0, startYear , false , false));
        }
        int[] monthLength = {31,28,31,30,31,30,31,31,30,31,30,31};
        int[] monthLength2 = {31,29,31,30,31,30,31,31,30,31,30,31};
        for (int y = startYear ; y<startYear+10;y++){
            for (int m = 0 ; m<12;m++){
                for (int d = 0 ; d<(isLargeYear(y)?(monthLength2[m]+7):monthLength[m]+7);d++){
                    if (d < ( isLargeYear(y)?(monthLength2[m]):monthLength[m] )  ) {
                        arrayList.add(new CalendarClass("" + (d+1),(d+1), m+1,  y, false , true));
                    }else{
                        arrayList.add(new CalendarClass("",0, m+1,  y, false , false));
                    }
                }
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //int type=adapter.getItemViewType(position);
                CalendarClass testClass = arrayList.get(position);
                String txt = testClass.getDayTxt();
                if ( isDivid7(position)&&txt.length()==0){
                    return 7 ;
                }else {
                    return 1;
                }
            }
        });
        com.example.sherifcalendar.Adapter adapter = new com.example.sherifcalendar.Adapter(arrayList,getContext());
        this.setAdapter(adapter);
        this.setLayoutManager(gridLayoutManager);
        adapter.setmOnEntryClickListener((view, position, calendarClass) -> {

            boolean isCrossRange = true; // check if range contain blocked days
            for (int i = lastPosition; i <= position; i++) {
                if (!arrayList.get(i).isAvailable()) {
                    isCrossRange = false;
                    break;
                }
            }

            if (calendarClass.isDay() && calendarClass.isAvailable()) {
                calendarClass.setChecked(true);
                String date = calendarClass.getFullDate();
                if (from.length() == 0) {
                    from = date;
                    start_date = date;
                    end_date = "";
                    g.myToast(from + to);
                    lastPosition = position;
                    arrayList.get(position).setChecked(true);
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i != position) {
                            arrayList.get(i).setChecked(false);
                        }
                    }
                } else if (to.length() == 0 && position > lastPosition && isCrossRange && enableRange) {
                    String temp = from;
                    from = " من " + temp;
                    to = " الى " + date;
                    end_date = date;
                    g.myToast(from + to);
                    // add background in range
                    for (int i = lastPosition; i < position; i++) {
                        arrayList.get(i).setInRange(true);
                    }
                    //end part
                    lastPosition = position;
                    arrayList.get(position).setChecked(true);
                } else {
                    to = "";
                    end_date = "";
                    from = date;
                    start_date = date;
                    g.myToast(from + to);
                    lastPosition = position;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i != position) {
                            arrayList.get(i).setChecked(false);
                            arrayList.get(i).setInRange(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else if (!calendarClass.isAvailable() && calendarClass.isDay()) {
                g.myToast("لا يمكن الحجز في هذا التاريخ");
            }
        });
    }
    private boolean isLargeYear (int year){
        int outOfDiv = year / 4;
        return  (outOfDiv * 4) == year;
    }
    private boolean isDivid7(int number){
        int x = number/7;
        int y = x*7 ;
        return y == number;
    }
    String getStart_date (){
        return start_date ;
    }
    String getEnd_date (){
        return end_date ;
    }
    boolean isEnableRange (){
        return enableRange ;
    }
}