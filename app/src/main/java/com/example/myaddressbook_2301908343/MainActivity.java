package com.example.myaddressbook_2301908343;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myaddressbook_2301908343.fragments.EmployeeSearchFragment;
import com.example.myaddressbook_2301908343.fragments.MyAddressBookFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView;

        loadFragment(new MyAddressBookFragment());

        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Home");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyAddressBookFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_employee_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EmployeeSearchFragment()).addToBackStack(null).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}