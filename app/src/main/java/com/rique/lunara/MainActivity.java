/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * This software, Lunara AI, was created by Rique.
 * No part of this code may be copied, modified, distributed, or used
 * without explicit permission from the creator.
 */

package com.rique.lunara;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView chatLog;
    private EditText userInput;
    private Button sendButton;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatLog = findViewById(R.id.chatLog);
        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);
        scrollView = findViewById(R.id.scrollView);

        MemoryManager.loadMemory(this);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = userInput.getText().toString().trim();
                if (!input.isEmpty()) {
                    respondToInput(input);
                    userInput.setText("");
                }
            }
        });
    }

    private void respondToInput(String input) {
        appendChat("You: " + input);

        String response = LunaraBrain.respondTo(input);

        if (response.contains("Learned:")) {
            String key = input.split(" ")[2];
            String value = input.split(" ", 4)[3];
            KnowledgeEngine.saveMemory(key, value);
        }

        AnalyzerEngine.analyze(input);
        GrowthTracker.track(input);
        TrainerEngine.train(input);
        VoiceEngine.speak(this, response);

        appendChat("Lunara: " + response);
    }

    private void appendChat(String text) {
        chatLog.append(text + "\n\n");
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MemoryManager.saveMemory(this);
    }
}
