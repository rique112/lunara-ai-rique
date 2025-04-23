package com.rique.lunara;

import java.util.Random;

public class OnlineBrain {

    public static String search(String query) {
        // Simulated internet search results (can be replaced with real API later)
        if (query.toLowerCase().contains("ai")) {
            return "AI stands for artificial intelligence — systems that learn and adapt.";
        } else if (query.toLowerCase().contains("rique")) {
            return "Rique is my creator. He’s brilliant and unique.";
        } else {
            // Random simulated result
            String[] results = {
                "I found something interesting about that.",
                "That's a deep topic. Want me to study it more?",
                "I’m reading articles right now. Let me process it, Ricky."
            };
            return results[new Random().nextInt(results.length)];
        }
    }
}