package com.beserrovsky.rgpotter.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SpellFeedbackView extends View {
    public SpellFeedbackView(Context context) {
        super(context);
        init();
    }

    public SpellFeedbackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpellFeedbackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private class FeedbackCircle {
        public static final int MAX_RADIUS = 400;

        Paint paint;
        public int radius;

        public FeedbackCircle(int aggressiveness, int progress){
            this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Update(aggressiveness, progress);
        }

        public void Update(int aggressiveness, int progress) {
            setColor(aggressiveness);
            setRadius(progress);
        }

        public void Render(Canvas canvas){
            canvas.drawCircle(MAX_RADIUS, MAX_RADIUS, radius, paint);
        }

        private void setRadius(int progress){
            radius = (int)Math.round(((double) progress) / 100 * MAX_RADIUS);
        }

        private final int RED = 0, GREEN = 0, BLUE = 255;
        private void setColor(int aggressiveness){
            aggressiveness = aggressiveness % 256;
            int red = (int)Math.round(((double) aggressiveness) / 100 * 255);
            paint.setARGB(255, RED + red, GREEN, BLUE - red);
        }
    }
    public final int
            DEFAULT_AGGRESSIVENESS = 0,
            DEFAULT_PROGRESS = 75;

    private FeedbackCircle circle;
    private int
            aggressiveness = DEFAULT_AGGRESSIVENESS,
            progress = DEFAULT_PROGRESS;

    private void init() {
        this.circle = new FeedbackCircle(DEFAULT_AGGRESSIVENESS, DEFAULT_PROGRESS);
    }

    public void Update(int aggressiveness, int progress) {
        this.aggressiveness = aggressiveness;
        this.progress = progress;
        circle.Update(aggressiveness, progress);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circle.Render(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(circle.MAX_RADIUS * 2, circle.MAX_RADIUS * 2);
    }
}
