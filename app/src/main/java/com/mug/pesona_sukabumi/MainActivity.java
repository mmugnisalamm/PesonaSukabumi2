package com.mug.pesona_sukabumi;


import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.mug.pesona_sukabumi.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser currentUSer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Tempat Pariwisata");
        getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new TempatPariwisataFragment()).commit();

        //init
        mAuth = FirebaseAuth.getInstance();
        currentUSer = mAuth.getCurrentUser();

        updateNavHeader();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Berita Sukabumi");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new HomeFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportActionBar().setTitle("Penunjang Pariwisata");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new GalleryFragment()).commit();
        } else if (id == R.id.nav_profile) {
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new ProfileFragment()).commit();
        } else if (id == R.id.nav_music_video) {
            getSupportActionBar().setTitle("Tentang");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new MusicVideoFragment()).commit();
        } else if (id == R.id.nav_daily) {
            getSupportActionBar().setTitle("Tempat Pariwisata");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new TempatPariwisataFragment()).commit();
        } else if (id == R.id.logout) {
            getSupportActionBar().setTitle("Logout");
            getSupportFragmentManager().beginTransaction().replace(R.id.itscontainer, new Logout()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navNama);
        TextView navMail = headerView.findViewById(R.id.navEmail);
        ImageView navImg = headerView.findViewById(R.id.imageView);

        navUsername.setText(currentUSer.getDisplayName());
        navMail.setText(currentUSer.getEmail());
        Glide.with(this).load(currentUSer.getPhotoUrl()).into(navImg);


    }
}
