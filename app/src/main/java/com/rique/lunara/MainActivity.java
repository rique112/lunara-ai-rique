/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * This file is part of the Lunara AI System.
 * Unauthorized use, copy, or modification is prohibited.
 */

package com.rique.lunara;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.RecognizerIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.*;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView chatOutput;
    private Button sendButton, voiceButton;
    private TextToSpeech tts;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private static final int REQUEST_MIC_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        chatOutput = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.sendButton);
        voiceButton = findViewById(R.id.voiceButton);

        // Request microphone permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_MIC_PERMISSION);
        }

        // Text-to-Speech setup
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        // Send typed input
        sendButton.setOnClickListener(v -> {
            String userInput = inputField.getText().toString().trim();
            if (!userInput.isEmpty()) {
                chatOutput.append("You: " + userInput + "\n");
                handleInput(userInput);
                inputField.setText("");
            }
        });

        // Voice input
        voiceButton.setOnClickListener(v -> startVoiceInput());
    }

    private void handleInput(String input) {
        String response;

        if (input.toLowerCase().startsWith("rique says learn this:")) {
            String info = input.substring("rique says learn this:".length()).trim();
            MemoryManager.saveMemory(this, info, "learned");
            response = "Got it, Rique. I’ve learned: " + info;
        } else if (input.equalsIgnoreCase("rique says forget everything")) {
            MemoryManager.clearMemory(this);
            response = "All memory cleared as requested.";
        } else if (input.toLowerCase().startsWith("rique says search:")) {
            String query = input.substring("rique says search:".length()).trim();
            response = OnlineBrain.search(query);
        } else if (input.equalsIgnoreCase("rique says update")) {
            response = SelfUpgradeEngine.update(this);
        } else if (input.equalsIgnoreCase("rique says evolve")) {
            response = GrowthTracker.evolve();
        } else if (input.toLowerCase().startsWith("rique says analyze this:")) {
            String data = input.substring("rique says analyze this:".length()).trim();
            response = AnalyzerEngine.analyze(data);
        } else if (input.equalsIgnoreCase("rique says show memory")) {
            response = MemoryManager.loadMemory(this);
        } else {
            response = MemoryManager.getResponse(this, input);
        }

        chatOutput.append("Lunara: " + response + "\n");
        tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Lunara...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Voice input not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                inputField.setText(spokenText);
                sendButton.performClick();
            }
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
