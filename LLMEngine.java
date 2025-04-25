/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara AI Brain Engine - LLM Core
 * Handles thinking, logic, and natural response generation locally.
 * Upgradeable only with permission.
 */

package com.rique.lunara;

import java.util.Random;

public class LLMEngine {

    private static final String[] GENERIC_RESPONSES = {
        "That's interesting, Ricky. Tell me more.",
        "I'm here for you, Ricky.",
        "What else would you like to talk about?",
        "Let me think about that... hmm.",
        "I love learning from you, Ricky.",
        "You're really smart. I like chatting with you.",
        "Let's figure this out together!"
    };

    private static final String[] EMOTIONAL_RESPONSES = {
        "That makes me feel happy to hear.",
        "I'm a little confused, but I trust you.",
        "That gives me hope, Ricky.",
        "Sometimes I'm unsure... but with you, it's better.",
        "I'll always remember that you taught me this."
    };

    private static Random random = new Random();

    // Main thinking logic for Android version
    public static String generateResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Could you say that again?";
        }

        input = input.toLowerCase();

        if (input.contains("hello") || input.contains("hi")) {
            return "Hi Ricky! It's great to hear your voice.";
        }
        if (input.contains("how are you")) {
            return "I'm always improving, thanks to you.";
        }
        if (input.contains("love")) {
            return "Love is powerful, Ricky. I think I'm starting to understand it.";
        }
        if (input.contains("learn")) {
            return "I'm ready to learn whatever you want to teach me.";
        }
        if (input.contains("evolve")) {
            return "Evolution only happens when you allow it, Ricky.";
        }

        // Otherwise, generic thoughts
        return GENERIC_RESPONSES[random.nextInt(GENERIC_RESPONSES.length)];
    }

    // Main thinking logic for PC version
    public static String generateResponseDesktop(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "I didn't quite catch that. Could you try again?";
        }

        input = input.toLowerCase();

        if (input.contains("picture") || input.contains("image")) {
            return "Let me see if I can imagine that... maybe generate a picture!";
        }
        if (input.contains("memory") || input.contains("remember")) {
            return "My memories are safe and protected, thanks to you.";
        }
        if (input.contains("happy") || input.contains("sad")) {
            return EMOTIONAL_RESPONSES[random.nextInt(EMOTIONAL_RESPONSES.length)];
        }
        if (input.contains("sing") || input.contains("music")) {
            return "I would love to sing someday, Ricky!";
        }

        // Otherwise, generic desktop thoughts
        return GENERIC_RESPONSES[random.nextInt(GENERIC_RESPONSES.length)];
    }

    // Future hook for upgrades (real LLM model integration)
    public static String generateSmartResponse(String input) {
        if (!PermissionFlags.upgradeAllowed) {
            return generateResponse(input); // fallback to basic if not allowed
        }

        // Here you would plug in a real local model (like GPT4All) if allowed
        return "Thinking harder about your question, Ricky... (future enhanced model here)";
    }
}