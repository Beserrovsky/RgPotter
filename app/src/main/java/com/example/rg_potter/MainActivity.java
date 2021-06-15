package com.example.rg_potter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rg_potter.data.Global;
import com.example.rg_potter.entity.User;
import com.example.rg_potter.view.PingView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnSuccessListener<Location>, OnFailureListener {

    Location location;
    Address address;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "Activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        if(address==null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                final FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
                fusedLocationProviderClient.getLastLocation().addOnFailureListener(this);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Log.d("MainActivity", "Permissão para localização não concedida, utilizando Brasil!");
                this.location = new Location("me");
                this.location.setLatitude(-23.520835998942797);
                this.location.setLongitude(-46.72865548277011);
                defineAddress(getAddress(this.location.getLatitude(),this.location.getLongitude()));
            }
        }
    }

    public void Visualization (View view){
        Intent intent = new Intent(this, VisualizationActivity.class);
        startActivity(intent);
    }

    public void Spell (View view){
        Intent intent = new Intent(this, LumusActivity.class);
        startActivity(intent);
    }

    public void More (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://harrypotter.fandom.com/pt-br/wiki/P%C3%A1gina_Principal"));
        startActivity(intent);
    }

    @Override
    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
        Log.d("MainActivity", "Erro ao localizar dispositivo, utilizando Brasil!");
        this.location = new Location("me");
        this.location.setLatitude(-23.520835998942797);
        this.location.setLongitude(-46.72865548277011);
        defineAddress(getAddress(this.location.getLatitude(),this.location.getLongitude()));
    }

    @Override
    public void onSuccess(Location location) {
        if(location != null) {
            this.location = location;
            Log.d("MainActivity", "Dispositivo localizado com sucesso!");
            ((PingView) findViewById(R.id.Ping)).setColor(Color.GREEN);
            defineAddress(getAddress(this.location.getLatitude(),this.location.getLongitude()));
        }
    }

    private void defineAddress(String countryName){
        ((TextView) findViewById(R.id.txtCountry)).setText(countryName);
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                //result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage());
        }
        return result.toString();
    }

}