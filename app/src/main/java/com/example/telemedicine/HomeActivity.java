package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity{

  private BottomNavigationView mNavigationView;
  private FloatingActionButton mAddReqFab;
  private Menu mMenu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    mNavigationView = findViewById(R.id.nav_bar);

    disableMenuItems();
    openHomeFragment();

    mAddReqFab = findViewById(R.id.add_req_fab);
    Drawable fabTint = getResources().getDrawable(R.drawable.ic_add_black_24dp);
    Drawable willBeWhite = fabTint.getConstantState().newDrawable();
    willBeWhite.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
    mAddReqFab.setImageDrawable(willBeWhite);

    mNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
//      Fragment fragment = null;
      switch (menuItem.getItemId()){
        case R.id.nav_home:
//          fragment = new DoctorListFragment();
            DoctorListFragment doctorListFragment = new DoctorListFragment();
            getSupportFragmentManager()
                  .beginTransaction()
                  .replace(R.id.fragment_container, doctorListFragment)
                  .addToBackStack(null)
                  .commit();
          break;
        case R.id.nav_notification:
//          fragment = new NotificationFragment();
//          mNavigationView.removeBadge(R.id.nav_notification);
            NotificationFragment notificationFragment = new NotificationFragment();
            getSupportFragmentManager()
                  .beginTransaction()
                  .replace(R.id.fragment_container, notificationFragment)
                  .addToBackStack(null)
                  .commit();
            notificationFragment.setOnViewCreatedListener(() ->
                    mNavigationView.removeBadge(R.id.nav_notification));
          break;
      }
//      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
      return true;
    });
    mAddReqFab.setOnClickListener(view -> {
      AddFragment fragment = new AddFragment();
      getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
      fragment.setOnRequestClickedListener(() -> {
//                MenuItem notification = mMenu.findItem(R.id.nav_notification);
//                notification.setEnabled(true);
                mNavigationView.getOrCreateBadge(R.id.nav_notification).setNumber(1);}
              );
    }
    );
  }

  private void disableMenuItems() {
    mMenu = mNavigationView.getMenu();
    MenuItem schedule = mMenu.findItem(R.id.nav_schedule);
    schedule.setEnabled(false);
    MenuItem profile = mMenu.findItem(R.id.nav_profile);
    profile.setEnabled(false);
//    MenuItem notification = mMenu.findItem(R.id.nav_notification);
//    notification.setEnabled(false);
  }

  private void openHomeFragment() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DoctorListFragment()).commit();
  }

  @Override
  public void onBackPressed() {
//    super.onBackPressed();
    finish();
  }
}
