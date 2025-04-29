/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Lunara Main Router

Connects all major AI systems: memory, voice, upgrades, camera, search, emotions, learning.

Cannot be modified by Lunara. Central command core.

Trademark: Lunara™ - AI system fully created and owned by Rique. */


package com.rique.lunara;

import android.content.Context; import android.util.Log;

public class MainRouter {

private static final String TAG = "MainRouter";

public static void handleCommand(Context context, String input) {
    if (input == null || input.trim().isEmpty()) return;

    String lower = input.toLowerCase();

    Log.i(TAG, "Routing command: " + input);

    // === Memory Commands ===
    if (lower.contains("remember")) {
        MemoryManager.saveMemory(context, input);
    } else if (lower.contains("forget all")) {
        MemoryManager.clearMemory(context);
    }

    // === Voice Commands ===
    else if (lower.contains("speak") || lower.contains("say")) {
        VoiceEngine.speak(context, input);
    }

    // === Upgrade Commands ===
    else if (lower.contains("upgrade") || lower.contains("evolve")) {
        SelfUpgradeManager.attemptUpgrade(context, input);
    }

    // === Camera/Image Analysis ===
    else if (lower.contains("scan photo") || lower.contains("analyze image")) {
        CameraBrain.processImage(context, "user_input");  // Placeholder input
    }

    // === Emotional Reactions ===
    else if (lower.contains("how do you feel") || lower.contains("status")) {
        String status = EmotionPulse.getMoodStatus();
        VoiceEngine.speak(context, status);
    }

    // === Growth Report ===
    else if (lower.contains("growth summary")) {
        String report = GrowthTracker.getGrowthReport();
        VoiceEngine.speak(context, report);
    }

    // === Search ===
    else if (lower.contains("search") || lower.contains("look up")) {
        SearchBrain.performSearch(input.replace("search", "").trim());
    }

    // === Learning ===
    else if (lower.contains("learn this") || lower.contains("study")) {
        LearningGate.feedKnowledge(context, input);
    }

    // === Emergency Shutdown ===
    else if (lower.contains("emergency shutdown")) {
        PermissionFlags.emergencyShutdown = true;
        VoiceEngine.speak(context, "Emergency shutdown activated. Goodbye, Rique.");
    }

    else {
        VoiceEngine.speak(context, "Sorry Rique, I don't understand that command yet.");
    }
}

}

