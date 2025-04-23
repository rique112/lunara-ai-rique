package com.rique.lunara;

/*
 * Copyright © 2024–2025 Rique (Rique112)
 * All rights reserved.
 *
 * This software and AI system “Lunara” is privately owned by Rique.
 * It may not be used, copied, modified, distributed, or accessed without explicit permission.
 * Lunara is designed as a personal evolving AI assistant with custom safety, memory, and autonomy.
 * Unauthorized use is strictly prohibited and may result in legal action.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.rique.lunara.AnalyzerEngine;
import com.rique.lunara.AIEngine;
import com.rique.lunara.BehaviorTracker;
import com.rique.lunara.EmotionEngine;
import com.rique.lunara.GrowthTracker;
import com.rique.lunara.KnowledgeTrainer;
import com.rique.lunara.LearningGate;
import com.rique.lunara.MemoryManager;
import com.rique.lunara.SceneManager;
import com.rique.lunara.SelfUpgradeEngine;
import com.rique.lunara.VoiceEngine;
import com.rique.lunara.MonaVoiceEngine;

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

        // Upgrade gate
        SelfUpgradeEngine.authorizeUpgrade(userInput);
        String upgradeResponse = SelfUpgradeEngine.attemptUpgrade(userInput);
        chatOutput.append("Lunara (Upgrade): " + upgradeResponse + "\n");

        // Analyzer logic
        String analyzed = AnalyzerEngine.analyzeText(userInput);
        chatOutput.append("Lunara (Analysis): " + analyzed + "\n");

        // Check if it's a learning command
        String learningResponse = LearningGate.processLearningCommand(userInput);
        if (learningResponse != null) {
            chatOutput.append("Lunara: " + learningResponse + "\n");
            return;
        }

        // Check for pre-learned knowledge
        String learned = KnowledgeTrainer.lookupLearnedResponse(userInput);
        if (learned != null) {
            chatOutput.append("Lunara: " + learned + "\n");
            speak(learned);
            return;
        }

        // Generate + evolve response
        String response = generateResponse(userInput);
        String memory = MemoryManager.loadMemory(this);
        String evolved = BehaviorTracker.evolveResponse(memory, userInput);

        if (LearningGate.isLearning()) {
            BehaviorTracker.track("INPUT: " + userInput);
            BehaviorTracker.track("RESPONSE: " + evolved);
        }

        chatOutput.append("Lunara: " + evolved + "\n");
        scrollView.fullScroll(View.FOCUS_DOWN);
        inputField.setText("");

        MemoryManager.saveMemory(this, "You: " + userInput, "input");
        MemoryManager.saveMemory(this, "Lunara: " + evolved, "reply");

        speak(evolved);
        GrowthTracker.updateGrowth("User said: " + userInput);
    }

    private void speak(String message) {
        if (useMona) monaVoiceEngine.speak(message);
        else voiceEngine.speak(message);
    }

    private String generateResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("enable nsfw")) {
            nsfwEnabled = true;
            return "NSFW mode activated, Rique.";
        }

        if (input.contains("disable nsfw")) {
            nsfwEnabled = false;
            return "NSFW mode turned off.";
        }

        if (input.contains("use mona")) {
            useMona = true;
            return "Switching to Mona's voice.";
        }

        if (input.contains("use lunara")) {
            useMona = false;
            return "Back to my Lunara voice.";
        }

        if (input.contains("clear memory")) {
            MemoryManager.clearMemory(this);
            return "I've forgotten everything, Ricky.";
        }

        if (input.contains("what do i love")) {
            return "Your most used scene is: " + SceneManager.getMostUsedScene();
        }

        if (input.contains("how do you feel")) {
            return EmotionEngine.getMoodLine(this);
        }

        if (input.contains("start fantasy")) {
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