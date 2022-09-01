package com.getfilecordovaplugin;

import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;
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
    if ("init".equals(action)) {
      return init(data, callbackContext);
    }
    }

  // Initialize the plugin
  public boolean init(final JSONArray data, final CallbackContext context) {
    log(DEBUG, "init() " + data);

    if (data.length() != 0) {
      log(WARN, "init() -> invalidAction");
      return false;
    }

    onNewIntent(cordova.getActivity().getIntent());
    log(DEBUG, "init() -> ok");

    return PluginResultSender.ok(context);
  }
  
    /**
   * This is called when a new intent is sent while the app is already opened.
   *
   * We also call it manually with the cordova application intent when the plugin
   * is initialized (so all intents will be managed by this method).
   */
  @Override
  public void onNewIntent(final Intent intent) {
    log(DEBUG, "onNewIntent() " + intent.getAction());

    final JSONObject json = toJSONObject(intent);
    if (json != null) {
      pendingIntents.add(json);
    }

    processPendingIntents();
  }
  
    /**
   * When the handler is defined, call it with all attached files.
   */
  private void processPendingIntents() {
    log(DEBUG, "processPendingIntents()");

    if (handlerContext == null) {
      return;
    }

    for (int i = 0; i < pendingIntents.size(); i++) {
      sendIntentToJavascript((JSONObject) pendingIntents.get(i));
    }

    pendingIntents.clear();
  }
  
    /** Calls the javascript intent handlers. */
  private void sendIntentToJavascript(final JSONObject intent) {
    final PluginResult result = new PluginResult(PluginResult.Status.OK, intent);

    result.setKeepCallback(true);
    handlerContext.sendPluginResult(result);
  }

  /**
   * Converts an intent to JSON
   */
  private JSONObject toJSONObject(final Intent intent) {
    try {
      final ContentResolver contentResolver = this.cordova
        .getActivity().getApplicationContext().getContentResolver();

      return Serializer.toJSONObject(contentResolver, intent);
    } catch (JSONException e) {
      log(ERROR, "Error converting intent to JSON: " + e.getMessage());
      log(ERROR, Arrays.toString(e.getStackTrace()));

      return null;
    }
  }
  
  
}
