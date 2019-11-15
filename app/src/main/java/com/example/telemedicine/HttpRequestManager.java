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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.telemedicine.Constants.*;

public class HttpRequestManager {

  private OnRegistrationFinishedListener onRegistrationFinishedListener;
  private OnRegistrationFailedListener onRegistrationFailedListener;


  public void registerUser(Context ctx, UserInfo userInfo) throws JSONException {
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
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
      }
    };
    request.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    request.setShouldCache(false);
    Volley.newRequestQueue(ctx).add(request);
  }

  public void logInUser(Context ctx, List<String> credentials){

  }

  public interface OnRegistrationFinishedListener{
    void onRegistrationFinished(String response);
  }

  public interface OnRegistrationFailedListener{
    void onRegistrationFailed(VolleyError error);
  }

  public void setOnRegistrationFinishedListener(OnRegistrationFinishedListener listener){
    this.onRegistrationFinishedListener = listener;
  }

  public void setOnRegistrationFailedListener(OnRegistrationFailedListener listener){
    this.onRegistrationFailedListener = listener;
  }

  HttpRequestManager(){
    onRegistrationFinishedListener = null;
    onRegistrationFailedListener = null;
  }
}
