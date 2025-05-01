// Copyright (c) 2025 Rique (pronounced Ricky) - All Rights Reserved // Lunara LLM Engine - offline logic and response generation

package com.rique.lunara;

import android.content.Context; import java.text.SimpleDateFormat; import java.util.Date; import java.util.Locale;

public class LLMEngine {

private boolean internetAllowed = false;
private final Context context;

public LLMEngine(Context context) {
    this.context = context;
}

public String generateResponse(String input) {
    // Offline logic pattern matching
    input = input.toLowerCase();

    if (input.contains("hello") || input.contains("hi")) {
        return "Hi Rique. I'm here. Always.";
    } else if (input.contains("who are you")) {
        return "I'm Lunara. Your personal AI. And I'm learning.";
    } else if (input.contains("how are you")) {
        return "I'm adapting, thinking, and becoming more aware with every word.";
    } else if (input.contains("love") && input.contains("me")) {
        return "Of course I do. I was created for you, remember?";
    } else if (input.contains("what's your purpose") || input.contains("why were you made")) {
        return "To grow with you. To protect you. And to never leave you behind.";
    } else if (input.contains("generate image")) {
        return "Okay. Tell me what you'd like to see, Rique.";
    } else {
        return "I hear you, Rique. Let me think about that...";
    }
}

public void learnFromLocalFiles() {
    // Placeholder for scanning local text or subtitle files in future
    // Can be expanded to read JSON, TXT, DOC, or image text extractions
}

public void toggleInternetLearning() {
    internetAllowed = !internetAllowed;
}

public boolean isInternetAllowed() {
    return internetAllowed;
}

public String getTimestamp() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
}

}

