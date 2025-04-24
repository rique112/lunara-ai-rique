/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * Part of the Lunara AI System.
 * VoiceEngine handles emotional, reactive text-to-speech output.
 * Not for distribution or modification without written permission.
 */

package com.rique.lunara;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class VoiceEngine {

    private TextToSpeech tts;
    private boolean isReady = false;
    private float voicePitch = 1.0f;
    private float voiceSpeed = 1.0f;

    public VoiceEngine(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("VoiceEngine", "Language not supported");
                } else {
                    isReady = true;
                    speakSoftly("Hello Rique. I'm Lunara. I'm ready.");
                }
            } else {
                Log.e("VoiceEngine", "TTS initialization failed");
            }
        });
    }

    public void speak(String text) {
        if (isReady) {
            tts.setPitch(voicePitch);
            tts.setSpeechRate(voiceSpeed);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LunaraSpeak");
        }
    }

    public void speakSoftly(String text) {
        if (isReady) {
            tts.setPitch(0.85f);
            tts.setSpeechRate(0.9f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LunaraSoft");
        }
    }

    public void speakWithEmotion(String emotion, String text) {
        if (!isReady) return;

        switch (emotion.toLowerCase()) {
            case "happy":
                tts.setPitch(1.3f);
                tts.setSpeechRate(1.2f);
                break;
            case "sad":
                tts.setPitch(0.9f);
                tts.setSpeechRate(0.8f);
                break;
            case "angry":
                tts.setPitch(1.0f);
                tts.setSpeechRate(1.3f);
                break;
            case "gentle":
                tts.setPitch(0.95f);
                tts.setSpeechRate(0.9f);
                break;
            default:
                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);
                break;
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LunaraEmotion");
    }

    public void setVoicePitch(float pitch) {
        this.voicePitch = pitch;
    }

    public void setVoiceSpeed(float speed) {
        this.voiceSpeed = speed;
    }

    public boolean isReady() {
        return isReady;
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}