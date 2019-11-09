package com.example.telemedicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

  private LinearLayoutManager mLayoutManager;
  private RecyclerView mRecyclerView;
  private List<DoctorInfo> mDoctorInfoList = new ArrayList<>();
  private RecyclerAdapter mAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = view.findViewById(R.id.doctor_list);
    mLayoutManager = new LinearLayoutManager(view.getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);
    DataManager.getInstance().createDoctorInfos();
    mDoctorInfoList = DataManager.getInstance().getMDoctorInfo();
    mAdapter = new RecyclerAdapter(getActivity(), mDoctorInfoList);
    mRecyclerView.setAdapter(mAdapter);
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
}

