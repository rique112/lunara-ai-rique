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

        voiceEngine = new VoiceEngine(this);
        monaVoiceEngine = new MonaVoiceEngine(this);

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

        // Analyzer logic
        String analyzed = AnalyzerEngine.analyzeText(userInput);
        chatOutput.append("Lunara (Analysis): " + analyzed + "\n");

        // Learning
        LearningGate.tryLearn(userInput);
        GrowthTracker.updateGrowth("User said: " + userInput);

        // Memory check
        String learned = KnowledgeTrainer.lookupLearnedResponse(userInput);
        if (learned != null) {
            chatOutput.append("Lunara: " + learned + "\n");
            speak(learned);
            return;
        }

        // Generate new response
        String memory = MemoryManager.loadMemory(this);
        String evolved = BehaviorTracker.evolveResponse(memory, userInput);
        chatOutput.append("Lunara: " + evolved + "\n");

        MemoryManager.saveMemory(this, "You: " + userInput, "input");
        MemoryManager.saveMemory(this, "Lunara: " + evolved, "reply");

        speak(evolved);
        inputField.setText("");
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void speak(String text) {
        if (useMona) monaVoiceEngine.speak(text);
        else voiceEngine.speak(text);
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
            for (String line : lines) speak(line);
            return "(Fantasy scene started...)";
        }

        String memory = MemoryManager.loadMemory(this);
        return BehaviorTracker.evolveResponse(memory, input);
    }
}