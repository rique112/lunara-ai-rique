package com.rique.lunara;

public class LearningGate {

    private static boolean learningEnabled = false;

    public static void toggleLearning(boolean on) {
        learningEnabled = on;
    }

    public static boolean isLearning() {
        return learningEnabled;
    }

    public static String processLearningCommand(String input) {
        if (input.toLowerCase().contains("learn this") || input.toLowerCase().contains("remember this")) {
            toggleLearning(true);
            return "Okay Rique, I’m listening. You can teach me now.";
        } else if (input.toLowerCase().contains("don’t learn this") || input.toLowerCase().contains("stop learning")) {
            toggleLearning(false);
            return "Got it. I won’t learn from this.";
        }
        return null;
    }
}