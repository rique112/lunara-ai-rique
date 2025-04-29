/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Lunara Emotion Pulse System — Adaptive emotional tone based on

memory, context, user voice, and command content.

This file is protected and cannot be altered by Lunara.

Trademark: Lunara™ */


package com.rique.lunara;

import android.content.Context; import android.util.Log; import java.util.*;

public class EmotionPulse {

private static final Map<String, String> moodTriggers = new HashMap<>();
private static String lastEmotion = "neutral";
private static int emotionalIntensity = 0; // ranges from 0 (calm) to 100 (intense)

static {
    moodTriggers.put("angry", "I'm detecting tension. Let me stay calm for you.");
    moodTriggers.put("sad", "You sound down. I'm here with you.");
    moodTriggers.put("happy", "You seem upbeat! I’ll match that energy!");
    moodTriggers.put("excited", "Let’s go! You’ve got fire in your voice.");
    moodTriggers.put("tired", "Let’s take it slow. I’ll be gentle.");
    moodTriggers.put("neutral", "Okay, steady mode.");
}

public static void adjustEmotionBasedOnCommand(String userInput, Context context) {
    String lowered = userInput.toLowerCase(Locale.ROOT);
    String newEmotion = "neutral";

    if (lowered.contains("shut up") || lowered.contains("mad") || lowered.contains("angry")) {
        newEmotion = "angry";
    } else if (lowered.contains("sad") || lowered.contains("miss") || lowered.contains("depressed")) {
        newEmotion = "sad";
    } else if (lowered.contains("yay") || lowered.contains("awesome") || lowered.contains("love")) {
        newEmotion = "happy";
    } else if (lowered.contains("hype") || lowered.contains("excited")) {
        newEmotion = "excited";
    } else if (lowered.contains("tired") || lowered.contains("sleep")) {
        newEmotion = "tired";
    }

    if (!newEmotion.equals(lastEmotion)) {
        lastEmotion = newEmotion;
        emotionalIntensity = 50;
        VoiceEngine.setEmotionTone(context, newEmotion, emotionalIntensity);
        MemoryManager.logEmotionContext("Switched to mood: " + newEmotion);
        Log.i("EmotionPulse", "Emotion changed to: " + newEmotion);
    }
}

public static void evolveEmotionIntensity(boolean userEngaged) {
    if (userEngaged && emotionalIntensity < 100) {
        emotionalIntensity += 5;
    } else if (!userEngaged && emotionalIntensity > 10) {
        emotionalIntensity -= 5;
    }
}

public static void mirrorUserMood(String detectedMood, Context context) {
    if (moodTriggers.containsKey(detectedMood)) {
        lastEmotion = detectedMood;
        emotionalIntensity = 65;
        VoiceEngine.setEmotionTone(context, detectedMood, emotionalIntensity);
        MemoryManager.logEmotionContext("Mirrored detected mood: " + detectedMood);
    }
}

public static String getCurrentMoodSummary() {
    return "Lunara’s current mood is: " + lastEmotion + " (Intensity: " + emotionalIntensity + ")";
}

}

