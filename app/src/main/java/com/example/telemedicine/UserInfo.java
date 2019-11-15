package com.example.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
  private String mFullName;
  private String mBirthday;
  private String mEmail;
  private String mPhone;
  private String mAddress;
  private String mUserName;
  private String mPassword;
  private String mBase64Photo;

  public UserInfo(String fullName, String birthday, String email,
                  String phone, String address, String userName,
                  String password, String base64Photo) {
    mFullName = fullName;
    mBirthday = birthday;
    mEmail = email;
    mPhone = phone;
    mAddress = address;
    mUserName = userName;
    mPassword = password;
    mBase64Photo = base64Photo;
  }
}
