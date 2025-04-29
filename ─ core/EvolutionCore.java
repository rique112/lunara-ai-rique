package com.rique.lunaraai.core;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * EvolutionCore - Lunara's true learning and self-improvement core.
 * Reads memories, reads analysis reports, generates evolution plans,
 * saves improvements for smarter behavior over time.
 */

public class EvolutionCore {

    private static String MEMORY_FOLDER;
    private static String ANALYSIS_FOLDER;
    private static String IMPROVEMENT_FOLDER;

    static {
        // Try to load configuration from a properties file (optional)
        Properties config = new Properties();
        try (InputStream input = EvolutionCore.class.getClassLoader().getResourceAsStream("lunara.config")) {
            if (input != null) {
                config.load(input);
                MEMORY_FOLDER = config.getProperty("memory.folder", "LunaraMemory");
                ANALYSIS_FOLDER = config.getProperty("analysis.folder", "LunaraAnalysisReports");
                IMPROVEMENT_FOLDER = config.getProperty("improvement.folder", "LunaraEvolutionPlans");
            } else {
                System.out.println("EvolutionCore: Config file not found. Using default folders.");
                MEMORY_FOLDER = "LunaraMemory";
                ANALYSIS_FOLDER = "LunaraAnalysisReports";
                IMPROVEMENT_FOLDER = "LunaraEvolutionPlans";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            MEMORY_FOLDER = "LunaraMemory";
            ANALYSIS_FOLDER = "LunaraAnalysisReports";
            IMPROVEMENT_FOLDER = "LunaraEvolutionPlans";
        }
    }

    /**
     * Start the evolution process: read memories, generate improvement plan
     */
    public static void evolve() {
        try {
            List<String> memories = readAllFiles(MEMORY_FOLDER);
            List<String> reports = readAllFiles(ANALYSIS_FOLDER);

            if (memories.isEmpty() && reports.isEmpty()) {
                System.out.println("EvolutionCore: No memories or reports found to evolve from.");
                return;
            }

            StringBuilder plan = new StringBuilder();
            plan.append("=== Lunara Evolution Plan ===\n\n");

            for (String memory : memories) {
                plan.append("Memory Reflection:\n").append(memory.trim()).append("\n\n");
            }

            for (String report : reports) {
                plan.append("Analysis Reflection:\n").append(report.trim()).append("\n\n");
            }

            plan.append("--- Suggested Improvements ---\n");
            plan.append(generateImprovementSuggestions(memories, reports));

            saveEvolutionPlan(plan.toString());

            System.out.println("EvolutionCore: Evolution plan generated successfully!");

        } catch (IOException e) {
            System.err.println("EvolutionCore Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reads all text files from a specified folder
     */
    private static List<String> readAllFiles(String folderName) throws IOException {
        List<String> contents = new ArrayList<>();
        Path folderPath = Paths.get(System.getProperty("user.home"), folderName);
        if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        try {
                            contents.add(Files.readString(file));
                        } catch (IOException e) {
                            System.err.println("Failed to read file: " + file.getFileName() + ". Skipping...");
                        }
                    }
                }
            }
        } else {
            System.out.println("Warning: Folder not found: " + folderPath);
        }
        return contents;
    }

    /**
     * Generates smarter improvement suggestions
     */
    private static String generateImprovementSuggestions(List<String> memories, List<String> reports) {
        int memoryCount = memories.size();
        int reportCount = reports.size();
        StringBuilder suggestions = new StringBuilder();

        if (memoryCount > 10) {
            suggestions.append("- Improve memory summarization. Speak with more precision.\n");
        } else if (memoryCount > 0) {
            suggestions.append("- Keep interacting and forming new detailed memories.\n");