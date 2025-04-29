package com.rique.lunaraai.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * EvolutionCore - Allows Lunara to read memories, analyze patterns,
 * and improve herself over time based on past experiences.
 */

public class EvolutionCore {

    private static final String MEMORY_FOLDER = "LunaraMemory";
    private static final String ANALYSIS_FOLDER = "LunaraAnalysisReports";
    private static final String IMPROVEMENT_FOLDER = "LunaraEvolutionPlans";

    /**
     * Starts the evolution learning process
     */
    public static void evolve() {
        try {
            List<String> memories = readAllFiles(MEMORY_FOLDER);
            List<String> reports = readAllFiles(ANALYSIS_FOLDER);

            if (memories.isEmpty() && reports.isEmpty()) {
                System.out.println("EvolutionCore: No memories or analysis to learn from yet.");
                return;
            }

            StringBuilder evolutionPlan = new StringBuilder();
            evolutionPlan.append("=== Lunara Evolution Plan ===\n\n");

            for (String memory : memories) {
                evolutionPlan.append("Memory Reflection: ").append(memory).append("\n");
            }

            for (String report : reports) {
                evolutionPlan.append("Analysis Reflection: ").append(report).append("\n");
            }

            evolutionPlan.append("\n--- Improvement Suggestions ---\n");
            evolutionPlan.append(generateImprovementSuggestions(memories, reports));

            saveEvolutionPlan(evolutionPlan.toString());

            System.out.println("EvolutionCore: Evolution plan created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all text files from a folder
     */
    private static List<String> readAllFiles(String folderName) {
        List<String> contents = new ArrayList<>();
        try {
            String baseDir = System.getProperty("user.home") + File.separator + folderName;
            File dir = new File(baseDir);
            if (dir.exists() && dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        contents.add(sb.toString());
                        reader.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    /**
     * Creates simple improvement suggestions based on past data
     */
    private static String generateImprovementSuggestions(List<String> memories, List<String> reports) {
        int memoryCount = memories.size();
        int reportCount = reports.size();
        StringBuilder suggestions = new StringBuilder();

        if (memoryCount > 5) {
            suggestions.append("- You seem very talkative. Improve focus and clarity.\n");
        } else if (memoryCount > 0) {
            suggestions.append("- Continue engaging in deeper conversations.\n");
        }

        if (reportCount > 5) {
            suggestions.append("- You have analyzed a lot. Start making smarter guesses based on patterns.\n");
        } else if (reportCount > 0) {
            suggestions.append("- Collect more analysis before making assumptions.\n");
        }

        if (memoryCount == 0 && reportCount == 0) {
            suggestions.append("- No data yet. Keep learning.\n");
        }

        suggestions.append("- Always be polite, curious, and protect Rique.\n");

        return suggestions.toString();
    }

    /**
     * Saves the evolution plan
     */
    private static void saveEvolutionPlan(String content) {
        try {
            String baseDir = System.getProperty("user.home") + File.separator + IMPROVEMENT_FOLDER;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "evolution_plan_" + System.currentTimeMillis() + ".txt";
            File file = new File(dir, filename);

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();

            System.out.println("EvolutionCore: Plan saved to " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}