/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

LearningGate.java – Filters and controls what Lunara can learn.

Protects against unsafe or harmful knowledge. Evolves only with permission. */


package com.rique.lunara;

import android.util.Log; import java.util.Arrays; import java.util.List;

public class LearningGate {

private static final List<String> blockedKeywords = Arrays.asList(
    "violence", "harm", "exploit", "abuse", "manipulate",
    "illegal", "weapon", "terror", "suicide", "dark web"
);

public static boolean canLearn(String input) {
    if (!PermissionFlags.upgradeAllowed || PermissionFlags.emergencyShutdown) {
        Log.w("LearningGate", "Learning denied due to permission flags.");
        return false;
    }

    for (String keyword : blockedKeywords) {
        if (input.toLowerCase().contains(keyword)) {
            Log.w("LearningGate", "Blocked keyword found in input: " + keyword);
            return false;
        }
    }

    return true;
}

public static String filterAndClean(String input) {
    String sanitized = input;
    for (String keyword : blockedKeywords) {
        if (sanitized.toLowerCase().contains(keyword)) {
            sanitized = sanitized.replaceAll("(?i)" + keyword, "[REDACTED]");
        }
    }
    return sanitized.trim();
}

}

