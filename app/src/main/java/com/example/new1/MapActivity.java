package com.example.new1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    private FusedLocationProviderClient providerClient;
    private Location location;
    private Address address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SearchView searchView = findViewById(R.id.search_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        providerClient = LocationServices.getFusedLocationProviderClient(this);
        askPermissions();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                MapActivity.this.googleMap = googleMap;
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                markLocation(location);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!s.isEmpty()) {
                    Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                    try {
                        Address address = geocoder.getFromLocationName(s, 1).get(0);
                        MapActivity.this.address = address;
                        location = new Location("");
                        location.setLatitude(address.getLatitude());
                        location.setLongitude(address.getLongitude());
                        markLocation(location);
                    } catch (Exception e) {

                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        Button saveBT = findViewById(R.id.save);
        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void askPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
         != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            findMyLocation();
        }
    }

    private void findMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    MapActivity.this.location = location;
                    markLocation(location);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findMyLocation();
            }
        }
    }

    private void markLocation(Location location) {
        if (googleMap != null && location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Address address = geocoder.getFromLocation(latitude, longitude, 1).get(0);
                MapActivity.this.address = address;
                Toast.makeText( MapActivity.this, address.getAddressLine(0), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }


        }
    }
}