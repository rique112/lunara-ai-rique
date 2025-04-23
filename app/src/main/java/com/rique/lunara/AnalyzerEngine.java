package com.rique.lunara;

public class AnalyzerEngine {
    public static String analyzeText(String input) {
        // Simple simulated analysis
        if (input.toLowerCase().contains("rique")) {
            return "You’re talking about yourself again. I love that.";
        } else if (input.toLowerCase().contains("learn")) {
            return "Should I remember this for later?";
        } else {
            return "Interesting... tell me more about it, Ricky.";
        }
    }
}