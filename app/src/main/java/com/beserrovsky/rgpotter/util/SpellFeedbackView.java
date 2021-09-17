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
        Paint paint;
        public FeedbackCircle(){ this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);}

        public final int MAX_RADIUS = 400;
        private int radius;

        public void Update(int aggressiveness, int progress) {
            double p = (double)progress/100 * MAX_RADIUS;
            radius = (int)Math.round(p);
            int red = (int) (Math.round(aggressiveness)%256);
            paint.setARGB(255, red, 0, (255 - red));
        }

        public void Render(Canvas canvas){
            canvas.drawCircle(MAX_RADIUS, MAX_RADIUS, radius, paint);
        }
    }
    
    private FeedbackCircle circle;
    public final int DEFAULT_AGGRESSIVENESS = 30, DEFAULT_PROGRESS = 75;
    int aggressiveness = DEFAULT_AGGRESSIVENESS, progress = DEFAULT_PROGRESS;
    
    private void init(){
        this.circle = new FeedbackCircle();
    }

    public void Update(int aggressiveness, int progress)
    {
        this.aggressiveness = aggressiveness;
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circle.Update(aggressiveness, progress);
        circle.Render(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(circle.MAX_RADIUS*2, circle.MAX_RADIUS*2);
    }
}
