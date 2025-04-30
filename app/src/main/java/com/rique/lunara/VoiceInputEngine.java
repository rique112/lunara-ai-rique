/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

VoiceInputEngine.java – Real-time voice listener for Lunara (Android only).

Listens via mic, processes into text, routes to brain. */


package com.rique.lunara;

import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.speech.RecognizerIntent; import android.speech.SpeechRecognizer; import android.speech.RecognitionListener; import android.util.Log;

import java.util.ArrayList;

public class VoiceInputEngine { private static SpeechRecognizer speechRecognizer; private static Intent speechIntent; private static boolean isListening = false;

public static void initialize(Activity activity) {
    if (!PermissionFlags.voiceLearningAllowed) return;

    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
    speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

    speechRecognizer.setRecognitionListener(new RecognitionListener() {
        @Override public void onReadyForSpeech(Bundle params) {}
        @Override public void onBeginningOfSpeech() {}
        @Override public void onRmsChanged(float rmsdB) {}
        @Override public void onBufferReceived(byte[] buffer) {}
        @Override public void onEndOfSpeech() {}
        @Override public void onError(int error) {
            Log.e("VoiceInput", "Error code: " + error);
            isListening = false;
        }
        @Override public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String input = matches.get(0);
                Log.d("VoiceInput", "Heard: " + input);
                String reply = LLMEngine.generateResponse(input);
                VoiceEngine.speak(reply);
                MemoryManager.save("[Voice] You: " + input + "\nLunara: " + reply);
            }
            isListening = false;
        }
        @Override public void onPartialResults(Bundle partialResults) {}
        @Override public void onEvent(int eventType, Bundle params) {}
    });
}

public static void startListening(Activity activity) {
    if (!PermissionFlags.voiceLearningAllowed || isListening) return;
    initialize(activity);
    speechRecognizer.startListening(speechIntent);
    isListening = true;
    Log.i("VoiceInput", "Listening started.");
}

public static void stopListening() {
    if (speechRecognizer != null && isListening) {
        speechRecognizer.stopListening();
        isListening = false;
        Log.i("VoiceInput", "Listening stopped.");
    }
}

public static boolean isActive() {
    return isListening;
}

}

