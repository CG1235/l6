package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GoogleApiAvailability;

public class WelcomeActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 1488;
  private Button mSignUpButton;
  private Button mLoginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    mSignUpButton = findViewById(R.id.sign_up_button);
    mLoginButton = findViewById(R.id.login_button);
    mSignUpButton.setOnClickListener(view -> {
      startActivityForResult(new Intent(getApplicationContext(), SignUpActivity.class), REQUEST_CODE);
//        finishAffinity();
    });
    mLoginButton.setOnClickListener(view -> {
      startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        finishAffinity();
    });

  }
}
