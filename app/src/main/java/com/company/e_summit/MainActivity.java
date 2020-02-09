package com.company.e_summit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    public FragmentTransaction fragmentTransaction;
    private FirebaseAuth firebaseAuth;
    public static String currSummit, summitTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        summitTitle = intent.getStringExtra("Summit");
        currSummit = summitTitle.toLowerCase();

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(summitTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, new SummitMain());
        fragmentTransaction.commit();

        navigationView = findViewById(R.id.main_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                if (menuItem.getItemId() == R.id.nav_sponsors) {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new SponsorsMain()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
//                else if (menuItem.getItemId() == R.id.nav_events) {
//                    manager.beginTransaction().replace(R.id.main_frame_layout, new EventsMain()).commit();
//                    menuItem.setChecked(true);
//                    toolbar.setTitle(menuItem.getTitle());
//                    drawerLayout.closeDrawers();
//                    return true;
//                }
                else if (menuItem.getItemId() == R.id.nav_agenda) {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new AgendaMain()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_announcements) {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new AnnouncementsActivity()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_feedback) {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new FeedbackActivity()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_faq) {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new FaqMain()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_logout) {
                    firebaseAuth.signOut();
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivty.class);
                    startActivity(loginIntent);
                    return true;
                }
                else {
                    manager.beginTransaction().replace(R.id.main_frame_layout, new SummitMain()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(summitTitle);
                    drawerLayout.closeDrawers();
                    return true;
                }
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
