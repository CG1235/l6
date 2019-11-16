package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.OnViewCreatedListener;

import java.io.ByteArrayOutputStream;

import static com.example.telemedicine.Constants.*;

public class NotificationFragment extends Fragment {

  private RatingBar mRatingBar;
  private TextView mIndicator;
  private ImageView mDoctorPhoto;
  private OnViewCreatedListener onViewCreatedListener;
  private TextView mName;
  private TextView mDisease;
  private TextView mLocation;
  private TextView mDescription;
  private TextView mDoctorSpecialtyTv;
  private TextView mDoctorName;
  private CardView mDoctorInfoCv;
  private Bundle mArgs;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (onViewCreatedListener != null)
      onViewCreatedListener.onViewCreated();

    mName = view.findViewById(R.id.user_req_name);
    mDisease = view.findViewById(R.id.user_disease);
    mLocation = view.findViewById(R.id.user_location);
    mDescription = view.findViewById(R.id.user_description);
    mDoctorInfoCv = view.findViewById(R.id.notif_card_view);

    mDoctorName = view.findViewById(R.id.notif_doctor_info_name);
    mDoctorSpecialtyTv = view.findViewById(R.id.notif_doctor_info_specialty);
    mRatingBar = view.findViewById(R.id.notif_doctor_info_rating_bar);
    mIndicator = view.findViewById(R.id.notif_doctor_info_rating_number);
    mDoctorPhoto = view.findViewById(R.id.notif_doctor_info_photo);

    fillTextViews();

    mDoctorInfoCv.setOnClickListener(view1 -> {
      DoctorInfoFragment fragment = new DoctorInfoFragment();
      fragment.setArguments(mArgs);

      getActivity().getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment_container, fragment)
              .addToBackStack(null)
              .commit();
    });
  }

  private void fillTextViews() {
    SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

    String name = sp.getString(PATIENT_NAME, "");
    String disease = sp.getString(PATIENT_DISEASE, "");
    String location = sp.getString(PATIENT_LOCATION, "");
    String description = sp.getString(PATIENT_DESCRIPTION, "");
    String doctorName = sp.getString(DOCTOR_NAME, "");
    String doctorSpecialty = sp.getString(DOCTOR_SPECIALTY, "");
    String doctorAddress = sp.getString(DOCTOR_ADDRESS, "");
    String doctorAbout = sp.getString(DOCTOR_ABOUT, "");
    float doctorRating = sp.getFloat(DOCTOR_RATING, 1);
    String base64photo = sp.getString(DOCTOR_BASE_64_PHOTO, "");

    setArgs(doctorName, doctorSpecialty, doctorAddress, doctorAbout,
            doctorRating, base64photo);

    mName.setText(name);
    mDisease.setText(disease);
    mLocation.setText(location);
    mDescription.setText(description);
    mDoctorName.setText(doctorName);
    mDoctorSpecialtyTv.setText(doctorSpecialty);
    mRatingBar.setRating(doctorRating);
    mIndicator.setText(String.valueOf(mRatingBar.getRating()));

    if (!base64photo.equals("")){
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] byteArray = outputStream.toByteArray();
      byteArray = Base64.decode(base64photo, Base64.DEFAULT);
      Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
      mDoctorPhoto.setImageBitmap(image);
    }
  }

  private void setArgs(String doctorName, String doctorSpecialty, String doctorAddress,
                       String doctorAbout, float doctorRating, String base64photo) {
    mArgs = new Bundle();
      mArgs.putString(DOCTOR_NAME, doctorName);
      mArgs.putString(DOCTOR_SPECIALTY,doctorSpecialty );
      mArgs.putString(DOCTOR_ADDRESS, doctorAddress );
      mArgs.putFloat(DOCTOR_RATING, doctorRating);
      mArgs.putString(DOCTOR_ABOUT, doctorAbout);
      mArgs.putString(DOCTOR_BASE_64_PHOTO, base64photo);
  }

  public void setOnViewCreatedListener(OnViewCreatedListener listener){
    this.onViewCreatedListener = listener;
  }
}

