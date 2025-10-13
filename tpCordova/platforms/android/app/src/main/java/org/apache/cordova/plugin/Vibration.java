package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.content.Context;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class Vibration extends CordovaPlugin {
    private static final String TAG = "CordovaVibration";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "execute() called with action = " + action);
        if ("vibrate".equals(action)) {
            JSONArray patternArray = args.getJSONArray(0);
            Log.d(TAG, "Pattern reçu depuis JS: " + patternArray.toString());
            long[] pattern = new long[patternArray.length()];
            for (int i = 0; i < patternArray.length(); i++) {
                pattern[i] = patternArray.getLong(i);
            }
            vibrate(pattern);
            callbackContext.success();
            return true;
        }

        Log.w(TAG, "Action non reconnue: " + action);
        return false;
    }

    private void vibrate(long[] pattern) {
        Vibrator v = (Vibrator) cordova.getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        Log.d(TAG, "Pattern final envoyé au Vibrator: " + java.util.Arrays.toString(pattern));
        if (v != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createWaveform(pattern, -1));
            } else {
                v.vibrate(pattern, -1);
            }
            Log.d(TAG, "Vibration exécutée");
        }
    }
}