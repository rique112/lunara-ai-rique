/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara Behavior Tracker
 * Tracks AI behavior patterns, emotion tagging, usage trends
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BehaviorTracker {

    private static final String BEHAVIOR_FILE = "lunara_behavior_log.txt";

    public static void recordMood(Context context, String tag, String note) {
        try {
            File file = new File(context.getFilesDir(), BEHAVIOR_FILE);
            FileWriter writer = new FileWriter(file, true);

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            writer.append("[").append(tag.toUpperCase()).append("] ").append(timestamp).append(" → ").append(note).append("\n");

            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e("BehaviorTracker", "Error logging mood: " + e.getMessage());
        }
    }

    public static void resetMoodHistory(Context context) {
        try {
            File file = new File(context.getFilesDir(), BEHAVIOR_FILE);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Log.e("BehaviorTracker", "Error clearing mood history: " + e.getMessage());
        }
    }
}