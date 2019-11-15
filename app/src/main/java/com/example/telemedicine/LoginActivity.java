package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.telemedicine.Constants.REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {

  private Button mSignUpButton;
  private Button mLoginButton;
  private EditText mPassword;
  private EditText mEmail;
  private HttpRequestManager mRequestManager;
  private JSONObject mAuthResponse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mRequestManager = new HttpRequestManager();
    mEmail = findViewById(R.id.email_login_input);
    mPassword = findViewById(R.id.password_login_input);
    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    mSignUpButton = findViewById(R.id.login_sign_up_button);
    mLoginButton = findViewById(R.id.login_login_button);
    mLoginButton.setEnabled(false);

    mSignUpButton.setOnClickListener(view ->
            startActivityForResult(new Intent(LoginActivity.this, SignUpActivity.class),
                    REQUEST_CODE));

    TextWatcher watcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mLoginButton.setEnabled(!email.isEmpty() && !password.isEmpty());
      }

      @Override
      public void afterTextChanged(Editable editable) {}
    };

    mEmail.addTextChangedListener(watcher);
    mPassword.addTextChangedListener(watcher);

    mLoginButton.setOnClickListener(view -> {
      if (mEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")){
        try {
          mRequestManager.authUser(LoginActivity.this, new ArrayList<String>(){
            { add(mEmail.getText().toString());
              add(mPassword.getText().toString());}});
        } catch (JSONException e) {
          e.printStackTrace();
        }

        mRequestManager.setOnLoginSucceedListener(response -> {
          mAuthResponse = new JSONObject();
          mAuthResponse = response;
          startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        });
        mRequestManager.setOnLoginFailedListener(error ->
          Toast.makeText(
                  LoginActivity.this, "Authorization failed" + error.getMessage(),
                  Toast.LENGTH_LONG).show()
        );

      } else {
        Toast.makeText(LoginActivity.this, "Incorrect email pattern", Toast.LENGTH_LONG)
                .show();
      }
    });
  }
}
