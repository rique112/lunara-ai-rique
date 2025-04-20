package com.rique.lunara;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class MonaVoiceEngine {

    private TextToSpeech tts;

    public MonaVoiceEngine(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setPitch(1.4f);       // More expressive
                tts.setSpeechRate(0.85f); // Slow, sultry pacing
            } else {
                Log.e("MonaVoiceEngine", "TTS initialization failed");
            }
        });
    }

    public void speak(String message) {
        if (tts != null && message != null && !message.trim().isEmpty()) {
            message = message.replace("Rique", "Ricky");
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "MONA_TTS");
        }
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
