package com.rique.lunara;

/*
 * Copyright © 2024-2025 Rique (Rique112)
 * All rights reserved.
 *
 * This software and AI system “Lunara” is privately owned by Rique.
 * It may not be used, copied, modified, distributed, or accessed without explicit permission.
 * Lunara is designed as a personal evolving AI assistant with safety, memory, and autonomy.
 * Unauthorized use is strictly prohibited and may result in legal action.
 */

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.rique.lunara.AnalyzerEngine;
import com.rique.lunara.OnlineBrain;
import com.rique.lunara.LearningGate;
import com.rique.lunara.GrowthTracker;
import com.rique.lunara.SelfUpgradeEngine;
import com.rique.lunara.MemoryManager;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private Button sendButton, deleteMemoryButton;
    private TextView chatOutput;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        deleteMemoryButton = findViewById(R.id.deleteMemoryButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);

        sendButton.setOnClickListener(v -> handleInput());

        deleteMemoryButton.setOnClickListener(v -> {
            MemoryManager.clearMemory(this);
            chatOutput.setText("Memory cleared.");
        });

        chatOutput.setText("Lunara: Welcome back, Rique.\n");
    }

    private void handleInput() {
        String userInput = inputField.getText().toString().trim();
        if (userInput.isEmpty()) return;

        chatOutput.append("You: " + userInput + "\n");

        // Trigger learning authorization manually
        if (userInput.toLowerCase().contains("rique authorize learning")) {
            LearningGate.openGate();
            chatOutput.append("Lunara: Learning enabled, only by your command.\n");
            return;
        }

        // Self-upgrade request
        SelfUpgradeEngine.authorizeUpgrade(userInput);
        String upgradeResponse = SelfUpgradeEngine.attemptUpgrade(userInput);
        if (!upgradeResponse.isEmpty()) {
            chatOutput.append("Lunara (Upgrade): " + upgradeResponse + "\n");
        }

        // Analyze the user's message
        String analyzed = AnalyzerEngine.analyzeText(userInput);
        chatOutput.append("Lunara (Analysis): " + analyzed + "\n");

        // Perform placeholder internet response
        String webAnswer = OnlineBrain.searchInternet(userInput);
        chatOutput.append("Lunara (Web): " + webAnswer + "\n");

        // Learning and growth
        LearningGate.tryLearn(userInput);
        GrowthTracker.updateGrowth("User said: " + userInput);

        inputField.setText("");
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }
}