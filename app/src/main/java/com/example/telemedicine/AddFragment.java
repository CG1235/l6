package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Random;

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
    mRequset = view.findViewById(R.id.add_fragment_request_button);
    mRequset.setEnabled(false);

    mRequestManager = new HttpRequestManager();
    SharedPreferences tokenSp = getActivity().getSharedPreferences(TOKEN_SHARED_PREFS, Context.MODE_PRIVATE);
    mToken = tokenSp.getString(TOKEN_KEY, "");

    TextWatcher watcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String name = mNameEdTxt.getText().toString().trim();
        String disease = mDiseaseEdTxt.getText().toString().trim();
        String address = mLocationEdTxt.getText().toString().trim();
//        String description = mDescriptionEdTxt.getText().toString().trim();
        mRequset.setEnabled(!name.isEmpty() && !disease.isEmpty() &&
                !address.isEmpty());
      }

      @Override
      public void afterTextChanged(Editable editable) {}
    };

    mNameEdTxt.addTextChangedListener(watcher);
    mDiseaseEdTxt.addTextChangedListener(watcher);
    mLocationEdTxt.addTextChangedListener(watcher);
//    mDescriptionEdTxt.addTextChangedListener(watcher);

    mRequset.setOnClickListener(view1 -> {

      RequestDialog confirmDialog = new RequestDialog();
      confirmDialog.show(getActivity().getSupportFragmentManager(), "Dialog");

      confirmDialog.setOnConfirmClickedListener(() -> {
        String dis = mDiseaseEdTxt.getText().toString();
        dis.replaceAll("\\s+", "");
        ConsultationInfo info = new ConsultationInfo(1, mNameEdTxt.getText().toString(),
                dis + " " + genKeyWord(),
                mLocationEdTxt.getText().toString(), mDescriptionEdTxt.getText().toString(),
                5, "false");
        mRequestManager.addCons(getActivity(), info, mToken);
        mRequestManager.setOnConsultationAddedListener(response -> {
          new ConsResponseParser().execute(response);
          System.out.println("RESPONSE = " + response);
        });
        mRequestManager.setOnConsultationFailureListener(error -> {
          Log.d("ERROR", error.getMessage());
        });
      });
      confirmDialog.setOnCancelClickedListener(() -> {
        Toast.makeText(getActivity(), "Request canceled", Toast.LENGTH_LONG).show();
      });
    });
  }

  class ConsResponseParser extends AsyncTask<String, Void, Integer>{
    @Override
    protected Integer doInBackground(String... strings) {
      String response = strings[0];
      int docId = 0;
      try {
        JSONObject obj = new JSONObject(response);
        docId = obj.getInt("DocId");
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return docId;
    }

    @Override
    protected void onPostExecute(Integer id) {
      super.onPostExecute(id);
      mRequestManager.getDoctorById(getActivity(), id, mToken);
      mRequestManager.setOnDoctorGetListener(response -> {
        new DoctorInfoParser().execute(response);
      });
      mRequestManager.setOnGetDoctorFailureListener(error -> {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
      });
    }
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
      editor.putString(DOCTOR_ADDRESS, doctorInfo.getMAddress());
      editor.putString(DOCTOR_ABOUT, doctorInfo.getMAbout());
      editor.apply();

      if (onRequestClickedListener != null)
        onRequestClickedListener.onRequest();
    }
  }

  private String genKeyWord(){
    ArrayList<String> keyWords = new ArrayList<String>() {{
      add("ochi"); add("ochiul"); add("ochelari"); add("ochii"); add("vederea");
      add("baiatul"); add("fetita"); add("picior"); add("mina"); add("interventie");
      add("chirurgical"); add("fractura"); add("fracturat"); add("dislocat"); add("picior");
    }};
    Random r = new Random();
    int low = 0;
    int high = keyWords.size();
    int index = r.nextInt(high - low) + low;

    if (index >= keyWords.size())
      index = 0;

    return keyWords.get(index);
  }

  void setOnRequestClickedListener(OnRequestClickedListener listener){
    this.onRequestClickedListener = listener;
  }
}
