package com.rique.lunara;

/**
 * GrowthTracker monitors Lunara's development over time and adds adaptive behavior.
 */
public class GrowthTracker {
    private int totalLearned = 0;
    private int interactionCount = 0;

    public void logLearning() {
        totalLearned++;
    }

    public void logInteraction() {
        interactionCount++;
    }

    public String growthStatus() {
        if (totalLearned > 100 || interactionCount > 200) {
            return "Lunara has grown significantly. She's starting to develop patterns.";
        } else if (totalLearned > 50) {
            return "Lunara is learning fast. Her memory is expanding.";
        } else if (totalLearned > 10) {
            return "Lunara is starting to learn more from you.";
        } else {
            return "Lunara is in early development.";
        }
    }

    public void resetGrowth() {
        totalLearned = 0;
        interactionCount = 0;
    }
}