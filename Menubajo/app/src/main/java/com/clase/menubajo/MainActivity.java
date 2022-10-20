package com.clase.menubajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.clase.menubajo.fragments.HomeFragment;
import com.clase.menubajo.fragments.ProfileFragment;
import com.clase.menubajo.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSelectedFragment(new SettingsFragment());

        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottonNavigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuIem) {

                if (menuIem.getItemId()== R.id.menu_home){
                    showSelectedFragment(new HomeFragment());

                }
                if (menuIem.getItemId()== R.id.menu_profile){
                    showSelectedFragment(new ProfileFragment());
                }
                if (menuIem.getItemId()== R.id.menu_settings){
                    showSelectedFragment(new SettingsFragment());

                }

                return true;
            }
        });

    }

    //Metodo que permite elegir el fragment

    private void  showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }



}
