package com.example.sherifcalendar;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

public class Globals {
    private Context context;

    public Globals(Context context) {
        this.context = context;
    }

    public Drawable blueShape60() {
        return shape(R.color.myDarkBlue, getScreenDpi(60), 0, 0);
    }

    public Drawable blueShape60Right() {
        return shape60Right(R.color.myDarkBlue);
    }

    public Drawable blueShape60Left() {
        return shape60Left(R.color.myDarkBlue);
    }
    public GradientDrawable shape(int colorId, int radius, int strokeWidth, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(radius);
        if (colorId != 0) {
            shape.setColor(context.getResources().getColor(colorId));
        }
        if (strokeColor != 0) {
            shape.setStroke(getScreenDpi(strokeWidth), context.getResources().getColor(strokeColor));
        }
        return shape;
    }

    public GradientDrawable shape60Right(int colorId) {
        GradientDrawable shape = new GradientDrawable();
        float[] radius = {0, 0, getScreenDpi(60), getScreenDpi(60), getScreenDpi(60), getScreenDpi(60), 0, 0};
        shape.setCornerRadii(radius);
        if (colorId != 0) {
            shape.setColor(context.getResources().getColor(colorId));
        }
        return shape;
    }

    public GradientDrawable shape60Left(int colorId) {
        GradientDrawable shape = new GradientDrawable();
        float[] radius = {getScreenDpi(60), getScreenDpi(60), 0, 0, 0, 0, getScreenDpi(60), getScreenDpi(60)};
        shape.setCornerRadii(radius);
        if (colorId != 0) {
            shape.setColor(context.getResources().getColor(colorId));
        }
        return shape;
    }
    public int getScreenDpi(int pixels) {
        float scale = context.getResources().getDisplayMetrics().density;
        pixels = (int) (pixels * scale + 0.5f);
        return pixels;
    }
    public GradientDrawable shapeColorString(String color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(radius);
        shape.setColor(Color.parseColor(color));
        return shape;
    }
    void myToast (String msg){
        Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
    }
}