/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

ScreenObserverService.java – Background service to observe foreground apps and UI content.

Requires Android Accessibility Service permissions. */


package com.rique.lunara;

import android.accessibilityservice.AccessibilityService; import android.util.Log; import android.view.accessibility.AccessibilityEvent; import java.io.File; import java.io.FileWriter; import java.text.SimpleDateFormat; import java.util.Date; import java.util.Locale;

public class ScreenObserverService extends AccessibilityService {

@Override
public void onAccessibilityEvent(AccessibilityEvent event) {
    if (event == null || event.getText() == null) return;

    try {
        StringBuilder rawText = new StringBuilder();
        for (CharSequence cs : event.getText()) {
            rawText.append(cs.toString()).append(" ");
        }

        String logText = rawText.toString().trim();
        if (!logText.isEmpty()) {
            logAndTeach(logText);
        }
    } catch (Exception e) {
        Log.e("ScreenObserver", "Error reading screen text: " + e.getMessage());
    }
}

@Override
public void onInterrupt() {
    Log.w("ScreenObserver", "Observation service interrupted");
}

private void logAndTeach(String observedText) {
    try {
        File logFile = new File(getFilesDir(), "observed_screen_log.txt");
        FileWriter writer = new FileWriter(logFile, true);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        writer.append("[" + timestamp + "] " + observedText + "\n");
        writer.close();

        MemoryManager.saveMemory(this, observedText, "Observation");
        TrainerEngine.learn("screen-observe", observedText);
    } catch (Exception e) {
        Log.e("ScreenObserver", "Failed to log screen content: " + e.getMessage());
    }
}

}

