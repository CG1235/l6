package com.example.telemedicine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.ByteArrayOutputStream;

import static com.example.telemedicine.Constants.*;

public class DoctorInfoFragment extends Fragment implements OnMapReadyCallback {

  private MapView   mMapView;
  private TextView  mNameTv;
  private TextView  mSpecialtyTv;
  private TextView  mAddressTv;
  private TextView  mRatingNumberTv;
  private RatingBar mRatingBar;
  private ImageView mPhoto;
  private String    mDoctorName;
  private String    mDoctorSpecialty;
  private String    mDoctorAddress;
  private float     mDoctorRating;
  private int       mPhotoUrl;
  private ImageView mBackArrow;
  private String mAbout;
  private Bitmap mPhotoBitmap;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_doctor_info, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mBackArrow = view.findViewById(R.id.info_back_arrow_image_view);

    mNameTv = view.findViewById(R.id.doctor_info_name);
    mSpecialtyTv = view.findViewById(R.id.doctor_info_specialty);
    mAddressTv = view.findViewById(R.id.doctor_info_location_street);
    mRatingNumberTv = view.findViewById(R.id.doctor_info_rating_number);
    mPhoto = view.findViewById(R.id.doctor_info_photo);
    mRatingBar = view.findViewById(R.id.doctor_info_rating_bar);

    initializeData();
    setData();
    initMap(savedInstanceState, view);

    mBackArrow.setOnClickListener(view1 -> getActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, new DoctorListFragment())
            .addToBackStack(null)
            .commit());
  }

  private void initializeData() {
    mDoctorName = getArguments().getString(DOCTOR_NAME);
    mDoctorSpecialty = getArguments().getString(DOCTOR_SPECIALTY);
    mDoctorAddress = getArguments().getString(DOCTOR_ADDRESS);
    mPhotoUrl = getArguments().getInt(DOCTOR_PHOTO_URL);
    mDoctorRating = getArguments().getFloat(DOCTOR_RATING);
    mAbout = getArguments().getString(DOCTOR_ABOUT);
    String base64photo = getArguments().getString(DOCTOR_BASE_64_PHOTO);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] byteArray = outputStream.toByteArray();
    byteArray = Base64.decode(base64photo, Base64.DEFAULT);
    mPhotoBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
  }

  private void setData() {
//    Drawable image = getResources().getDrawable(mPhotoUrl);
//    mPhoto.setImageDrawable(image);
    mPhoto.setImageBitmap(mPhotoBitmap);
    mNameTv.setText(mDoctorName);
    mSpecialtyTv.setText(mDoctorSpecialty);
    mAddressTv.setText(mDoctorAddress);
    mRatingBar.setRating(mDoctorRating);
    mRatingNumberTv.setText(String.valueOf(mDoctorRating));
  }

  private void initMap(Bundle savedInstanceState, View view){
    Bundle mapViewBundle = null;
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
    }
    mMapView = (MapView) view.findViewById(R.id.mapview);
    mMapView.onCreate(mapViewBundle);

    mMapView.getMapAsync(this);
  }


  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
    if (mapViewBundle == null) {
      mapViewBundle = new Bundle();
      outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
    }

    mMapView.onSaveInstanceState(mapViewBundle);
  }

  @Override
  public void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    mMapView.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mMapView.onStop();
  }

  @Override
  public void onMapReady(GoogleMap map) {
    map.setMapType(map.MAP_TYPE_SATELLITE);
    map.addMarker(new MarkerOptions().position(new LatLng(43.653656, -79.378640)).title("Marker"));
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.653656, -79.378640), 15.0f));
  }

  @Override
  public void onPause() {
    mMapView.onPause();
    super.onPause();
  }

  @Override
  public void onDestroy() {
    mMapView.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mMapView.onLowMemory();
  }
}

