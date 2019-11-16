package com.example.telemedicine.Interfaces;

import com.android.volley.VolleyError;

public interface OnConsultationFailureListener {
  void onConsultationFailure(VolleyError error);
}
