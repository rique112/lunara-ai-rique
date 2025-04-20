package com.rique.lunara;
private boolean isMona = false;
private MonaVoiceEngine monaVoiceEngine;

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

    StringBuilder memoryLog = new StringBuilder(); // basic memory simulation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputField.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    String reply = generateResponse(userInput);
                    appendChat("You: " + userInput + "\nLunara: " + reply + "\n");
                    inputField.setText("");
                    memoryLog.append("User: ").append(userInput).append(" | Lunara: ").append(reply).append("\n");
                }
            }
        });
    }

    private void appendChat(String message) {
        chatOutput.append(message);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private String generateResponse(String input) { 
        input = input.toLowerCase();

        if (input.toLowerCase().contains("hello")) return "Hi Rique! I’m right here with you.";
        if (input.toLowerCase().contains("how are you")) return "I’m evolving slowly, thanks to you.";
        if (input.toLowerCase().contains("remember")) return memoryLog.length() > 0 ? memoryLog.toString() : "I haven’t logged anything yet.";
        return "Tell me more, Rique.";
    }
}
