package com.example.sample.samplenavigation;

/**
 * Created by Rajarajan Selvaraj on 21/01/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpJSONParser {
    private String TAG = HttpJSONParser.this.getClass().getSimpleName();
    private HttpURLConnection mHttpURLConnection;

    public JSONArray getJSONData(String path) {
        String response="";
        URL url;
        JSONArray mJSONArray = null;

        try {
            url = new URL(path);

            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setReadTimeout(15000);
            mHttpURLConnection.setConnectTimeout(15000);
            mHttpURLConnection.setRequestMethod("GET");

            int responseCode = mHttpURLConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line + '\n');
                }
                response = result.toString();
            } else {
                response = "";
            }
        } catch (Exception e) {
            Log.e(TAG,"Exception while getting Data from server :" + e.getMessage());
            e.printStackTrace();
        }finally {
            mHttpURLConnection.disconnect();
        }

        try{
            mJSONArray = new JSONArray(response);
        }catch(JSONException ex){
            Log.e(TAG, "Exception Occured :" + ex.getMessage());
        }
        return mJSONArray;
    }


}
