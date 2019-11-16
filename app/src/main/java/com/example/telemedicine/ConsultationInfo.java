package com.example.telemedicine;

import com.android.volley.toolbox.StringRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultationInfo {
  private int mConsId;
  private String mName;
  private String mDisease;
  private String mAddress;
  private String mDescription;
  private int mDocId;
  private String mIsConfirmed;

  public ConsultationInfo(int consId, String name, String disease,
                          String address, String description,
                          int docId, String isConfirmed) {
    mConsId = consId;
    mName = name;
    mDisease = disease;
    mAddress = address;
    mDescription = description;
    mDocId = docId;
    mIsConfirmed = isConfirmed;
  }
}
