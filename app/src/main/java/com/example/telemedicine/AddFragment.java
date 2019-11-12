package com.example.telemedicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddFragment extends Fragment {

  private OnRequestClickedListener onRequestClickedListener;
  private Button mRequset;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_add, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    mRequset = view.findViewById(R.id.add_fragment_request_button);
    mRequset.setOnClickListener(view1 -> {
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

