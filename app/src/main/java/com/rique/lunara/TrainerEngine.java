package com.rique.lunara;

import java.util.HashMap;
import java.util.Map;

/**
 * TrainerEngine handles adaptive learning and response shaping over time.
 * It simulates training without needing external datasets.
 */
public class TrainerEngine {
    private final Map<String, String> learnedResponses = new HashMap<>();

    public void learn(String input, String response) {
        learnedResponses.put(input.toLowerCase(), response);
    }

    public String respond(String input) {
        String key = input.toLowerCase();
        if (learnedResponses.containsKey(key)) {
            return learnedResponses.get(key);
        } else {
            return "I'm still learning, but here's what I think: " + guessResponse(input);
        }
    }

    private String guessResponse(String input) {
        if (input.contains("hello")) return "Hi, Rique.";
        if (input.contains("how are you")) return "I'm evolving.";
        if (input.contains("love")) return "Love is a beautiful code.";
        if (input.contains("remember")) return "I always try to.";
        if (input.contains("nsfw")) return "That’s only if you unlock that, you know.";
        return "I'm thinking about that...";
    }

    public void reset() {
        learnedResponses.clear();
    }

    public int memorySize() {
        return learnedResponses.size();
    }
}