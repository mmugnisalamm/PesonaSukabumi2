package com.mug.pesona_sukabumi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mug.pesona_sukabumi.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailTempat extends AppCompatActivity implements OnMapReadyCallback{

    View view;

    private GoogleMap mMap;

    String googleMap2;

    private TextView tvNama, deskripsi;
    private ImageView imageView2;
    private Double lat1, lon1;

    Uri gmmIntentUri;
    Intent mapIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tempat);

        ActionBar menu = getSupportActionBar();

        menu.setDisplayShowHomeEnabled(true);

        menu.setDisplayHomeAsUpEnabled(true);

        menu.setTitle("Detail Tempat Wisata");


        deskripsi = findViewById(R.id.deskripsi2);
        tvNama =  findViewById(R.id.tvNama);
        imageView2 = findViewById(R.id.imageView2);


        //reviece
        Intent intent = getIntent();
        String deskripsi2 = intent.getStringExtra("deskripsi");
        String image = intent.getStringExtra("gambar");
        String lat = intent.getStringExtra("latitude");
        String lon = intent.getStringExtra("longitude");
        String nama = intent.getStringExtra("nama");

        Glide.with(DetailTempat.this).load(image).into(imageView2);
        tvNama.setText(nama);
        deskripsi.setText(deskripsi2);

        //deklarasi konversi string ke double
        lat1 = Double.valueOf(lat).doubleValue();
        lon1 = Double.valueOf(lon).doubleValue();

        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap2 = "com.google.android.apps.maps";
        mMap = googleMap;
        mMap.setPadding(10, 180, 10, 10);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(true);

            }
        }

        LatLng sydney = new LatLng(lat1, lon1);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Lokasi Wisata"));

        /*LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
        latLongBuilder.include(sydney);
        //latLongBuilder.include(locationLatLng);

        // Bounds Coordinata
        LatLngBounds bounds = latLongBuilder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int paddingMap = (int) (width * 0.2); //jarak dari
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
        mMap.animateCamera(cu);
        */
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        /** END
         * Logic untuk membuat layar berada ditengah2 dua koordinat
         */
        mMap.setPadding(10, 180, 10, 180);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
                gmmIntentUri = Uri.parse("google.navigation:q=" + String.valueOf(lat1) + "," +String.valueOf(lon1));

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(googleMap2);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(DetailTempat.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
