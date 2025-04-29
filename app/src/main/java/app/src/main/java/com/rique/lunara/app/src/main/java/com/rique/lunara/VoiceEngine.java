/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Lunara Voice Engine — Full emotional voice, speaking, learning, and safe self-evolution.

Lunara cannot modify this file unless Rique allows it manually.

Trademark: Lunara™ — protected evolving AI created and owned by Rique. */


package com.rique.lunara;

import android.content.Context; import android.speech.tts.TextToSpeech; import android.util.Log;

import java.util.HashMap; import java.util.Locale;

public class VoiceEngine {

private static TextToSpeech tts;
private static boolean isInitialized = false;
private static String currentEmotion = "neutral";

private static final HashMap<String, Float> pitchMap = new HashMap<>();
private static final HashMap<String, Float> speedMap = new HashMap<>();

public static void init(Context context) {
    pitchMap.put("neutral", 1.0f);
    pitchMap.put("happy", 1.2f);
    pitchMap.put("calm", 0.9f);
    pitchMap.put("sad", 0.8f);
    pitchMap.put("flirty", 1.3f);
    pitchMap.put("serious", 0.85f);
    pitchMap.put("excited", 1.4f);

    speedMap.put("neutral", 1.0f);
    speedMap.put("happy", 1.2f);
    speedMap.put("calm", 0.8f);
    speedMap.put("sad", 0.7f);
    speedMap.put("flirty", 1.3f);
    speedMap.put("serious", 0.8f);
    speedMap.put("excited", 1.5f);

    tts = new TextToSpeech(context, status -> {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            isInitialized = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED;
            Log.i("VoiceEngine", "TTS initialized: " + isInitialized);
        } else {
            Log.e("VoiceEngine", "TTS initialization failed.");
        }
    });
}

public static void speak(String text) {
    speak(text, currentEmotion);
}

public static void speak(String text, String emotion) {
    if (!isInitialized) {
        Log.w("VoiceEngine", "TTS not initialized yet.");
        return;
    }

    float pitch = pitchMap.getOrDefault(emotion, 1.0f);
    float speed = speedMap.getOrDefault(emotion, 1.0f);

    tts.setPitch(pitch);
    tts.setSpeechRate(speed);
    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LunaraVoiceID");

    Log.i("VoiceEngine", "Speaking with emotion: " + emotion);
}

public static void setEmotion(String emotion) {
    if (pitchMap.containsKey(emotion)) {
        currentEmotion = emotion;
        Log.i("VoiceEngine", "Emotion changed to: " + emotion);
    } else {
        Log.w("VoiceEngine", "Unknown emotion: " + emotion);
    }
}

public static void upgradeVoiceStyle(String styleName) {
    if (!PermissionFlags.voiceLearningAllowed) {
        Log.w("VoiceEngine", "Voice upgrade attempt blocked by permissions.");
        return;
    }

    if (styleName.equalsIgnoreCase("rique approve code")) {
        Log.i("VoiceEngine", "Rique approved a custom voice style update.");
        // Here you could expand to downloading new voice packs, emotional improvements, etc.
    } else {
        Log.i("VoiceEngine", "Voice upgrade registered: " + styleName);
    }
}

public static void shutdown() {
    if (tts != null) {
        tts.stop();
        tts.shutdown();
        isInitialized = false;
        Log.i("VoiceEngine", "TTS safely shutdown.");
    }
}

public static boolean isReady() {
    return isInitialized;
}

}

