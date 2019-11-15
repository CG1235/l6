package com.example.telemedicine;

import org.jetbrains.annotations.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorInfo {

  private int mId;
  private String mName;
  private String mSpecialty;
  private String mAddress;
  private String mPhoto;
  private String mAbout;
  private int mPhotoUrl;
  private float mRating;

  public DoctorInfo(){
    mAbout = null;
    mPhoto = null;
    mId = 0;
    mName = null;
    mSpecialty = null;
    mAddress = null;
    mPhotoUrl = 0;
    mRating = 0;
  }
  public DoctorInfo(String name, String specialty,
                    String address, int photoUrl, float rating){

    mAbout = null;
    mId = 0;
    mPhoto = null;
    mName = name;
    mSpecialty = specialty;
    mAddress = address;
    mPhotoUrl = photoUrl;
    mRating = rating;
  }
  public DoctorInfo(int id, String name, String specialty,
                    String address, String about, float rating, String photo){

    mId = id;
    mName = name;
    mSpecialty = specialty;
    mAddress = address;
    mAbout = about;
    mPhoto = photo;
    mRating = rating;
  }
}
