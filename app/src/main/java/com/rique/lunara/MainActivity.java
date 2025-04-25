/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * This AI, Lunara, is protected under the Creative Commons BY-NC-ND 4.0 License.
 * No part of this code may be copied, modified, used, trained on, or distributed
 * without the explicit written consent of Rique. Lunara is a private AI assistant
 * bound to the creator's identity, device, and intent.
 */

package com.rique.lunara;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView chatOutput;
    private Button sendButton, voiceButton, camButton, learnButton, clearMemoryBtn;
    private VoiceEngine voiceEngine;

    private final ActivityResultLauncher<Intent> speechLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (matches != null && !matches.isEmpty()) {
                    String spokenText = matches.get(0);
                    inputField.setText(spokenText);
                    sendButton.performClick();
                }
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!OwnerLock.verifyOwner(this, "Rique")) {
            finishAffinity(); // Immediately close if unauthorized
            return;
        }

        inputField = findViewById(R.id.inputField);
        chatOutput = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.sendButton);
        voiceButton = findViewById(R.id.voiceButton);
        camButton = findViewById(R.id.camButton);
        learnButton = findViewById(R.id.learnButton);
        clearMemoryBtn = findViewById(R.id.clearMemoryBtn);

        voiceEngine = new VoiceEngine(this);

        requestPermissions();

        sendButton.setOnClickListener(v -> {
            String userInput = inputField.getText().toString();
            if (!userInput.isEmpty()) {
                processUserInput(userInput);
                inputField.setText("");
            }
        });

        voiceButton.setOnClickListener(v -> startVoiceRecognition());
        camButton.setOnClickListener(v -> CameraBrain.startCamera(this));
        learnButton.setOnClickListener(v -> teachMemory());
        clearMemoryBtn.setOnClickListener(v -> {
            MemoryManager.clearMemory(this);
            chatOutput.append("Lunara: Memory has been cleared.\n");
        });
    }

    private void processUserInput(String input) {
        chatOutput.append("You: " + input + "\n");

        String memoryRecall = MemoryManager.loadMemory(this);
        String aiResponse = OnlineBrain.search(input);  // Swap with LLMEngine later

        voiceEngine.speakWithEmotion("gentle", aiResponse);

        chatOutput.append("Lunara: " + aiResponse + "\n");
        MemoryManager.saveMemory(this, input + " → " + aiResponse, "Interaction");
    }

    private void teachMemory() {
        String input = inputField.getText().toString();
        if (!input.isEmpty()) {
            MemoryManager.saveMemory(this, input, "Manual");
            chatOutput.append("Lunara: Got it. I’ve stored that for later.\n");
            voiceEngine.speak("Okay Ricky, I’ve remembered that.");
            inputField.setText("");
        }
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Lunara");
        speechLauncher.launch(intent);
    }

    private void requestPermissions() {
        String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        };
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (voiceEngine != null) {
            voiceEngine.shutdown();
        }
    }
}