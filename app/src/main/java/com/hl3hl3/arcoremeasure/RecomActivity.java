package com.hl3hl3.arcoremeasure;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hl3hl3.arcoremeasure.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harjot on 27/9/18.
 */

public class RecomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recom_activity);
    }

    public void callPlaces(View view){
//      /  final TextView mTextView = (TextView) findViewById(R.id.textView);
//  Find based on Pnr and last name
        final EditText pnr = (EditText) findViewById(R.id.pnr);
        final EditText lastname = (EditText) findViewById(R.id.lastname);

        String url_pnr = "https://apigw.singaporeair.com/appchallenge/api/pax/pnr";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("bookingReference", pnr.getText().toString());
        params.put("bookingLastName", lastname.getText().toString());
        Log.v("values",pnr.getText().toString()+' '+ lastname.getText().toString());

        JsonObjectRequest strRequest = new JsonObjectRequest(url_pnr,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("Res is ------------------->", response.toString());
                        }catch(Exception e){
                            Log.v("error",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.v("error",error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("apikey", "aghk73f4x5haxeby7z24d2rc");

                return params;
            }
        };

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
//        String myApiKey="";
//        try {
//            ApplicationInfo ai = getPackageManager().getApplicationInfo(getCallingActivity().getPackageName(), PackageManager.GET_META_DATA);
//            Bundle bundle = ai.metaData;
//            myApiKey = bundle.getString("googleKey");
//        } catch (Exception e) {
//        }
        //String url2 = "https://maps.googleapis.com/maps/api/geocode/json?address=Australia&key=AIzaSyCP-FfCs2PYzYflGfoPeMe3fq8z9XsLR1Q";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=place+to+see+in+Singapore&key=AIzaSyCP-FfCs2PYzYflGfoPeMe3fq8z9XsLR1Q";
       // String url1 ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=Places%20to%20see%20in%20Australia&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours&key=AIzaSyANz3phI8cHUZPdsXP1JVvyQnntdF_aIl0";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response);
                        try {
                            ListView listView = (ListView) findViewById(R.id.list_view);

                            List<String> items = new ArrayList<>();

                            JSONObject json = new JSONObject(response);
                            JSONArray arr = json.getJSONArray("results");

                            for (int i = 0; i < arr.length() && i < 10; i++) {
                                JSONObject object = arr.getJSONObject(i);
                                items.add(object.getString("name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, items);

                            if (listView != null) {
                                listView.setAdapter(adapter);
                            }

                        }catch(Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(strRequest);
        queue.add(stringRequest);
    }


}
