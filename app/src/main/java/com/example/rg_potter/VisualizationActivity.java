package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.example.rg_potter.data.Global;

public class VisualizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("VisualizationActivity", "Activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualization);
        start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    private void start () {
        loadUser();
    }

    private void loadUser (){

        SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");

        ((TextView) findViewById(R.id.txtsexo2)).setText(this.getString(Global.user.getGender().NameResource));
        ((TextView) findViewById(R.id.txtname2)).setText(Global.user.Name.length() > 0? Global.user.Name.substring(0, 1).toUpperCase() + Global.user.Name.substring(1) : "");
        ((TextView) findViewById(R.id.txtbirthday2)).setText(DateFor.format(Global.user.Birth.getTime()));
        ((TextView) findViewById(R.id.txtpatrono2)).setText(Global.user.Patronus.length() > 0? Global.user.Patronus.substring(0, 1).toUpperCase() + Global.user.Patronus.substring(1) : "");
        ((TextView) findViewById(R.id.txthouse2)).setText(this.getString(Global.user.getHouse().NameResource));

       ((ImageView) findViewById(R.id.imgHouse)).setImageBitmap(drawableToBitmap(getDrawable(Global.user.getHouse().Image)));

       findViewById(R.id.frame).setBackgroundColor(getColor(Global.user.getHouse().Color));

    }

    public void Edit(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void Curiosity (View view){
        Intent intent = new Intent(this, CuriosityActivity.class);
        startActivity(intent);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}