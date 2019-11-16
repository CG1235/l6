package com.example.telemedicine;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.telemedicine.Interfaces.OnConsultationAddedListener;
import com.example.telemedicine.Interfaces.OnConsultationFailureListener;
import com.example.telemedicine.Interfaces.OnDoctorGetListener;
import com.example.telemedicine.Interfaces.OnDoctorListLoadedListener;
import com.example.telemedicine.Interfaces.OnGetDoctorFailureListener;
import com.example.telemedicine.Interfaces.OnLoginFailedListener;
import com.example.telemedicine.Interfaces.OnLoginSucceedListener;
import com.example.telemedicine.Interfaces.OnRegistrationFailedListener;
import com.example.telemedicine.Interfaces.OnRegistrationFinishedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.telemedicine.Constants.*;

public class HttpRequestManager {

  private OnRegistrationFinishedListener onRegistrationFinishedListener;
  private OnRegistrationFailedListener onRegistrationFailedListener;
  private OnLoginSucceedListener onLoginSucceedListener;
  private OnLoginFailedListener onLoginFailedListener;
  private OnDoctorListLoadedListener onDoctorListLoadedListener;
  private OnDoctorGetListener onDoctorGetListener;
  private OnGetDoctorFailureListener onGetDoctorFailureListener;
  private OnConsultationAddedListener onConsultationAddedListener;
  private OnConsultationFailureListener onConsultationFailureListener;

  public synchronized void registerUser(Context ctx, UserInfo userInfo) throws JSONException {
    String regUrl = API_URL + REG_URL;

    StringRequest request = new StringRequest(Request.Method.POST, regUrl, response -> {
      if (onRegistrationFinishedListener != null){
        onRegistrationFinishedListener.onRegistrationFinished(response);
      }
    }, error -> {
      if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Log.d("RESPONSE_ERROR", "time out / no connection!" + error.getMessage());
          } else if (error instanceof AuthFailureError) {
            Log.d("RESPONSE_ERROR", "Authentication Failure while performing the request" + error.getMessage());
          } else if (error instanceof ServerError) {
            Log.d("RESPONSE_ERROR", "server responded with a error response" + error.getMessage());
          } else if (error instanceof NetworkError) {
            Log.d("RESPONSE_ERROR", "network error while performing the request" + error.getMessage());
          } else if (error instanceof ParseError) {
            Log.d("RESPONSE_ERROR", "server response could not be parsed" + error.getMessage());
          }
      Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
      if (onRegistrationFailedListener != null)
        onRegistrationFailedListener.onRegistrationFailed(error);
    }){
      @Override
      protected Map<String, String> getParams(){
        Map<String, String> data = new HashMap<>();
        data.put(FULL_NAME, userInfo.getMFullName());
        data.put(BIRTHDAY, userInfo.getMBirthday());
        data.put(EMAIL, userInfo.getMEmail());
        data.put(PHONE, userInfo.getMPhone());
        data.put(ADDRESS, userInfo.getMAddress());
        data.put(USERNAME, userInfo.getMUserName());
        data.put(PASSWORD, userInfo.getMPassword());
        data.put(BASE_64_PHOTO, userInfo.getMBase64Photo());
        return data;
      }

      @Override
      public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
      }

      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        return headers;
      }
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }

  public synchronized void authUser(Context ctx, ArrayList<String> credentials) throws JSONException {
    String authUrl = API_URL + AUTH_URL;

    JSONObject req = new JSONObject();
    req.put(EMAIL, credentials.get(0));
    req.put(PASSWORD, credentials.get(1));

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, authUrl, req,
            response -> {
              if(onLoginSucceedListener != null){
                onLoginSucceedListener.onLoginSucceed(response);
              }
            }, error -> {
                Toast.makeText(ctx, "Auth error" + error.getMessage(), Toast.LENGTH_LONG).show();
                if (onLoginFailedListener != null){
                  onLoginFailedListener.onLoginFailed(error);
                }
            }){
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }

  public synchronized void getDoctorList(Context ctx, String token){
    String url = API_URL + GET_DOCTOR_LIST_URL;
    StringRequest request = new StringRequest(Request.Method.GET, url,
            response -> {
              if (onDoctorListLoadedListener != null){
                onDoctorListLoadedListener.onDoctorListLoaded(response);
              }
            },
            error -> {
              Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
              System.out.println("Error" + error.getMessage());
            }){
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        headers.put("token", token);
        return headers;
      }
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }

  public synchronized void getDoctorById(Context ctx, int id, String token){
    String url = API_URL + GET_DOCTOR_URL + id;
    StringRequest request = new StringRequest(Request.Method.GET, url,
            response -> {
              if (onDoctorGetListener != null){
                onDoctorGetListener.onDoctorGet(response);
              }
            },
            error -> {
              Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
              if (onGetDoctorFailureListener != null)
                onGetDoctorFailureListener.onGetDoctorFailure(error); }
    ){
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        headers.put("token", token);
        return headers;
      }
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }

  public synchronized void addCons(Context ctx, ConsultationInfo info, String token){
    String url = API_URL + ADD_CONS_URL;
    StringRequest request = new StringRequest(Request.Method.POST, url,
            response -> {
              if (onConsultationAddedListener != null)
                onConsultationAddedListener.onConsultationAdded(response);
            },
            error -> {
              if (onConsultationFailureListener != null)
                onConsultationFailureListener.onConsultationFailure(error);
            }){
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        headers.put("token", token);
        return headers;
      }

      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("ConsId", String.valueOf(info.getMConsId()));
        params.put("Name", info.getMName());
        params.put("Disease", info.getMDisease());
        params.put("Address", info.getMAddress());
        params.put("Description", info.getMDescription());
        params.put("DocId", String.valueOf(info.getMDocId()));
        params.put("IsConfirmed", info.getMIsConfirmed());
        return params;
      }
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }


  public void setOnRegistrationFinishedListener(OnRegistrationFinishedListener listener){
    this.onRegistrationFinishedListener = listener;
  }

  public void setOnRegistrationFailedListener(OnRegistrationFailedListener listener){
    this.onRegistrationFailedListener = listener;
  }

  public void setOnLoginSucceedListener(OnLoginSucceedListener listener){
    this.onLoginSucceedListener = listener;
  }

  public void setOnLoginFailedListener(OnLoginFailedListener listener){
    this.onLoginFailedListener = listener;
  }

  public void setOnDoctorListLoadedListener(OnDoctorListLoadedListener listener){
    this.onDoctorListLoadedListener = listener;
  }

  public void setOnDoctorGetListener(OnDoctorGetListener listener){
    this.onDoctorGetListener = listener;
  }

  public void setOnGetDoctorFailureListener(OnGetDoctorFailureListener listener){
    this.onGetDoctorFailureListener = listener;
  }

  public void setOnConsultationAddedListener(OnConsultationAddedListener listener){
    this.onConsultationAddedListener = listener;
  }

  public void setOnConsultationFailureListener(OnConsultationFailureListener listener){
    this.onConsultationFailureListener = listener;
  }

  HttpRequestManager(){
    onRegistrationFinishedListener  = null;
    onRegistrationFailedListener    = null;
    onLoginSucceedListener          = null;
    onLoginFailedListener           = null;
    onDoctorListLoadedListener      = null;
    onDoctorGetListener             = null;
    onGetDoctorFailureListener      = null;
    onConsultationAddedListener     = null;
    onConsultationFailureListener   = null;
  }
}
