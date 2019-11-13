package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.telemedicine.Constants.*;

public class AddFragment extends Fragment {


  private OnRequestClickedListener onRequestClickedListener;
  private Button mRequset;
  private EditText mNameEdTxt;
  private EditText mDiseaseEdTxt;
  private EditText mLocationEdTxt;
  private EditText mDescriptionEdTxt;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_add, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    mNameEdTxt = view.findViewById(R.id.name_input);
    mDiseaseEdTxt = view.findViewById(R.id.disease_input);
    mLocationEdTxt = view.findViewById(R.id.location_input);
    mDescriptionEdTxt = view.findViewById(R.id.description_input);

    mRequset = view.findViewById(R.id.add_fragment_request_button);

    mRequset.setOnClickListener(view1 -> {
      String name = mNameEdTxt.getText().toString();
      String disease = mDiseaseEdTxt.getText().toString();
      String location = mLocationEdTxt.getText().toString();
      String description = mDescriptionEdTxt.getText().toString();

      SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putString(PATIENT_NAME, name);
      editor.putString(PATIENT_DISEASE, disease);
      editor.putString(PATIENT_LOCATION, location);
      editor.putString(PATIENT_DESCRIPTION, description);
      editor.apply();

      if (onRequestClickedListener != null)
        onRequestClickedListener.onRequest();
    });
  }

  void setOnRequestClickedListener(OnRequestClickedListener listener){
    this.onRequestClickedListener = listener;

  }

  public interface OnRequestClickedListener{
    void onRequest();
  }
}

