/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * 
 * This file is part of the Lunara AI system.
 * No part of this code may be copied, modified, distributed, or used
 * without explicit permission from the creator.
 */

package com.rique.lunara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView chatOutput;
    private Button sendButton, voiceButton, clearMemoryButton, enableLearningButton, disableLearningButton;
    private TextToSpeech tts;

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private boolean learningEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        chatOutput = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.sendButton);
        voiceButton = findViewById(R.id.voiceButton);
        clearMemoryButton = findViewById(R.id.clearMemoryButton);
        enableLearningButton = findViewById(R.id.enableLearningButton);
        disableLearningButton = findViewById(R.id.disableLearningButton);

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                speak("Lunara is awake, Rique.");
            }
        });

        sendButton.setOnClickListener(v -> processInput());

        voiceButton.setOnClickListener(v -> startVoiceInput());

        clearMemoryButton.setOnClickListener(v -> {
            MemoryManager.clearMemory(this);
            speak("All memory has been erased.");
            chatOutput.append("Lunara: Memory cleared.\n");
        });

        enableLearningButton.setOnClickListener(v -> {
            learningEnabled = true;
            speak("Learning mode is now on.");
            chatOutput.append("Lunara: I will now learn what you teach me, Rique.\n");
        });

        disableLearningButton.setOnClickListener(v -> {
            learningEnabled = false;
            speak("Learning mode is now off.");
            chatOutput.append("Lunara: I will not learn anything new unless told otherwise.\n");
        });
    }

    private void processInput() {
        String userInput = inputField.getText().toString().trim();
        if (userInput.isEmpty()) return;

        chatOutput.append("You: " + userInput + "\n");

        // Handle custom commands
        if (learningEnabled && userInput.toLowerCase().startsWith("rique says learn ")) {
            String[] parts = userInput.substring(17).split("=", 2);
            if (parts.length == 2) {
                MemoryManager.teach(parts[0].trim(), parts[1].trim());
                speak("Got it. I’ve learned: " + parts[0].trim());
                chatOutput.append("Lunara: I will remember that, Rique.\n");
                return;
            }
        }

        // Get response
        String response = MemoryManager.getResponse(userInput);
        chatOutput.append("Lunara: " + response + "\n");
        speak(response);

        // Save interaction
        MemoryManager.saveMemory(this, userInput, "Rique");
        MemoryManager.saveMemory(this, response, "Lunara");

        // Trim memory to avoid overflow
        MemoryManager.checkMemoryLimit(this, 500);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Lunara...");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Voice input not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                inputField.setText(result.get(0));
                processInput();
            }
        }
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
