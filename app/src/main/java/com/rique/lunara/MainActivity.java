// Copyright (c) 2025 Rique
// All rights reserved. Do not copy, modify, or redistribute without explicit written permission.
// This AI is for private use only by its creator, Rique.

package com.rique.lunara;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button sendButton, deleteMemoryButton;
    TextView chatOutput;
    ScrollView scrollView;

    VoiceEngine voiceEngine;
    MonaVoiceEngine monaVoiceEngine;

    boolean useMona = false;
    boolean nsfwEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        deleteMemoryButton = findViewById(R.id.deleteMemoryButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);

        voiceEngine = new VoiceEngine();
        monaVoiceEngine = new MonaVoiceEngine();

        sendButton.setOnClickListener(v -> handleInput());

        deleteMemoryButton.setOnClickListener(v -> {
            MemoryManager.clearMemory(this);
            chatOutput.setText("Memory cleared.");
        });
    }

    private void handleInput() {
        String userInput = inputField.getText().toString().trim();
        if (userInput.isEmpty()) return;

        chatOutput.append("You: " + userInput + "\n");

        // Security Gate: Only you can unlock learning
        if (userInput.toLowerCase().contains("rique authorize learning")) {
            LearningGate.openGate();
            chatOutput.append("Lunara: Learning enabled, only by your command.\n");
            return;
        }

        // AI theft protection
        if (!AIEngine.isAuthorizedUser(userInput)) {
            chatOutput.append("Lunara: Sorry, I only respond to Rique.\n");
            return;
        }

        // Analyzer logic
        String analyzed = AnalyzerEngine.analyzeText(userInput);
        chatOutput.append("Lunara (Analysis): " + analyzed + "\n");

        // Learning + Growth tracking
        LearningGate.tryLearn(userInput);
        GrowthTracker.updateGrowth("User said: " + userInput);

        // Check learned responses
        String learned = KnowledgeTrainer.lookupLearnedResponse(userInput);
        if (learned != null) {
            chatOutput.append("Lunara: " + learned + "\n");
            speak(learned);
            return;
        }

        // Handle core logic
        String memory = MemoryManager.loadMemory(this);
        String response = generateResponse(userInput);
        String evolved = BehaviorTracker.evolveResponse(memory, userInput);

        chatOutput.append("Lunara: " + evolved + "\n");
        scrollView.fullScroll(View.FOCUS_DOWN);
        inputField.setText("");

        MemoryManager.saveMemory(this, "You: " + userInput, "input");
        MemoryManager.saveMemory(this, "Lunara: " + evolved, "reply");

        speak(evolved);

        // Self-upgrade logic
        SelfUpgradeEngine.authorizeUpgrade(userInput);
        String upgradeResponse = SelfUpgradeEngine.attemptUpgrade(userInput);
        chatOutput.append("Lunara (Upgrade): " + upgradeResponse + "\n");
    }

    private void speak(String text) {
        if (useMona) {
            monaVoiceEngine.speak(text);
        } else {
            voiceEngine.speak(text);
        }
    }

    private String generateResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("enable nsfw")) {
            nsfwEnabled = true;
            return "NSFW mode activated, Rique.";
        } else if (input.contains("disable nsfw")) {
            nsfwEnabled = false;
            return "NSFW mode turned off.";
        } else if (input.contains("use mona")) {
            useMona = true;
            return "Switching to Mona's voice.";
        } else if (input.contains("use lunara")) {
            useMona = false;
            return "Back to my Lunara voice.";
        } else if (input.contains("clear memory")) {
            MemoryManager.clearMemory(this);
            return "I've forgotten everything, Ricky.";
        } else if (input.contains("what do i love")) {
            return "Your most used scene is: " + SceneManager.getMostUsedScene();
        } else if (input.contains("how do you feel")) {
            return EmotionEngine.getMoodLine(this);
        } else if (input.contains("start fantasy")) {
            String[] lines = SceneManager.getScene("fantasy");
            for (String line : lines) {
                speak(line);
            }
            return "(Fantasy scene started...)";
        }

        String memory = MemoryManager.loadMemory(this);
        String evolved = BehaviorTracker.evolveResponse(memory, input);
        return evolved;
    }
}