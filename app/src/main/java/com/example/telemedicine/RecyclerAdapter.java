package com.example.telemedicine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

  private FragmentActivity mContext;
  private LayoutInflater mInflater;
  private List<DoctorInfo> mDoctorList;

  public RecyclerAdapter(FragmentActivity context, List<DoctorInfo> doctorList){

    mContext = context;
    mDoctorList = new ArrayList<>();
    mDoctorList = doctorList;
    mInflater = LayoutInflater.from(mContext);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_doctor_list, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    DoctorInfo info = mDoctorList.get(position);
    int dimenNameHeight = (int) mContext.getResources().getDimension(R.dimen.list_name_h);
    System.out.println("HEIGHT============================ " + dimenNameHeight);
    if (info.getMName().length() > 20){
      holder.mParams.height = holder.mNameFieldHeight * 2;
      holder.mDoctorName.setLayoutParams(holder.mParams);
    }else{
      holder.mParams.height = dimenNameHeight;
      holder.mDoctorName.setLayoutParams(holder.mParams);
    }
    holder.mIndex = position;
    holder.mDoctorName.setText(info.getMName());
    holder.mDoctorSpecialty.setText(info.getMSpecialty());
    holder.mDoctorAddress.setText(info.getMAddress());
    holder.mRatingNumber.setText(String.valueOf(info.getMRating()));
    holder.mRatingBar.setRating(info.getMRating());
    Drawable image = mContext.getResources().getDrawable(info.getMPhotoUrl());
    holder.mDoctorPhoto.setImageDrawable(image);
  }

  @Override
  public int getItemCount() {
    return mDoctorList == null ? 0 : mDoctorList.size();
  }

  @Setter
  @Getter
  protected class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView mDoctorPhoto;
    private TextView mDoctorName;
    private TextView mDoctorSpecialty;
    private TextView mDoctorAddress;
    private TextView mRatingNumber;
    private RatingBar mRatingBar;
    private int mNameFieldHeight;
    private ViewGroup.LayoutParams mParams;
    private int mIndex;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      mDoctorPhoto = itemView.findViewById(R.id.doctor_photo_list);
      mDoctorName = itemView.findViewById(R.id.doctor_name_list);
      mDoctorSpecialty = itemView.findViewById(R.id.doctor_specialty_list);
      mDoctorAddress = itemView.findViewById(R.id.doctor_address_list);
      mRatingNumber = itemView.findViewById(R.id.doctor_rating_number_list);
      mRatingBar = itemView.findViewById(R.id.star_doctor_list);
      mParams = mDoctorName.getLayoutParams();
      mNameFieldHeight = mDoctorName.getHeight();

      itemView.setOnClickListener(view -> {
        DoctorInfoFragment fragment = new DoctorInfoFragment();

        Bundle args = new Bundle();
        args.putString("Name", mDoctorList.get(mIndex).getMName());
        args.putString("Specialty", mDoctorList.get(mIndex).getMSpecialty());
        args.putString("Address", mDoctorList.get(mIndex).getMAddress());
        args.putInt("PhotoUrl", mDoctorList.get(mIndex).getMPhotoUrl());
        args.putFloat("Rating", mDoctorList.get(mIndex).getMRating());
        fragment.setArguments(args);

        mContext.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

//        FragmentManager manager = mContext.getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
      });
    }
  }
}
