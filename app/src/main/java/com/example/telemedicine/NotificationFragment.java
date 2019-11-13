package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

public class NotificationFragment extends Fragment {

  public static final String FRAGMENT_ADD_NAME = "com.example.telemedicine.Name";
  public static final String FRAGMENT_ADD_DISEASE = "com.example.telemedicine.DISEASE";
  public static final String FRAGMENT_ADD_LOCATION = "com.example.telemedicine.LOCATION";
  public static final String FRAGMENT_ADD_DESCRIPTION = "com.example.telemedicine.DESCRIPTION";

  private RatingBar mRatingBar;
  private TextView mIndicator;
  private ImageView mDoctorPhoto;
  private OnViewCreatedListener onViewCreatedListener;
  private TextView mName;
  private TextView mDisease;
  private TextView mLocation;
  private TextView mDescription;

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

    fillTextViews();

    mRatingBar = view.findViewById(R.id.doctor_info_rating_bar);
    mIndicator = view.findViewById(R.id.doctor_info_rating_number);
    mDoctorPhoto = view.findViewById(R.id.doctor_info_photo);
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

  private void fillTextViews() {
    SharedPreferences sp = getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

    String name = sp.getString("NAME", "");
    String disease = sp.getString("DISEASE", "");
    String location = sp.getString("LOCATION", "");
    String description = sp.getString("DESCRIPTION", "");

    mName.setText(name);
    mDisease.setText(disease);
    mLocation.setText(location);
    mDescription.setText(description);
  }

  public void setOnViewCreatedListener(OnViewCreatedListener listener){
    this.onViewCreatedListener = listener;
  }

  public interface OnViewCreatedListener{
    void onViewCreated();
  }
}

