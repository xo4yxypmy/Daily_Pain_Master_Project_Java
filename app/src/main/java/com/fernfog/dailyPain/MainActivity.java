package com.fernfog.dailyPain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.fernfog.dailyPain.objects.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(this);

        User user = dbHandler.getUser();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setTitle(user.getFirstName() + " " + user.getLastName());

        replaceFragment(new AddSymptomCategoryFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.add) {
                    replaceFragment(new AddSymptomCategoryFragment());
                } else if (itemId == R.id.symptom_list) {
                    replaceFragment(new SymptomListFragment());
                } else if (itemId == R.id.analis) {
                    replaceFragment(new AnalisFragment());
                } else if (itemId == R.id.report) {
                    replaceFragment(new ReportFragment());
                }

                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}