package com.rique.lunara;

import android.content.Context;
import android.provider.Settings;

public class LunaraAppProtector {

    private static final String ALLOWED_DEVICE_ID = "your_device_id_here";

    public static boolean isAllowedDevice(Context context) {
        String deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceID.equals(ALLOWED_DEVICE_ID);
    }
}