/*
 * Copyright (c) 2025 Rique
 * Shared path resolver for Android & PC builds
 */

package com.rique.lunara;

import android.content.Context;
import android.os.Build;

import java.io.File;

public class GlobalPaths {

    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static File getAppStorage() {
        if (context != null) {
            return context.getFilesDir(); // Android internal storage
        } else {
            // Fallback for PC/Desktop use
            String userDir = System.getProperty("user.home");
            File desktopPath = new File(userDir, "LunaraData");
            if (!desktopPath.exists()) desktopPath.mkdirs();
            return desktopPath;
        }
    }

    public static File getModelFolder() {
        File path = new File(getAppStorage(), "models");
        if (!path.exists()) path.mkdirs();
        return path;
    }

    public static File getLogFolder() {
        File path = new File(getAppStorage(), "logs");
        if (!path.exists()) path.mkdirs();
        return path;
    }
}