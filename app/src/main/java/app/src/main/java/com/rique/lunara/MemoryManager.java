/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara AI Memory Management System
 * No part of this code may be copied, modified, or redistributed without explicit permission.
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MemoryManager {

    private static final String MEMORY_DIR = "lunara_memory";
    private static final String MEMORY_FILE = "core_memory.txt";

    // Save memory entry (with type: Interaction, Learning, Emotion, etc.)
    public static void saveMemory(Context context, String data, String type) {
        try {
            File dir = new File(context.getFilesDir(), MEMORY_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, MEMORY_FILE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            String entry = "[" + type.toUpperCase() + "] " + timestamp + " -> " + data;

            writer.append(entry);
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (Exception e) {
            Log.e("MemoryManager", "Error saving memory: " + e.getMessage());
        }
    }

    // Load the last 10 memory entries for Lunara's context
    public static String loadMemory(Context context) {
        try {
            File dir = new File(context.getFilesDir(), MEMORY_DIR);
            File file = new File(dir, MEMORY_FILE);

            if (!file.exists()) {
                return "I don't remember anything yet.";
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> entries = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                entries.add(line);
            }
            reader.close();

            StringBuilder lastEntries = new StringBuilder();
            int start = Math.max(0, entries.size() - 10);
            for (int i = start; i < entries.size(); i++) {
                lastEntries.append(entries.get(i)).append("\n");
            }
            return lastEntries.toString().trim();

        } catch (Exception e) {
            Log.e("MemoryManager", "Error loading memory: " + e.getMessage());
            return "My memory is cloudy right now.";
        }
    }

    // Clear all stored memory
    public static void clearMemory(Context context) {
        try {
            File dir = new File(context.getFilesDir(), MEMORY_DIR);
            File file = new File(dir, MEMORY_FILE);

            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Log.e("MemoryManager", "Error clearing memory: " + e.getMessage());
        }
    }

    // Advanced future option: categorized memory retrieval
    public static String searchMemory(Context context, String typeFilter) {
        try {
            File dir = new File(context.getFilesDir(), MEMORY_DIR);
            File file = new File(dir, MEMORY_FILE);

            if (!file.exists()) {
                return "I have no memories of that type.";
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> filteredEntries = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[" + typeFilter.toUpperCase() + "]")) {
                    filteredEntries.add(line);
                }
            }
            reader.close();

            if (filteredEntries.isEmpty()) {
                return "No memories found for: " + typeFilter;
            }

            StringBuilder memories = new StringBuilder();
            for (String entry : filteredEntries) {
                memories.append(entry).append("\n");
            }
            return memories.toString().trim();

        } catch (Exception e) {
            Log.e("MemoryManager", "Error searching memory: " + e.getMessage());
            return "Error accessing specific memories.";
        }
    }
}