package com.example.telemedicine;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DataManager {
  private static DataManager mInstance = null;
  private List<DoctorInfo> mDoctorInfo = new ArrayList<>();


  public static DataManager getInstance(){
    if (mInstance == null)
      mInstance = new DataManager();
    return mInstance;
  }

  public void createDoctorInfos(){
    if (mDoctorInfo == null)
      mDoctorInfo = new ArrayList<>();

    String name = "Oleg Olegovich";
    String specialty = "Neurolog";
    String address = "Backer St. 456";
    double rating = 4.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_cat_120, (float) rating));

    name = "Alexey Olegovich";
    specialty = "Neurolog";
    address = "Backer St. 456";
    rating = 4.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_dog_120, (float) rating));

    name = "Ivan Ivanov";
    specialty = "Chirurg";
    address = "St. Ismail 98";
    rating = 3.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_panda_120, (float) rating));

    name = "Andrey Ivanov-Andreev";
    specialty = "Chirurg";
    address = "St. Columna 191";
    rating = 2.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_penguin_120, (float) rating));

    name = "Vlada Izotova";
    specialty = "Chirurg";
    address = "St. Ismail 34";
    rating = 1.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_pig_120, (float) rating));

    name = "Grisha Egorov";
    specialty = "Chirurg";
    address = "St. Ismail 1";
    rating = 5.0;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_rabbit_120, (float) rating));

    name = "Igor Guzun";
    specialty = "Stomatolog";
    address = "St. Ion Creanga 98";
    rating = 3.8;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_rhino_120, (float) rating));

    name = "Pavel Nikulishin";
    specialty = "Ortoped";
    address = "St. Ismail 100";
    rating = 3.5;
    mDoctorInfo.add(new DoctorInfo(name, specialty, address, R.drawable.ic_avatar_cat_120, (float) rating));
  }

  public void clearList(){
    mDoctorInfo.clear();
  }
}
