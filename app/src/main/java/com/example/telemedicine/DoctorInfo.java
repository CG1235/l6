package com.example.telemedicine;

import org.jetbrains.annotations.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorInfo {

  private String mName;
  private String mSpecialty;
  private String mAddress;
  private String mPhotoUrl;
  private float mRating;

  public DoctorInfo(){};

  public DoctorInfo(String name, String specialty,
                    String address, String photoUrl, float rating){

    mName = name;
    mSpecialty = specialty;
    mAddress = address;
    mPhotoUrl = photoUrl;
    mRating = rating;
  }
}
