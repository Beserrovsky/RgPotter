package com.example.rg_potter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rg_potter.data.Global;
import com.example.rg_potter.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SignUpActivity extends AppCompatActivity {

    private Date date = Global.user.Birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SignUpActivity", "Activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sign_up);

        loadUser();
    }

    private void loadUser(){

        ((EditText) findViewById(R.id.txtName)).setText(Global.user.Name);
        ((EditText) findViewById(R.id.txtPatronus)).setText(Global.user.Patronus);


        String[] houses = { this.getString(R.string.house_gryffindor), this.getString(R.string.house_hufflepuff), this.getString(R.string.house_ravenclaw), this.getString(R.string.house_slytherin), this.getString(R.string.house_none)};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        houses); //selected item will look like a spinner set from XML

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        ((Spinner) findViewById(R.id.spinnerHouses)).setAdapter(spinnerArrayAdapter);
        ((Spinner) findViewById(R.id.spinnerHouses)).setSelection(Global.user.getHouse().SpinnerIndex);

        if(Global.user.getGender().PronounResource == R.string.gender_m_pronoun){
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
        Global.user.setGender(((RadioButton) findViewById(R.id.radioButtonF)).isChecked()? "female": "male");

        String value = ((Spinner) findViewById(R.id.spinnerHouses)).getSelectedItem().toString();

        //TODO: That's gotta be a better logic here!

        String g = this.getString(R.string.house_gryffindor);
        String h = this.getString(R.string.house_hufflepuff);
        String r = this.getString(R.string.house_ravenclaw);
        String s = this.getString(R.string.house_slytherin);
        String n = this.getString(R.string.house_none);

        if(value.equals(g)) Global.user.setHouse("gryffindor");
        if(value.equals(h)) Global.user.setHouse("hufflepuff");
        if(value.equals(r)) Global.user.setHouse("ravenclaw");
        if(value.equals(s)) Global.user.setHouse("slytherin");
        if(value.equals(n)) Global.user.setHouse("");

        Global.user.save(this);

        Global.user = new User(0, this);

        Context context = getApplicationContext();
        CharSequence text = this.getString(R.string.saved);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void selectDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this, mDateSetListener, y, m,
                    d);
        }
        return null;
    }

    private final DatePickerDialog.OnDateSetListener mDateSetListener =
            (view, y, m, d) -> {

                date = new GregorianCalendar(y, m, d).getTime();

                Log.d("Debug date", date.toString());
            };

}