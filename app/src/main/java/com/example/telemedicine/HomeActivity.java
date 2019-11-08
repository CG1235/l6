package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

  private BottomNavigationView mNavigationView;
  private FloatingActionButton mAddReqFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mNavigationView = findViewById(R.id.nav_bar);

    openHomeFragment();
    mAddReqFab = findViewById(R.id.add_req_fab);
    mNavigationView.getOrCreateBadge(R.id.nav_notification).setNumber(1);
    mNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
      Fragment fragment = null;
      switch (menuItem.getItemId()){
        case R.id.nav_home:
          fragment = new HomeFragment();
          break;
        case R.id.nav_notification:
          fragment = new NotificationFragment();
          mNavigationView.removeBadge(R.id.nav_notification);
          break;
      }
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
      return true;
    });
    mAddReqFab.setOnClickListener(view ->
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new AddFragment())
              .commit()
    );
  }

  private void openHomeFragment() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
  }
}
