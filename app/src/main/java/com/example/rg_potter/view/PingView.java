package com.example.rg_potter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PingView extends View {

    private static final int RADIUS = 21;

    Paint myPaint;

    public PingView(Context context) {
        super(context);
        init();
    }

    public PingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setColor(Color.RED);
    }

    public void setColor(int color){
        myPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(RADIUS, RADIUS, RADIUS, myPaint);
    }
}
