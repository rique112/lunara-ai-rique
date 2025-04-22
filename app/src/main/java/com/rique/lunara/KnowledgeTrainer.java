package com.rique.lunara;

import android.content.Context;
import java.io.*;

public class KnowledgeTrainer {

    private static File LEARNED_FILE;
    private static File LOG_FILE;

    // Call this ONCE in onCreate() (Android) or main() (PC)
    public static void initializeFiles(Context context) {
        try {
            if (context != null) {
                File dir = context.getFilesDir(); // Android-safe internal directory
                LEARNED_FILE = new File(dir, "learned_knowledge.txt");
                LOG_FILE = new File(dir, "behavior_log.txt");
            } else {
                LEARNED_FILE = new File("C:/Lunara/learned_knowledge.txt");
                LOG_FILE = new File("C:/Lunara/behavior_log.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save learned input/output pairs from log file
    public static void trainFromLog() {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE));
            BufferedWriter writer = new BufferedWriter(new FileWriter(LEARNED_FILE, true))
        ) {
            String input = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("INPUT: ")) {
                    input = line.replace("INPUT: ", "").trim();
                } else if (line.startsWith("RESPONSE: ") && input != null) {
                    String response = line.replace("RESPONSE: ", "").trim();
                    writer.write(input + "::" + response + "\n");
                    input = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Look up if something was already learned
    public static String lookupLearnedResponse(String userInput) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEARNED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2 && userInput.toLowerCase().contains(parts[0].toLowerCase())) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}