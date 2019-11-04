package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 8841;
  private Button mSignUpButton;
  private Button mLoginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mSignUpButton = findViewById(R.id.login_sign_up_button);
    mLoginButton = findViewById(R.id.login_login_button);
    mSignUpButton.setOnClickListener(view -> startActivityForResult(new Intent(LoginActivity.this, SignUpActivity.class), REQUEST_CODE));
    mLoginButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));
  }
}
