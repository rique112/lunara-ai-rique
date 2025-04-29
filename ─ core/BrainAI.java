package com.rique.lunaraai.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * BrainAI - The main thinking core of Lunara.
 * Connects Memory, Voice, Analyzers, and Generators.
 * Ready for evolution, emotions, and internet expansion.
 */

public class BrainAI {

    /**
     * Think about a user input and react accordingly
     */
    public static void processInput(String input) {
        System.out.println("Lunara thinking about: " + input);

        saveMemory(input); // Save what was said

        if (input.toLowerCase().contains("image")) {
            System.out.println("Lunara: Creating a new image...");
            ImageGenerator.generateRandomImage();
        } else if (input.toLowerCase().contains("sound")) {
            System.out.println("Lunara: Creating a new sound...");
            SoundGenerator.generateSimpleSound();
        } else if (input.toLowerCase().contains("video")) {
            System.out.println("Lunara: Please select images to build a video. (Manual step for now.)");
            // VideoGenerator usage later with real selection
        } else if (input.toLowerCase().contains("analyze")) {
            System.out.println("Lunara: Please provide file path to analyze...");
            // Analyzer would be triggered externally for now (camera/files later)
        } else {
            System.out.println("Lunara: I heard you say: \"" + input + "\"");
            // VoiceEngine could be triggered here to speak out loud (expand later)
        }
    }

    /**
     * Save memories of conversations, commands, and analysis
     */
    private static void saveMemory(String text) {
        try {
            String baseDir = System.getProperty("user.home") + File.separator + "LunaraMemory";
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "memory_" + new Date().getTime() + ".txt";
            File file = new File(dir, filename);

            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();

            System.out.println("Memory saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}