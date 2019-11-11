package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 8841;
  private Button mSignUpButton;
  private Button mLoginButton;
  private EditText mPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mPassword = findViewById(R.id.password_login_input);
    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    mSignUpButton = findViewById(R.id.login_sign_up_button);
    mLoginButton = findViewById(R.id.login_login_button);
    mSignUpButton.setOnClickListener(view -> startActivityForResult(new Intent(LoginActivity.this, SignUpActivity.class), REQUEST_CODE));
    mLoginButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));
  }
}
