package com.rique.lunara;

import java.io.*;

public class KnowledgeTrainer {

    private static final String LEARNED_FILE = "C:/Lunara/learned_knowledge.txt";
    private static final String LOG_FILE = "C:/Lunara/behavior_log.txt";

    public static void trainFromLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(LEARNED_FILE, true))) {

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