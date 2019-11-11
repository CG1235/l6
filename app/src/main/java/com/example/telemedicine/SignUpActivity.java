package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

  private ImageView mBackArrow;
  private String mActivityName;
  private EditText mPassword;

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mBackArrow = findViewById(R.id.back_arrow_image_view);
    mActivityName = getCallingActivity().getClassName();
    mPassword = findViewById(R.id.password_input);
    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

    mBackArrow.setOnClickListener(view -> {
      if (mActivityName.equals("com.example.telemedicine.WelcomeActivity")){
        startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
        finish();
      }else if (mActivityName.equals("com.example.telemedicine.LoginActivity")){
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
      }
    });
  }
}
