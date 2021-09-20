package com.beserrovsky.rgpotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.beserrovsky.rgpotter.ui.curiosity.CuriosityFragment;
import com.beserrovsky.rgpotter.ui.rg.RgFragment;
import com.beserrovsky.rgpotter.ui.spells.SpellFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navigationView;
    private boolean viewIsAtHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        // Thanks Guilherme Palma, https://github.com/GuilhermePalma <3
        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                displayView(item.getItemId());
                return true;
            }
        });

        displayView(R.id.nav_rg);
    }

    // HUGE Thanks to Ojonugwa Jude Ochalifu on https://stackoverflow.com/questions/32944798/switch-between-fragments-with-onnavigationitemselected-in-new-navigation-drawer
    @SuppressLint("NonConstantResourceId")
    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        // TODO: SWITCH
        if (viewId == R.id.nav_rg) {
            fragment = new RgFragment();
            title  = getString(R.string.rg_name);
            viewIsAtHome = true;
        }
        if (viewId == R.id.nav_spells) {
            fragment = new SpellFragment();
            title = getString(R.string.spell_name);
            viewIsAtHome = false;
        }
        if (viewId == R.id.nav_curiosity) {
            fragment = new CuriosityFragment();
            title = getString(R.string.curiosity_name);
            viewIsAtHome = false;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (!viewIsAtHome) { //if the current view is not the RG fragment
            displayView(R.id.nav_rg); //display the RG fragment
        } else {
            moveTaskToBack(true);  //If view is in RG fragment, exit application
        }
    }

}