/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara Voice System - Smart Voice Manager
 * This file is part of Lunara AI and may not be copied, altered, or reused without permission.
 */

package com.rique.lunara;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class VoiceEngine {

    private TextToSpeech tts;
    private boolean ready = false;
    private static boolean usePiper = true;  // Default to using Piper if available

    private final Context context;

    public VoiceEngine(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                ready = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED;
                if (!ready) Log.e("VoiceEngine", "TTS language not supported or missing");
            } else {
                Log.e("VoiceEngine", "TTS Initialization failed");
            }
        });
    }

    public void speak(String text) {
        if (usePiper) {
            PiperVoiceBridge.speakOffline(context, text, "normal");
            return;
        }

        if (!ready) return;
        tts.setPitch(1.0f);
        tts.setSpeechRate(1.0f);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LUNARA_SPEAK");
    }

    public void speakWithEmotion(String emotion, String text) {
        if (usePiper) {
            PiperVoiceBridge.speakOffline(context, text, emotion);
            return;
        }

        if (!ready) return;

        float pitch = 1.0f;
        float rate = 1.0f;

        switch (emotion.toLowerCase()) {
            case "gentle":
                pitch = 1.1f;
                rate = 0.9f;
                break;
            case "excited":
                pitch = 1.4f;
                rate = 1.3f;
                break;
            case "sad":
                pitch = 0.8f;
                rate = 0.8f;
                break;
            case "angry":
                pitch = 0.9f;
                rate = 1.1f;
                break;
            case "slow":
                pitch = 0.9f;
                rate = 0.6f;
                break;
            case "fast":
                pitch = 1.2f;
                rate = 1.5f;
                break;
            case "seductive":
                pitch = 0.95f;
                rate = 0.85f;
                break;
            default:
                pitch = 1.0f;
                rate = 1.0f;
                break;
        }

        tts.setPitch(pitch);
        tts.setSpeechRate(rate);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LUNARA_SPEAK_EMOTIONAL");
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        PiperVoiceBridge.shutdown();
    }

    // Future: Lunara can evolve and switch voices automatically
    public static void upgradeVoiceSettings(boolean enablePiper) {
        usePiper = enablePiper;
    }
}