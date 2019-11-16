package com.example.telemedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.telemedicine.Constants.*;

public class DoctorListFragment extends Fragment {

  private LinearLayoutManager mLayoutManager;
  private RecyclerView mRecyclerView;
  private List<DoctorInfo> mDoctorInfoList = new ArrayList<>();
  private RecyclerAdapter mAdapter;
  private String mToken;
  private HttpRequestManager mRequestManager;
  private ProgressBar mProgressBar;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mProgressBar = view.findViewById(R.id.progress_indicator);
    mRecyclerView = view.findViewById(R.id.doctor_list);
    mLayoutManager = new LinearLayoutManager(view.getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);

    SharedPreferences sp = getActivity().getSharedPreferences(TOKEN_SHARED_PREFS, Context.MODE_PRIVATE);
    mToken = sp.getString(TOKEN_KEY, "");
//    Toast.makeText(getActivity(), mToken, Toast.LENGTH_LONG).show();
    performGetDoctorListRequest();
  }

  private void performGetDoctorListRequest() {
    mRequestManager = new HttpRequestManager();
    mRequestManager.getDoctorList(getActivity(), mToken);
    mRequestManager.setOnDoctorListLoadedListener((response) ->
      new Parser().execute(response));
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mDoctorInfoList.size() != 0){
      DataManager.getInstance().clearList();
      mDoctorInfoList.clear();
      DataManager.getInstance().createDoctorInfos();
      mDoctorInfoList = DataManager.getInstance().getMDoctorInfo();
      mAdapter.notifyDataSetChanged();
    }
    mRecyclerView.setAdapter(mAdapter);
  }

  class Parser extends AsyncTask<String, Void, Void>{

    @Override
    protected Void doInBackground(String... strings) {
      String response = strings[0];
      if (!response.equals("") && response!= null){
        try {
          JSONArray jsonArray = new JSONArray(response);
          for (int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            int id = obj.getInt("DocId");
            String fullName = obj.getString("FullName");
            String specialty = obj.getString("Specs");
            String address = obj.getString("Address");
            String about = obj.getString("About");
            double rating = obj.getDouble("Stars");
            String photo = obj.getString("Photo");
            mDoctorInfoList.add(new DoctorInfo(id, fullName, specialty, address, about, (float) rating, photo));
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      return null;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      mProgressBar.setVisibility(View.INVISIBLE);
      mAdapter = new RecyclerAdapter(getActivity(), mDoctorInfoList);
      mRecyclerView.setAdapter(mAdapter);
    }
  }
}

