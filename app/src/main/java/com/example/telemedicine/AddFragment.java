package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.OnRequestClickedListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.telemedicine.Constants.*;

public class AddFragment extends Fragment {


  private OnRequestClickedListener onRequestClickedListener;
  private Button mRequset;
  private EditText mNameEdTxt;
  private EditText mDiseaseEdTxt;
  private EditText mLocationEdTxt;
  private EditText mDescriptionEdTxt;
  private HttpRequestManager mRequestManager;
  private String mToken;
  private DoctorInfo mDoctorInfo;

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

    mRequestManager = new HttpRequestManager();
    SharedPreferences tokenSp = getActivity().getSharedPreferences(TOKEN_SHARED_PREFS, Context.MODE_PRIVATE);
    mToken = tokenSp.getString(TOKEN_KEY, "");


    mRequset = view.findViewById(R.id.add_fragment_request_button);

    mRequset.setOnClickListener(view1 -> {
      mRequestManager.getDoctorById(getActivity(), 5, mToken);
      mRequestManager.setOnDoctorGetListener(response -> {
        new DoctorInfoParser().execute(response);
      });
      mRequestManager.setOnGetDoctorFailureListener(error -> {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
      });
    });
  }

  void setOnRequestClickedListener(OnRequestClickedListener listener){
    this.onRequestClickedListener = listener;
  }

  class DoctorInfoParser extends AsyncTask<String, Void, DoctorInfo>{

    @Override
    protected DoctorInfo doInBackground(String... strings) {
      String response = strings[0];
      DoctorInfo info = null;
      try {
        JSONObject obj = new JSONObject(response);
        int docId = obj.getInt("DocId");
        String fullName = obj.getString("FullName");
        String specialty = obj.getString("Specs");
        String address = obj.getString("Address");
        String about = obj.getString("About");
        double rating = obj.getDouble("Stars");
        String photo = obj.getString("Photo");

        info = new DoctorInfo(docId, fullName, specialty, address,
                about, (float) rating, photo);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return info;
    }

    @Override
    protected void onPostExecute(DoctorInfo doctorInfo) {
      super.onPostExecute(doctorInfo);

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
      editor.putString(DOCTOR_NAME, doctorInfo.getMName());
      editor.putString(DOCTOR_SPECIALTY, doctorInfo.getMSpecialty());
      editor.putString(DOCTOR_BASE_64_PHOTO, doctorInfo.getMPhoto());
      editor.putFloat(DOCTOR_RATING, doctorInfo.getMRating());
      editor.apply();

      if (onRequestClickedListener != null)
        onRequestClickedListener.onRequest();
    }
  }
}

