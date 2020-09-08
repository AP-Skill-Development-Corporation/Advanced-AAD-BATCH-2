package com.example.devicelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this);

    }

    public void getDetails(View view) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            myDeviceLocation();
        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    public void myDeviceLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                Geocoder geocoder = new Geocoder(MainActivity.this,
                        Locale.getDefault());
                try {
                    List<Address> list = geocoder.getFromLocation(location.getLatitude(),
                            location.getLongitude(),10);
                    String latitude = String.valueOf(list.get(0).getLatitude());
                    String longitude = String.valueOf(list.get(0).getLongitude());
                    String CountryName = list.get(0).getCountryName();
                    String countryCode = list.get(0).getCountryCode();
                    String locality = list.get(0).getLocality();
                    String postalCode = list.get(0).getPostalCode();
                    String address = list.get(0).getAddressLine(0);
                    tv.setText(latitude+","+longitude+"\n"
                            +CountryName+"\n"
                            +countryCode+"\n"
                            +locality+"\n"
                            +postalCode+"\n"
                            +address);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}