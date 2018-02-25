package com.example.prashant.hikerswatch;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView zero, second, fourth, sixth, eighth, tenth;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //location Manager any time the location is moved or updated in the system we wnat to know about it
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                notifyTheChanges();
            }
        }
    }


    public void notifyTheChanges() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0, locationListener);


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        zero = (TextView) findViewById(R.id.textView0);

        second = (TextView) findViewById(R.id.textView2);

        fourth = (TextView) findViewById(R.id.textView4);

        sixth = (TextView) findViewById(R.id.textView6);

        eighth = (TextView) findViewById(R.id.textView8);

        tenth = (TextView) findViewById(R.id.textView10);


        //THis will give the user location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                notifyTheChanges();

                if (location.toString() != null) {
                    updateLocation(location);
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            //for app   running twice or more once already opened
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            //for app running for the first time once previously  the permission is granted.
            Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnowLocation != null) {
                updateLocation(lastKnowLocation);
            }
        }


    }


    public void updateLocation(Location location) {

        Log.i("LocationInfo", location.toString());


        Log.i("Lattitude", Double.toString(location.getLatitude()));
        Log.i("Lattitude", Double.toString(location.getLongitude()));


        try {
            String addressLocality = "";
            String addressAdmin = "";
            String addressCountry = "";


            Geocoder geocoder;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            try {

                if (addressList != null && addressList.size() > 0) {


                    String address = "Could not find address!!";
                    if (addressList.get(0).getLocality() != null) {
                        addressLocality += addressList.get(0).getLocality();


                    }
                    if (addressList.get(0).getAdminArea() != null) {
                        addressAdmin += addressList.get(0).getAdminArea();


                    }
                    if (addressList.get(0).getCountryName() != null) {
                        addressCountry += addressList.get(0).getCountryName();

                    }

                    zero.setText("Lattitude : " + Double.toString(location.getLatitude()));
                    second.setText("Longitude : " + Double.toString(location.getLongitude()));
                    fourth.setText("Altitude : " + Double.toString(location.getAltitude()));
                    sixth.setText("Locality : " + addressLocality);
                    eighth.setText("Admin : " + addressAdmin);
                    tenth.setText("Country : " + addressCountry);


                }else {

                    String address = "Could not find address!!";
                    zero.setText("Lattitude : " + Double.toString(location.getLatitude()));
                    second.setText("Longitude : " + Double.toString(location.getLongitude()));
                    fourth.setText("Altitude : " + Double.toString(location.getAltitude()));
                    sixth.setText("Locality : " + address);
                    eighth.setText("Admin : " + address);
                    tenth.setText("Country : " + address);

                }




            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}


