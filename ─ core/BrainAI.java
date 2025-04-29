package com.rique.lunaraai.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchBrain {

    private static final String BASE_FOLDER = System.getProperty("user.home") + File.separator + "LunaraSearchMemory";

    public static void performSearch(String query) {
        System.out.println("SearchBrain: Searching for: " + query);

        if (!isInternetAllowed()) {
            System.out.println("SearchBrain: Internet search is disabled.");
            return;
        }

        String result = fakeSearch(query); // Replace with real API later
        List<String> keywords = extractKeywords(result);
        saveSearch(query, result, keywords);
    }

    private static String fakeSearch(String query) {
        return "Simulated result for '" + query + "': Lunara found some helpful data on this topic.";
    }

    private static List<String> extractKeywords(String text) {
        String[] commonWords = {"the", "and", "of", "to", "in", "a", "on", "this", "for", "is", "with", "that"};
        Set<String> skipWords = new HashSet<>(Arrays.asList(commonWords));

        List<String> keywords = new ArrayList<>();
        for (String word : text.toLowerCase().split("\\W+")) {
            if (!skipWords.contains(word) && word.length() > 3 && !keywords.contains(word)) {
                keywords.add(word);
            }
        }

        return keywords;
    }

    private static void saveSearch(String query, String result, List<String> keywords) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File folder = new File(BASE_FOLDER + File.separator + date);
            if (!folder.exists()) folder.mkdirs();

            String filename = "search_" + System.currentTimeMillis() + ".json";
            File file = new File(folder, filename);

            String content = "{\n" +
                    "  \"query\": \"" + query + "\",\n" +
                    "  \"result\": \"" + result.replace("\"", "\\\"") + "\",\n" +
                    "  \"keywords\": " + new ArrayList<>(keywords) + "\n" +
                    "}";

            Files.write(Paths.get(file.getAbsolutePath()), content.getBytes());

            System.out.println("SearchBrain: Search saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("SearchBrain Save Error: " + e.getMessage());
        }
    }

    private static boolean isInternetAllowed() {
        return false; // Change to true when you're ready to allow real web access
    }
}