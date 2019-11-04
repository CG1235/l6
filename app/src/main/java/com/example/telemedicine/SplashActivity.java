package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

  private static boolean mIsSplashLoaded = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!mIsSplashLoaded){
      setContentView(R.layout.activity_splash);
      new Handler().postDelayed(() -> {
        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        finish();
      }, 2000);
      mIsSplashLoaded = true;
    }else{
       Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
       startActivity(intent);
       finishAffinity();
    }
  }
}
