package com.example.katherine.mapa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<MarkerOptions> markerList;
    private PolylineOptions polylineOptions;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        markerList = new ArrayList<>();
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.CYAN);

    }

    @Override
    public void onMapReady(GoogleMap googleMap)

    {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMap = googleMap;


        // Add a marker in Sydney and move the camera

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                50,
                new LocationListener()

                {
                    @Override
                    public void onLocationChanged(Location location) {

                        LatLng latlng
                                = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        MarkerOptions markerOptions
                                = new MarkerOptions().position(latlng);
                                markerList.add(markerOptions);
                                polylineOptions.add(latlng);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));


                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                        Log.v("LOCATION","Status cambio");

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
        LatLng bogota = new LatLng(4.5, -74);
        mMap.addMarker(new MarkerOptions().position(bogota));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                markerList.add(markerOptions);
                polylineOptions.add(latLng);
                mMap.addMarker(markerOptions);
                mMap.addPolyline(polylineOptions);
            }
        });
    }
}



