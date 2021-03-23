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
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    private Date date = Global.user.Birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "SignUp activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sign_up);

        String[] houses = { this.getString(R.string.house_Gryffindor), this.getString(R.string.house_Hufflepuff), this.getString(R.string.house_Ravenclaw), this.getString(R.string.house_Slytherin), this.getString(R.string.house_none)};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        houses); //selected item will look like a spinner set from XML

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        ((Spinner) findViewById(R.id.spinnerHouses)).setAdapter(spinnerArrayAdapter);

        loadUser();
    }

    private void loadUser(){

        ((EditText) findViewById(R.id.txtName)).setText(Global.user.Name);
        ((EditText) findViewById(R.id.txtPatronus)).setText(Global.user.Patronus);
        // TODO: CREATE HOUSE AND BIND TO SPINNER

        ((Spinner) findViewById(R.id.spinnerHouses)).setSelection(Global.user.House.SpinnerIndex);

        Log.d("Debug", Global.user.getGender_Id(this));

        if(Global.user.getGender_Id(this).equalsIgnoreCase("M")){
            ((RadioButton) findViewById(R.id.radioButtonF)).setChecked(false);
            ((RadioButton) findViewById(R.id.radioButtonM)).setChecked(true);
        }else{
            ((RadioButton) findViewById(R.id.radioButtonM)).setChecked(false);
            ((RadioButton) findViewById(R.id.radioButtonF)).setChecked(true);
        }
    }

    public void saveUser(View view){

        Global.user.Name = ((EditText) findViewById(R.id.txtName)).getText().toString();
        Global.user.Birth = this.date;
        Global.user.Patronus = ((EditText) findViewById(R.id.txtPatronus)).getText().toString();
        Global.user.setGender(((RadioButton) findViewById(R.id.radioButtonF)).isChecked()? User.GenderEnum.F : User.GenderEnum.M);

        String value = ((Spinner) findViewById(R.id.spinnerHouses)).getSelectedItem().toString();

        String g = this.getString(R.string.house_Gryffindor);
        String h = this.getString(R.string.house_Hufflepuff);
        String r = this.getString(R.string.house_Ravenclaw);
        String s = this.getString(R.string.house_Slytherin);
        String n = this.getString(R.string.house_none);

        if(value.equals(g)) Global.user.House = new Character.House("gryffindor", this);
        if(value.equals(h)) Global.user.House = new Character.House("hufflepuff", this);
        if(value.equals(r)) Global.user.House = new Character.House("ravenclaw", this);
        if(value.equals(s)) Global.user.House = new Character.House("slytherin", this);
        if(value.equals(n)) Global.user.House = new Character.House("", this);

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

}