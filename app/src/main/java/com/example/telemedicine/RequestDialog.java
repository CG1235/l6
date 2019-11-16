package com.example.telemedicine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RequestDialog extends DialogFragment {

  OnConfirmClickedListener onConfirmClickedListener;
  OnCancelClickedListener onCancelClickedListener;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage("Confirm request?")
            .setNegativeButton("Cancel", (dialogInterface, i) -> {
              if (onCancelClickedListener != null)
                onCancelClickedListener.onCancelClicked();
            })
            .setPositiveButton("Confirm", ((dialogInterface, i) -> {
              if(onConfirmClickedListener != null)
                onConfirmClickedListener.onConfirmClicked();
            }));
    return builder.create();
  }

  public interface OnConfirmClickedListener{
    void onConfirmClicked();
  }

  public interface OnCancelClickedListener{
    void onCancelClicked();
  }

  public void setOnConfirmClickedListener(OnConfirmClickedListener listener){
    this.onConfirmClickedListener = listener;
  }

  public void setOnCancelClickedListener(OnCancelClickedListener listener){
    this.onCancelClickedListener = listener;
  }
}
