package com.getfilecordovaplugin;

import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import android.app.Activity;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


 public class MyActivity extends Activity {

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            this.handleSend(intent);
        }
     }

     protected void onPause() {
         super.onPause();
     }
     
    private void handleSend(Intent intent) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String fileUriS = fileUri.toString();
        Log.i(TAG, "Sending plugin result fileUri=" + (fileUriS.isEmpty() ? "<empty>" : fileUriS));
    }
     
     
 }
