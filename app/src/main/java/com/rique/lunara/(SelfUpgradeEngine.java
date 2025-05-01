/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara Self-Upgrade Manager
 * Manages safe, permission-based evolution of Lunara AI
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SelfUpgradeManager {

    private static final String UPGRADE_LOG = "lunara_self_upgrades.txt";

    public static void requestUpgrade(Context context, String description) {
        // This function must be called manually or with permission
        if (!PermissionFlags.upgradeAllowed) {
            Log.w("SelfUpgradeManager", "Upgrade blocked — permission not granted.");
            return;
        }

        // Simulate an upgrade process (e.g., adding new features, adapting code)
        logUpgrade(context, description);
        BehaviorTracker.recordMood(context, "UPGRADE", "Lunara has evolved: " + description);
    }

    private static void logUpgrade(Context context, String description) {
        try {
            File logFile = new File(context.getFilesDir(), UPGRADE_LOG);
            FileWriter writer = new FileWriter(logFile, true);

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            writer.append("[UPGRADE] ").append(time).append(" → ").append(description).append("\n");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            Log.e("SelfUpgradeManager", "Failed to log upgrade: " + e.getMessage());
        }
    }

    // Reset all upgrades if you ever want to wipe
    public static void resetUpgrades(Context context) {
        try {
            File logFile = new File(context.getFilesDir(), UPGRADE_LOG);
            if (logFile.exists()) {
                logFile.delete();
            }
        } catch (Exception e) {
            Log.e("SelfUpgradeManager", "Failed to reset upgrades: " + e.getMessage());
        }
    }
}