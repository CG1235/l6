package com.example.telemedicine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.telemedicine.Constants.*;

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
  private ImageView mPhoto;
  private String mBase64String;
  private UserInfo mUserInfo;
  private HttpRequestManager mRequestManager;

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

    mRequestManager = new HttpRequestManager();
    mBackArrow = findViewById(R.id.back_arrow_image_view);
    mActivityName = getCallingActivity().getClassName();
    mPhoto = findViewById(R.id.circle_image_view);

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
        if (mEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")){
          if (mFullName.getText().length() > 4 && mLogin.getText().length() > 4 && mPassword.getText().length() > 7){
            if (mBase64String.length() > 0){
              mUserInfo = new UserInfo(mFullName.getText().toString(), mBirthday.getText().toString(),
                      mEmail.getText().toString(), mPhone.getText().toString(),
                      mLocation.getText().toString(), mLogin.getText().toString(),
                      mPassword.getText().toString(), mBase64String);
              try {
                mRequestManager.registerUser(SignUpActivity.this, mUserInfo);
                mRequestManager.setOnRegistrationFinishedListener(response -> {
                  System.out.println(response);
                  startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                  finishAffinity();
                });
                mRequestManager.setOnRegistrationFailedListener(error -> {
                  VolleyError err = error;
                  System.out.println(err.toString() + "=======================++++++++++++++++++++++++++");
                });
              } catch (JSONException e) {
                e.printStackTrace();
              }
            } else{
              alarm("Add photo!!!");
            }
          } else{
            alarm("Too short name/userName/password");
            mFullName.setSelection(0);
          }
        } else{
          alarm("Wrong email input");
          mEmail.setSelection(0);
        }
    });

    mPhoto.setOnClickListener(view -> {

      if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {

        // Should we show an explanation?
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
          // Explain to the user why we need to read the contacts
        }

        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                RESULT_LOAD_PHOTO);
      }

      startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
              RESULT_LOAD_PHOTO);
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode ==RESULT_LOAD_PHOTO && resultCode == RESULT_OK){
      Uri photoUri = data.getData();
      if (photoUri != null){
        mBase64String = convertTobase64(photoUri);
      }
      mPhoto.setImageURI(photoUri);
    }
  }

  private String convertTobase64(Uri photoUri) {
    Bitmap b = null;
    try {
      b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    b.compress(Bitmap.CompressFormat.JPEG, 75, stream);
    byte[] byteArray = stream.toByteArray();

    String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
    System.out.println("++++++++++++++ " + encoded + " ++++++++++++++++++++++++++");
    encoded.replaceAll("\\s+", "");
    return encoded;
  }

  private void alarm(String str) {
    Toast.makeText(SignUpActivity.this, str, Toast.LENGTH_LONG).show();
  }
}
