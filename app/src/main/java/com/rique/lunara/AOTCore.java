/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * AOTCore.java – Auto Observe + Think loop for Lunara.
 * Learns from user patterns, moods, and usage over time.
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AOTCore {

    private static boolean running = false;
    private static final String OBSERVE_LOG = "aot_loop_log.txt";

    public static void start(Context context) {
        if (!PermissionFlags.upgradeAllowed || !PermissionFlags.memoryExpansionAllowed) {
            Log.w("AOTCore", "Observation denied by permission flags.");
            return;
        }
        if (running) return;
        running = true;

        new Thread(() -> {
            try {
                while (running) {
                    observe(context);
                    Thread.sleep(15000);  // Every 15 seconds
                }
            } catch (Exception e) {
                Log.e("AOTCore", "Error in loop: " + e.getMessage());
            }
        }).start();
    }

    public static void stop() {
        running = false;
        Log.i("AOTCore", "Auto observation stopped.");
    }

    private static void observe(Context context) {
        String mood = EmotionPulse.getMood();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        String entry = "[AOT] " + timestamp + " – Mood: " + mood + "\n";
        writeLog(context, entry);

        if (mood.contains("sad") || mood.contains("drained")) {
            VoiceEngine.speak("I’m sensing something's off, Rique. I'm here for you.");
        } else if (mood.contains("happy") || mood.contains("excited")) {
            VoiceEngine.speak("You sound great today, Rique. Let’s make it even better.");
        }

        GrowthTracker.logInteraction();
    }

    private static void writeLog(Context context, String content) {
        try {
            File file = new File(context.getFilesDir(), OBSERVE_LOG);
            FileWriter writer = new FileWriter(file, true);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e("AOTCore", "Failed to write: " + e.getMessage());
        }
    }

    public static boolean isRunning() {
        return running;
    }
} 
