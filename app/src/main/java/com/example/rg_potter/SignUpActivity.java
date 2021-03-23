package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    private Date date = Global.user.Birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sign_up);

        loadUser();
    }

    private void loadUser(){

        ((EditText) findViewById(R.id.txtName)).setText(Global.user.Name);
        ((EditText) findViewById(R.id.txtPatronus)).setText(Global.user.Patronus);
        // TODO: CREATE HOUSE AND BIND TO SPINNER

        Log.d("Debug", Global.user.getGender_Id(this));

        if(Global.user.getGender_Id(this).equalsIgnoreCase("M")){
            ((RadioButton) findViewById(R.id.radioButtonF)).setChecked(false);
            ((RadioButton) findViewById(R.id.radioButtonM)).setChecked(true);
        }else{
            ((RadioButton) findViewById(R.id.radioButtonM)).setChecked(false);
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

        Global.user.Name = ((EditText) findViewById(R.id.txtName)).getText().toString();
        Global.user.Birth = this.date;
        Global.user.Patronus = ((EditText) findViewById(R.id.txtPatronus)).getText().toString();
        Global.user.setGender(((RadioButton) findViewById(R.id.radioButtonF)).isChecked()? User.GenderEnum.F : User.GenderEnum.M);

        // TODO: BIND UI TO USER ATTRS

        Global.user.save(this);

        Global.user = new User(0, this);

        Context context = getApplicationContext();
        CharSequence text = this.getString(R.string.saved);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void selectDate(View view){
        // TODO: DIALOGUE FOR DATE
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