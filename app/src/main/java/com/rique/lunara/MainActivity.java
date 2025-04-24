/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * No part of this code may be copied, modified, or used without permission.
 */

package com.rique.lunara;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
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
    private Button sendButton, voiceButton, camButton;
    private TextToSpeech tts;

    private final ActivityResultLauncher<Intent> speechLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData()
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        String spokenText = matches.get(0);
                        inputField.setText(spokenText);
                        sendButton.performClick();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        chatOutput = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.sendButton);
        voiceButton = findViewById(R.id.voiceButton);
        camButton = findViewById(R.id.camButton);

        requestPermissions();

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(1.0f);
            }
        });

        sendButton.setOnClickListener(v -> {
            String userInput = inputField.getText().toString();
            if (!userInput.isEmpty()) {
                processUserInput(userInput);
                inputField.setText("");
            }
        });

        voiceButton.setOnClickListener(v -> startVoiceRecognition());
        camButton.setOnClickListener(v -> CameraBrain.startCamera(MainActivity.this));
    }

    private void processUserInput(String input) {
        chatOutput.append("You: " + input + "\n");

        String memoryAnswer = MemoryManager.loadMemory(this);
        String onlineAnswer = OnlineBrain.search(input);
        String reply = onlineAnswer + "\n\n(Recalling memory...)\n" + memoryAnswer;

        chatOutput.append("Lunara: " + reply + "\n");
        tts.speak(onlineAnswer, TextToSpeech.QUEUE_FLUSH, null, null);

        MemoryManager.saveMemory(this, input + " → " + onlineAnswer, "Interaction");
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}