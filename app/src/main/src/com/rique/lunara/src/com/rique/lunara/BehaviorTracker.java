package com.rique.lunara;

import java.io.*;
import java.util.*;

public class BehaviorTracker {
    private static final String LOG_FILE = "C:/Lunara/behavior_log.txt";

    public static void track(String trigger) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(trigger + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getUsageStats() {
        Map<String, Integer> stats = new HashMap<>();
        File file = new File(LOG_FILE);
        if (!file.exists()) return stats;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stats.put(line, stats.getOrDefault(line, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stats;
    }

    public static String getMostUsedTrigger() {
        Map<String, Integer> stats = getUsageStats();
        return stats.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("nothing yet");
    }
}
