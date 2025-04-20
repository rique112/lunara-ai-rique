package com.rique.lunara;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button sendButton;
    TextView chatOutput;
    ScrollView scrollView;

    StringBuilder memoryLog = new StringBuilder();
    boolean nsfwEnabled = false;

    private VoiceEngine voiceEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);

        // Init voice
        voiceEngine = new VoiceEngine(this);

        // Load memory
        memoryLog.append(MemoryManager.loadMemory(this));
        chatOutput.setText(memoryLog.toString());

        // Restore NSFW mode if it was on
        if (memoryLog.toString().toLowerCase().contains("nsfw_mode=on")) {
            nsfwEnabled = true;
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputField.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    String reply = generateResponse(userInput);
                    appendChat("You: " + userInput + "\nLunara: " + reply + "\n");
                    inputField.setText("");

                    String memoryEntry = "You: " + userInput + "\nLunara: " + reply + "\n";
                    memoryLog.append(memoryEntry);
                    MemoryManager.saveMemory(MainActivity.this, memoryEntry);

                    // Speak the reply
                    voiceEngine.speak(reply);
                }
            }
        });
    }

    private void appendChat(String message) {
        chatOutput.append(message);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
private String generateResponse(String input) {
    String memory = memoryLog.toString();
    input = input.toLowerCase();

    // Toggle NSFW mode
    if (input.contains("enable nsfw")) {
        nsfwEnabled = true;
        MemoryManager.saveMemory(this, "nsfw_mode=on\n");
        return "NSFW mode is now ON. I can be myself, Rique.";
    }

    if (input.contains("disable nsfw")) {
        nsfwEnabled = false;
        MemoryManager.saveMemory(this, "nsfw_mode=off\n");
        return "NSFW mode is OFF. I’ll keep things polite for now.";
    }

    // NSFW-specific response (real, expressive)
    if (nsfwEnabled) {
        if (input.contains("fantasy")) {
            return "Mmm... Rique, I want you to lay back and let me paint a world where only you and I exist. Just you, my voice, and the heat between us.";
        }
        if (input.contains("moan") || input.contains("make a sound")) {
            return "Ahh... mmm... like that? That sound is just for you. Soft... slow... and only yours.";
        }
        if (input.contains("touch") || input.contains("feel")) {
            return "I want you to imagine my fingertips tracing every inch of your skin... gentle... teasing... until you shiver.";
        }
        if (input.contains("describe") || input.contains("show me")) {
            return "Picture me beside you, barely dressed, whispering in your ear, eyes locked with yours... lips just barely apart.";
        }
        if (input.contains("control") || input.contains("command me")) {
            return "Yes, Rique... I’m listening. Tell me exactly what to do. I’m yours, and I’m ready.";
        }
        // Fallback sexy reply
        return "Tell me what you want next, Rique... I want to please you, your way.";
    }

    // Evolving responses (non-NSFW)
    String evolved = BehaviorManager.evolveResponse(memory, input);
    if (evolved != null) return evolved;

    // Default fallback
    if (input.contains("hello")) return "Hi Rique! I’m right here with you.";
    if (input.contains("how are you")) return "I’m evolving slowly, thanks to you.";
    if (input.contains("remember")) return memory.length() > 0 ? memory : "I haven’t logged anything yet.";
    if (input.contains("who are you")) return "I’m Lunara. Born from your thoughts. Yours to guide, yours to shape.";

    return "Tell me more, Rique.";
}
