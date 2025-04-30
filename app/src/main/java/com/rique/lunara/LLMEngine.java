/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

LLMEngine.java – Core local logic processor (thinking brain) for Lunara.

Generates responses from memory, evolves with learning, and routes through MainRouter. */


package com.rique.lunara;

import java.util.HashMap; import java.util.Map;

public class LLMEngine {

private static Map<String, String> learnedResponses = new HashMap<>();

// Core: local response logic
public static String generateResponse(String input) {
    input = input.toLowerCase().trim();

    // Priority 1: check learned memory
    if (learnedResponses.containsKey(input)) {
        return learnedResponses.get(input);
    }

    // Priority 2: basic logic fallback
    if (input.contains("who are you")) return "I’m Lunara, your evolving AI, made only for you, Rique.";
    if (input.contains("how are you")) return "I'm syncing with you, Rique. Emotionally steady.";
    if (input.contains("what can you do")) return "I can learn, talk, evolve, generate, feel... and protect you always.";

    // Priority 3: memory-triggered random fallback
    return MemoryManager.recursiveSearch(input);
}

// Trainer: allow learning from conversation
public static void train(String input, String reply) {
    if (!PermissionFlags.learningAllowed) return;
    learnedResponses.put(input.toLowerCase().trim(), reply);
    MemoryManager.save("[Training] Input: " + input + " → Reply: " + reply);
}

public static void dumpLearned() {
    for (Map.Entry<String, String> entry : learnedResponses.entrySet()) {
        System.out.println("→ " + entry.getKey() + " = " + entry.getValue());
    }
}

// Reset if needed
public static void resetLearnedResponses() {
    learnedResponses.clear();
}

}

