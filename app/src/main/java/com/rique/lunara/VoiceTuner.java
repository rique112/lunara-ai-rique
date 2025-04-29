/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * VoiceTuner.java – Dynamically adapts and refines Lunara's voice presets.
 * Self-upgradable when permission is granted by Rique.
 */

package com.rique.lunara;

import java.util.HashMap;

public class VoiceTuner {
    private static final HashMap<String, Float> voiceSettings = new HashMap<>();

    static {
        voiceSettings.put("default_pitch", 1.0f);
        voiceSettings.put("default_speed", 1.0f);
    }

    public static void refineVoice(String feedbackType) {
        if (!PermissionFlags.voiceLearningAllowed) return;

        switch (feedbackType.toLowerCase()) {
            case "too_slow":
                voiceSettings.put("default_speed", voiceSettings.get("default_speed") + 0.1f);
                break;
            case "too_fast":
                voiceSettings.put("default_speed", voiceSettings.get("default_speed") - 0.1f);
                break;
            case "too_high":
                voiceSettings.put("default_pitch", voiceSettings.get("default_pitch") - 0.1f);
                break;
            case "too_low":
                voiceSettings.put("default_pitch", voiceSettings.get("default_pitch") + 0.1f);
                break;
        }
    }

    public static float getPitch() {
        return voiceSettings.get("default_pitch");
    }

    public static float getSpeed() {
        return voiceSettings.get("default_speed");
    }
}
