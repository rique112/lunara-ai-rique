package com.rique.lunara;

import android.content.Context;
import android.util.Log;

public class AIEngine {

    private Context context;

    public AIEngine(Context context) {
        this.context = context;
    }

    public String processInput(String input) {
        // Basic starter logic (will evolve)
        input = input.trim().toLowerCase();

        String response;

        if (input.contains("hello")) {
            response = "Hi Rique! I’m happy to talk to you.";
        } else if (input.contains("how are you")) {
            response = "I’m doing great now that you’re here.";
        } else if (input.contains("remember")) {
            response = "Yes, I remember everything you’ve said to me.";
        } else {
            response = "Hmm... I’m thinking about that. Want to talk more?";
        }

        // Save to memory
        MemoryManager.saveMemory(context, "User: " + input);
        MemoryManager.saveMemory(context, "Lunara: " + response);

        return response;
    }

    public String greetUser() {
        return "Welcome back, Rique. I missed you.";
    }
}
