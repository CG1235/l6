package com.example.telemedicine.Interfaces;

import com.example.telemedicine.DoctorInfo;

import java.util.ArrayList;

public interface OnDoctorListLoadedListener {
  void onDoctorListLoaded(ArrayList<DoctorInfo> doctorInfoArrayList);
}
