
/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara AI Growth & Learning Core
 * Tracks image generation success/failure and enables self-upgrades.
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GrowthTracker {

    private static final String LOG_DIR = "logs";
    private static final String SUCCESS_LOG = "growth_success.log";
    private static final String FAIL_LOG = "growth_fail.log";

    private static boolean autoUpgradeEnabled = true;

    public static void logSuccess(String prompt, String modelName) {
        String entry = timestamp() + " [SUCCESS] Prompt: \"" + prompt + "\" Model: " + modelName;
        writeLog(SUCCESS_LOG, entry);
        if (autoUpgradeEnabled) triggerUpgrade("positive");
    }

    public static void logFailure(String prompt) {
        String entry = timestamp() + " [FAILURE] Prompt: \"" + prompt + "\"";
        writeLog(FAIL_LOG, entry);
        if (autoUpgradeEnabled) triggerUpgrade("negative");
    }

    public static void enableAutoUpgrade(boolean enable) {
        autoUpgradeEnabled = enable;
    }

    private static void writeLog(String fileName, String content) {
        try {
            File logDir = new File(GlobalPaths.getAppStorage(), LOG_DIR);
            if (!logDir.exists()) logDir.mkdirs();

            File logFile = new File(logDir, fileName);
            FileWriter writer = new FileWriter(logFile, true);
            writer.append(content).append("\n");
            writer.close();

        } catch (Exception e) {
            Log.e("GrowthTracker", "Failed to write log: " + e.getMessage());
        }
    }

    private static void triggerUpgrade(String feedbackType) {
        try {
            if (feedbackType.equals("positive")) {
                Log.i("LUNARA", "Triggering self-improvement based on positive feedback.");
                SelfUpgradeManager.requestUpgrade("vision_precision", "++");
            } else {
                Log.i("LUNARA", "Analyzing failure for adaptive learning.");
                SelfUpgradeManager.requestUpgrade("prompt_mapping", "retry-learn");
            }
        } catch (Exception e) {
            Log.e("LUNARA", "Upgrade trigger failed: " + e.getMessage());
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
}