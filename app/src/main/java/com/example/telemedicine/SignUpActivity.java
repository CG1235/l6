package com.example.telemedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

  private ImageView mBackArrow;
  private String mActivityName;
  private Button mNextBtn;
  private EditText mPassword;
  private EditText mFullName;
  private EditText mBirthday;
  private EditText mEmail;
  private EditText mLogin;
  private EditText mPhone;
  private EditText mLocation;
  private TextWatcher mWatcher;

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
    mFullName = findViewById(R.id.full_name_input);
    mBirthday = findViewById(R.id.birthday_input);
    mEmail = findViewById(R.id.email_input);
    mLogin = findViewById(R.id.login_input);
    mPhone = findViewById(R.id.phone_input);
    mLocation = findViewById(R.id.location_input);

    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    mNextBtn = findViewById(R.id.next_button);
    mNextBtn.setEnabled(false);

    mBackArrow.setOnClickListener(view -> {
      if (mActivityName.equals("com.example.telemedicine.WelcomeActivity")){
        startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
        finish();
      }else if (mActivityName.equals("com.example.telemedicine.LoginActivity")){
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
      }
    });

    mWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int before, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String fullNameInput = mFullName.getText().toString().trim();
        String birthdayInput = mBirthday.getText().toString().trim();
        String emailInput = mEmail.getText().toString().trim();
        String loginInput = mLogin.getText().toString().trim();
        String passwordInput = mPassword.getText().toString().trim();
        String phoneInput = mPhone.getText().toString().trim();
        String locationInput = mLocation.getText().toString().trim();

        mNextBtn.setEnabled(!fullNameInput.isEmpty() && !birthdayInput.isEmpty() &&
                !emailInput.isEmpty() && !loginInput.isEmpty() && !passwordInput.isEmpty() &&
                !phoneInput.isEmpty() && !locationInput.isEmpty());
      }

      @Override
      public void afterTextChanged(Editable s) {}
    };

    mPassword.addTextChangedListener(mWatcher);
    mFullName.addTextChangedListener(mWatcher);
    mBirthday.addTextChangedListener(mWatcher);
    mEmail.addTextChangedListener(mWatcher);
    mLocation.addTextChangedListener(mWatcher);
    mPhone.addTextChangedListener(mWatcher);
    mLocation.addTextChangedListener(mWatcher);

    mNextBtn.setOnClickListener(view -> {
//      if (fieldsNotEmpty()){
        if (mEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")){
          if (mFullName.getText().length() > 4){
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finishAffinity();
          } else{
            alarm("Too short name");
            mFullName.setSelection(0);
          }
        } else{
          alarm("Wrong email input");
          mEmail.setSelection(0);
        }
//      } else{
//        alarm("Complete all fields!");
//      }
    });
  }

  private void alarm(String str) {
    Toast.makeText(SignUpActivity.this, str, Toast.LENGTH_LONG).show();
  }

  private boolean fieldsNotEmpty() {
    return (mFullName.getText().length() > 0 && mBirthday.getText().length() > 0 &&
            mEmail.getText().length() > 0 && mLogin.getText().length() > 0 &&
            mPassword.getText().length() > 0 && mPhone.getText().length() > 0 &&
            mLocation.getText().length() > 0);
  }
}
