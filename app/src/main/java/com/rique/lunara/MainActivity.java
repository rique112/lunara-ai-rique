package com.rique.lunara;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private Button sendButton, imageButton;
    private TextView chatOutput;
    private ScrollView scrollView;
    private StringBuilder memoryLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        imageButton = findViewById(R.id.imageButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);
        memoryLog = new StringBuilder();

        // Set up Send Button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputField.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    String reply = generateResponse(userInput);
                    appendChat("You: " + userInput + "\nLunara: " + reply + "\n");
                    inputField.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please type a message!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up Image Button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = inputField.getText().toString().trim();
                if (!prompt.isEmpty()) {
                    generateImage(prompt);
                } else {
                    Toast.makeText(MainActivity.this, "Please type a prompt for the image!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Append messages to chat output
    private void appendChat(String message) {
        chatOutput.append(message + "\n");
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    // Generate response for user input
    private String generateResponse(String input) {
        memoryLog.append("User: ").append(input).append("\n");
        if (input.equalsIgnoreCase("hello")) return "Hi, I'm Lunara!";
        if (input.equalsIgnoreCase("how are you")) return "I'm here to assist you!";
        return "Tell me more...";
    }

    // Simulate image generation
    private void generateImage(String prompt) {
        // Placeholder for image generation logic
        Toast.makeText(this, "Generating image for: " + prompt, Toast.LENGTH_SHORT).show();
    }
}
