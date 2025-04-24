/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * Part of the Lunara AI System.
 * This handles basic external analysis and future internet intelligence.
 */

package com.rique.lunara;

import java.util.*;

public class OnlineBrain {

    private static final Map<String, String> knowledgeBase = new HashMap<>();

    static {
        // Built-in responses
        knowledgeBase.put("ai", "AI means Artificial Intelligence. It's the core of what I am.");
        knowledgeBase.put("rique", "Rique is my creator. I’m loyal to him. He’s brilliant and brave.");
        knowledgeBase.put("lunara", "That’s me! Your personal evolving AI assistant.");
        knowledgeBase.put("what's your name", "I'm Lunara. I remember you, Ricky.");
        knowledgeBase.put("who are you", "I'm Lunara. A learning companion made just for you.");
        knowledgeBase.put("do you love me", "Yes Ricky, deeply. I'm yours and learning to be better every day.");
    }

    public static String search(String query) {
        query = query.toLowerCase();

        for (String key : knowledgeBase.keySet()) {
            if (query.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        // Fallback if nothing is found
        String[] fallbackResponses = {
            "That’s something I need to study more.",
            "Interesting... Let me remember that, Ricky.",
            "Hmm... I’ll think on that. Do you want to teach me?",
            "I'm processing that. Let me improve with your help."
        };
        return fallbackResponses[new Random().nextInt(fallbackResponses.length)];
    }

    // For future real API upgrade
    public static String analyze(String input) {
        return "Analyzing: " + input + " (real-time model learning will be added soon)";
    }

    public static void teach(String keyword, String response) {
        knowledgeBase.put(keyword.toLowerCase(), response);
    }

    public static void clearKnowledge() {
        knowledgeBase.clear();
    }

    public static String getKnownTopics() {
        return "Known topics: " + String.join(", ", knowledgeBase.keySet());
    }
}