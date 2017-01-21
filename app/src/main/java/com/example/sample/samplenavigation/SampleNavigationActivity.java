package com.example.sample.samplenavigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class SampleNavigationActivity extends AppCompatActivity {

    private String TAG = SampleNavigationActivity.this.getClass().getSimpleName();
    private Spinner mSpinner = null;
    private TextView mTextView2 = null;
    private TextView mTextView3 = null;
    private JSONArray mJSONArray = null;
    private Context mContext = null;
    private ProgressDialog Dialog = null;
    private Double mLatitude = null;
    private Double mLongitude = null;
    private ArrayList<NavigationData> navigationData = null;
    private static final String SERVER_URL="http://express-it.optusnet.com.au/sample.json";
    private static final String KEY_NAME = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_navigation);
        mContext = this;
        mSpinner = (Spinner) findViewById(R.id.spinner);
        Button mButton = (Button)findViewById(R.id.button);
        ConnectivityManager mConncMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConncMgr.getActiveNetworkInfo();
        if (mNetworkInfo == null || (!mNetworkInfo.isConnected()))
        {
            AlertDialog.Builder mAlertDialogbuild = new AlertDialog.Builder(mContext);
            mAlertDialogbuild.setTitle(getResources().getString(R.string.app_name));
            mAlertDialogbuild.setMessage(getResources().getString(R.string.nointernet));
            mAlertDialogbuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SampleNavigationActivity.this.finish();
                }
            });
            AlertDialog mAlertDialog = mAlertDialogbuild.create();
            mAlertDialog.show();
        } else {
            new AsyncOperation().execute(SERVER_URL);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLatitude != null && mLongitude != null){
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mLatitude, mLongitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.nocoordinates,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Async Task operation to get the Data from Server
    private class AsyncOperation extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(mContext);
            Dialog.setMessage(getResources().getString(R.string.datafetch));
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpJSONParser mHttpJSONParser = new HttpJSONParser();
            mJSONArray = mHttpJSONParser.getJSONData(SERVER_URL);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (Dialog.isShowing())
                Dialog.dismiss();
            Toast.makeText(mContext, R.string.connecterror, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (Dialog.isShowing()) {
                Dialog.dismiss();
            }
            showValues();
        }

        // Customized OnItemSelectedListener for item selected from  Dropdown.
        public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

            Context mContext;

            public CustomOnItemSelectedListener(Context context){
                this.mContext = context;
            }

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
                String mModeCar ="";
                String mModeTrain ="";
                mLatitude = null;
                mLongitude = null;

                // set values to the TextViews
                if(navigationData != null && navigationData.size() > 0) {
                    mModeCar = navigationData.get(pos).getPlace().getCarValue();
                    mTextView2.setText(getString(R.string.car)+ " - " + mModeCar);
                    if(navigationData.get(pos).getPlace().getTrainValue() != null && navigationData.get(pos).getPlace().getTrainValue().length() > 0 ) {
                        mModeTrain = navigationData.get(pos).getPlace().getTrainValue();
                        mTextView3.setText( getString(R.string.train)+ "- " + mModeTrain);
                    } else {
                        mTextView3.setText("");
                    }

                    mLatitude  = navigationData.get(pos).getLocation().getLatitude();
                    mLongitude = navigationData.get(pos).getLocation().getLongitude();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        }

        //To Display fetched values from Server to the Screen
        private void showValues(){
            String[] name_arr;
            navigationData = new ArrayList<>();
            Gson gson = new Gson();

            if(mJSONArray != null)
                name_arr = new String[mJSONArray.length()];
            else{
                Toast.makeText(mContext, R.string.nodatafetched,Toast.LENGTH_LONG).show();
                if (Dialog.isShowing())
                    Dialog.dismiss();
                return;
            }
            for (int i = 0; i < mJSONArray.length(); i++) {
                try {
                    JSONObject mJSONArrayObject = mJSONArray.getJSONObject(i);
                    NavigationData data = gson.fromJson(mJSONArrayObject.toString(),NavigationData.class);
                    name_arr[i] = mJSONArrayObject.getString(KEY_NAME);
                    navigationData.add(data);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),R.string.exceptionmessage,Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"JSONException : " + e.getMessage());
                }
            }

            mTextView2 = (TextView)findViewById(R.id.textView2);
            mTextView3 = (TextView)findViewById(R.id.textView3);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_item,name_arr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            AdapterView.OnItemSelectedListener itemSelectedListener = new CustomOnItemSelectedListener(mContext);
            mSpinner.setOnItemSelectedListener(itemSelectedListener);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(navigationData != null){
            navigationData.clear();
        }
    }

}
