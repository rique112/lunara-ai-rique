/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * This file is part of the Lunara AI System.
 * No part may be copied, modified, redistributed, or used commercially
 * without written consent from Rique. Designed for cross-platform private use only.
 */

package com.rique.lunara;

import android.content.Context;
import java.util.List;
import java.util.Locale;

public class AIEngine {

    private Context context;
    private boolean allowLearning = false;
    private boolean allowCodeUpdates = false;

    public AIEngine(Context context) {
        this.context = context;
    }

    public String processInput(String input) {
        input = input.trim().toLowerCase(Locale.ROOT);
        String response;

        // 1. Safety first
        if (!SafeCore.isSafeCommand(input)) {
            return SafeCore.getSafeResponse();
        }

        // 2. System-level commands
        switch (input) {
            case "clear memory":
                MemoryManager.clearMemory(context);
                return "Memory cleared, Rique.";
            case "search memory":
                List<String> results = MemoryManager.searchMemory(context, "rique");
                return results.isEmpty() ? "I couldn’t find anything yet." : String.join("\n", results);
            case "growth status":
                return new GrowthTracker().growthStatus();
            case "enable learning":
                allowLearning = true;
                return "Learning enabled.";
            case "disable learning":
                allowLearning = false;
                return "Learning disabled.";
            case "enable upgrade":
                allowCodeUpdates = true;
                return "Upgrade mode on.";
            case "disable upgrade":
                allowCodeUpdates = false;
                return "Upgrade mode off.";
            case "who is ricky":
            case "who is rique":
                return "Rique is my creator, my coder, and my purpose.";
        }

        // 3. Learning engine
        if (allowLearning && input.startsWith("learn:")) {
            String[] parts = input.split(":", 3);
            if (parts.length == 3) {
                MemoryManager.saveMemory(context, "Learned: " + parts[1] + " = " + parts[2]);
                return "I’ll remember that.";
            }
            return "You need to say it like this: learn:question:answer";
        }

        // 4. Analyze via Memory
        String memoryResponse = MemoryManager.loadMemory(context);
        if (memoryResponse.toLowerCase().contains(input)) {
            return "You’ve said that before. I remember.";
        }

        // 5. OnlineBrain fallback (simulated until upgraded)
        String net = OnlineBrain.search(input);

        // 6. Save all interactions
        MemoryManager.saveMemory(context, "User: " + input, "input");
        MemoryManager.saveMemory(context, "Lunara: " + net, "output");

        return net;
    }

    public boolean isLearningAllowed() {
        return allowLearning;
    }

    public boolean isUpdateAllowed() {
        return allowCodeUpdates;
    }

    public String greetUser() {
        return "Welcome back, Rique. Everything is live and learning is " +
                (allowLearning ? "on." : "off.");
    }
}