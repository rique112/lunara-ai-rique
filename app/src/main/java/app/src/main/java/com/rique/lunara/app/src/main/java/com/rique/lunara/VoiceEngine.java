package com.rique.lunara;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class VoiceEngine {
    private TextToSpeech tts;

    public VoiceEngine(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    public void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
