package com.getfilecordovaplugin;

import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GetFilePlugin extends CordovaPlugin {
    private static final String TAG = "GetFilePlugin";


    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {
        if ("getFile".equals(action)) {
            return init(data, callbackContext);
        }
    }

    public boolean init(final JSONArray data, final CallbackContext callbackContext) {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            this.handleSend(intent);
        }

    }

    private void handleSend(Intent intent) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

        if (fileUri != null) {
            if (callbackContext != null) {
                PluginResult result = new PluginResult(PluginResult.Status.OK, fileUri);
                result.setKeepCallback(true);
                Log.i(TAG, "Sending plugin result fileUri=" + (fileUri.isEmpty() ? "<empty>" : fileUri));
                callbackContext.sendPluginResult(result);
            }
        }
    }
}
