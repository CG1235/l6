package com.example.telemedicine;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NotificationFragment extends Fragment {

  private RatingBar mRatingBar;
  private TextView mIndicator;
  private ImageView mDoctorPhoto;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRatingBar = view.findViewById(R.id.rating_bar);
    mIndicator = view.findViewById(R.id.rating_indicator);
    mDoctorPhoto = view.findViewById(R.id.doctor_photo);
    mRatingBar.setRating((float) 3.5);
    mIndicator.setText(String.valueOf(mRatingBar.getRating()));
    Picasso.get()
            .load("https://i.imgur.com/DvpvklR.png")
            .noFade()
            .into(mDoctorPhoto, new Callback() {
              @Override
              public void onSuccess() {
                System.out.println("++++++++++++++++++Success+++++++++++++++++++++++");
              }

              @Override
              public void onError(Exception e) {
                System.out.println("==============================" + e.getMessage() + "    Error====================");
              }
            });
  }
}

