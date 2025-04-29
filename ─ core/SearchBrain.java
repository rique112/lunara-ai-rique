package com.rique.lunaraai.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * SearchBrain - Allows Lunara to perform controlled, permission-based internet searches,
 * save search results locally, and analyze information for learning purposes.
 */

public class SearchBrain {

    private static final String SEARCH_MEMORY_FOLDER = "LunaraSearchMemory";

    /**
     * Perform a basic search and save results
     */
    public static void performSearch(String query) {
        System.out.println("SearchBrain: Preparing to search for: " + query);

        if (!isInternetAllowed()) {
            System.out.println("SearchBrain: Internet search is disabled by user.");
            return;
        }

        try {
            String results = fakeSearch(query); // Use a real API later if you want
            saveSearchMemory(query, results);
            System.out.println("SearchBrain: Search completed and saved successfully.");
        } catch (Exception e) {
            System.err.println("SearchBrain Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fake search method for now (simulates finding results)
     */
    private static String fakeSearch(String query) {
        // Simulate finding basic information
        return "Results found for '" + query + "': [Simulated information about " + query + "].";
    }

    /**
     * (Optional) Real search method if connected to a search API in the future
     */
    private static String realInternetSearch(String query) throws IOException {
        URL url = new URL("https://api.duckduckgo.com/?q=" + query + "&format=text");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return result.toString();
    }

    /**
     * Save the search result into a local file
     */
    private static void saveSearchMemory(String query, String result) {
        try {
            String baseDir = System.getProperty("user.home") + File.separator + SEARCH_MEMORY_FOLDER;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "search_" + sanitizeFilename(query) + "_" + System.currentTimeMillis() + ".txt";
            File file = new File(dir, filename);

            FileWriter writer = new FileWriter(file);
            writer.write("Query: " + query + "\n");
            writer.write("Result:\n" + result);
            writer.close();

            System.out.println("SearchBrain: Search saved to " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("SearchBrain Save Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sanitize a filename by replacing illegal characters
     */
    private static String sanitizeFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    /**
     * Check if internet use is allowed (manual switch — private AI respect)
     */
    private static boolean isInternetAllowed() {
        // YOU control this manually for now
        return false; // Set to true if you allow searching
    }
}