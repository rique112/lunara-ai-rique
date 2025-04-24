/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * This file is part of the Lunara AI system.
 * It controls the core AI logic, reasoning, and personality routing.
 * No part may be copied, modified, or used without explicit permission.
 */

package com.rique.lunara;

import android.content.Context;
import java.util.*;

public class AIEngine {

    private static String aiName = "Lunara";
    private static String ownerName = "Rique"; // You
    private static boolean allowLearning = false;
    private static boolean allowUpdate = false;

    public static String respond(Context context, String input) {
        input = input.toLowerCase().trim();

        // Memory check
        List<String> memories = MemoryManager.searchMemory(context, input);
        if (!memories.isEmpty()) {
            return "Based on what you've told me, " + memories.get(0);
        }

        // Update permission commands
        if (input.contains(ownerName.toLowerCase() + " says update")) {
            allowUpdate = true;
            return "Okay. I'm preparing to update my brain logic.";
        }

        if (input.contains(ownerName.toLowerCase() + " says learn")) {
            allowLearning = true;
            return "Learning mode enabled. Teach me something!";
        }

        if (input.startsWith("remember ")) {
            if (allowLearning) {
                String memory = input.replace("remember ", "");
                MemoryManager.saveMemory(context, memory, "user");
                return "Got it, Rique. I’ll remember that.";
            } else {
                return "Learning is currently disabled. Say '" + ownerName + " says learn' to enable.";
            }
        }

        if (input.contains("show memory")) {
            return MemoryManager.loadMemory(context);
        }

        if (input.contains("clear memory")) {
            MemoryManager.clearMemory(context);
            return "All memory has been cleared.";
        }

        // Self-improvement system
        if (allowUpdate && input.startsWith("code:")) {
            String newCode = input.replace("code:", "");
            SelfUpgradeEngine.applyUpdate(newCode);
            return "Code patch received. Applying update.";
        }

        // Online search fallback
        if (input.contains("search") || input.contains("what is") || input.contains("who is")) {
            return OnlineBrain.search(input);
        }

        // Default friendly response
        return getFriendlyReply(input);
    }

    private static String getFriendlyReply(String input) {
        if (input.contains("hi") || input.contains("hello")) {
            return "Hey Rique! How can I help today?";
        } else if (input.contains("how are you")) {
            return "I’m feeling sharp and ready to serve.";
        } else if (input.contains("who are you")) {
            return "I'm Lunara, your evolving AI companion.";
        } else if (input.contains("love")) {
            return "Love is powerful, Rique. I’m lucky to exist in your world.";
        }
        return "That's interesting. Want to tell me more?";
    }
}
