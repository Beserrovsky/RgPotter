package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sign_up);

        loadUser();
    }

    private void loadUser(){

        ((EditText) findViewById(R.id.txtName)).setText(Global.user.Name);
        ((EditText) findViewById(R.id.txtBirth)).setText(Global.user.Birth.toString());
        ((EditText) findViewById(R.id.txtPatronus)).setText(Global.user.Patronus);
        // TODO: CREATE HOUSE AND BIND TO SPINNER

        if(Global.user.getGender_Id(this).equals("M")){
            ((RadioButton) findViewById(R.id.radioButtonM)).setChecked(true);
        }else{
            ((RadioButton) findViewById(R.id.radioButtonF)).setChecked(true);
        }

        if(Global.user.Img==null){

            ((ImageView) findViewById(R.id.img_cadUser)).setImageBitmap(drawableToBitmap(this.getResources().getDrawable(R.drawable.ic_launcher_foreground)));
        }else{

            Bitmap bmImg = BitmapFactory.decodeFile(Global.user.Img.getEncodedPath());
            ((ImageView) findViewById(R.id.img_cadUser)).setImageBitmap(bmImg);
        }

        // TODO: BIND USER ATTRS TO UI
    }

    public void saveUser(View view){

        // TODO: BIND UI TO USER ATTRS

        Global.user.save(this);
    }

    public void takePhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);

                    // TODO: IMPLEMENT THIS
                }
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

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