package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

  private BottomNavigationView mNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    mNavigationView = findViewById(R.id.nav_bar);
    openHomeFragment();
    mNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
      Fragment fragment = null;
      switch (menuItem.getItemId()){
        case R.id.nav_home:
          fragment = new HomeFragment();
          break;
        case R.id.nav_notification:
          fragment = new NotificationFragment();
          break;
      }
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
      return true;
    });
  }

  private void openHomeFragment() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
  }
}
