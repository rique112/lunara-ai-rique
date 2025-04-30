/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

SystemStateTracker.java – Tracks Lunara's current system state.

Saves mode, flags, mood, and restores them on reboot. */


package com.rique.lunara;

import android.content.Context; import android.util.Log;

import org.json.JSONObject; import org.json.JSONException;

import java.io.File; import java.io.FileWriter; import java.nio.file.Files; import java.nio.file.Paths;

public class SystemStateTracker {

private static final String STATE_FILE = "lunara_state.json";

public static void saveState(Context context) {
    try {
        JSONObject state = new JSONObject();
        state.put("nsfwEnabled", PermissionFlags.imageGenerationAllowed);
        state.put("internetEnabled", PermissionFlags.internetAllowed);
        state.put("upgradeEnabled", PermissionFlags.upgradeAllowed);
        state.put("voiceLearning", PermissionFlags.voiceLearningAllowed);
        state.put("memoryExpanded", PermissionFlags.memoryExpansionAllowed);
        state.put("currentMood", EmotionPulse.getMood());

        File file = new File(context.getFilesDir(), STATE_FILE);
        FileWriter writer = new FileWriter(file);
        writer.write(state.toString(2));
        writer.close();

        Log.i("SystemState", "State saved.");
    } catch (Exception e) {
        Log.e("SystemState", "Failed to save: " + e.getMessage());
    }
}

public static void loadState(Context context) {
    try {
        File file = new File(context.getFilesDir(), STATE_FILE);
        if (!file.exists()) return;

        String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        JSONObject state = new JSONObject(content);

        PermissionFlags.imageGenerationAllowed = state.optBoolean("nsfwEnabled", false);
        PermissionFlags.internetAllowed = state.optBoolean("internetEnabled", false);
        PermissionFlags.upgradeAllowed = state.optBoolean("upgradeEnabled", false);
        PermissionFlags.voiceLearningAllowed = state.optBoolean("voiceLearning", false);
        PermissionFlags.memoryExpansionAllowed = state.optBoolean("memoryExpanded", false);

        String mood = state.optString("currentMood", "neutral");
        EmotionPulse.setMood(mood);

        Log.i("SystemState", "State loaded.");
    } catch (Exception e) {
        Log.e("SystemState", "Failed to load: " + e.getMessage());
    }
}

public static void clearState(Context context) {
    try {
        File file = new File(context.getFilesDir(), STATE_FILE);
        if (file.exists()) file.delete();
        Log.i("SystemState", "State cleared.");
    } catch (Exception e) {
        Log.e("SystemState", "Failed to clear: " + e.getMessage());
    }
}

}

