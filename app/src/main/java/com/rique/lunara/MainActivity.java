package com.rique.lunara;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button sendButton;
    TextView chatOutput;
    ScrollView scrollView;
    Button deleteMemoryButton;

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
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);
        deleteMemoryButton = findViewById(R.id.deleteMemoryButton);

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

        String response = generateResponse(userInput);

        chatOutput.append("You: " + userInput + "\n");
        chatOutput.append("Lunara: " + response + "\n");

        scrollView.fullScroll(View.FOCUS_DOWN);
        inputField.setText("");

        MemoryManager.saveMemory(this, "You: " + userInput, "input");
        MemoryManager.saveMemory(this, "Lunara: " + response, "reply");

        if (useMona) monaVoiceEngine.speak(response);
        else voiceEngine.speak(response);
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
            return "I’ve forgotten everything, Ricky.";
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
                if (useMona) monaVoiceEngine.speak(line);
                else voiceEngine.speak(line);
            }
            return "(Fantasy scene started...)";
        }

        String memory = MemoryManager.loadMemory(this);
        String evolved = BehaviorTracker.evolveResponse(memory, input);
        return evolved;
    }
}
