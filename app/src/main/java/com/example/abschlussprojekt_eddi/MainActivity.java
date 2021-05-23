package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abschlussprojekt_eddi.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    Intent intentStuhl;
    Intent intentEssen;
    Intent intentEinstellungen;
    public static final String INPUT_STRING = "InputString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent intent = new Intent(this, Anmeldung.class);
        startActivity(intent);*/

        SectionsPagerAdapter sectionsPagerAdapter = new
                SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);





    }


    //beim Klicken auf den "Stuhl" Button gelangt man zur Activity "Eintrag_Stuhl"
    public void enterEintragStuhl(View view) {
        intentStuhl = new Intent(MainActivity.this, Eintrag_Stuhl.class);
        startActivity(intentStuhl);
    }


    public void enterEintragEssen(View view) {
        intentEssen = new Intent(MainActivity.this, Eintrag_Essen.class);
        startActivity(intentEssen);
    }

    public void enterEinstellungen(View view) {
        intentEinstellungen = new Intent(MainActivity.this, Einstellungen.class);
        startActivity(intentEinstellungen);
    }



}