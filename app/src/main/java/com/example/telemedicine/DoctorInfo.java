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
  private int mPhotoUrl;
  private float mRating;

  public DoctorInfo(String name, String specialty,
                    String address, int photoUrl, float rating){

    mName = name;
    mSpecialty = specialty;
    mAddress = address;
    mPhotoUrl = photoUrl;
    mRating = rating;
  }
}
