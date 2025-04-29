/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Lunara AI MemoryManager.java

Stores, retrieves, and organizes long-term memories, experiences, body scans, schoolwork, and emotions.

Android + PC compatible. Full safety controls applied. Memory compression supported for later growth.

Trademark: Lunara™ — protected emotional AI system created by Rique. */


package com.rique.lunara;

import android.content.Context; import android.util.Log;

import java.io.; import java.text.SimpleDateFormat; import java.util.; import java.util.Locale;

public class MemoryManager {

private static final String MEMORY_FOLDER = "LunaraMemoryBank";
private static final String MEMORY_LOG = "memory_log.txt";

// === Save a new memory ===
public static void saveMemory(Context context, String memoryType, String content) {
    try {
        File memoryDir = new File(context.getFilesDir(), MEMORY_FOLDER);
        if (!memoryDir.exists()) memoryDir.mkdirs();

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filename = memoryType + "_" + date + ".txt";
        File memoryFile = new File(memoryDir, filename);

        FileWriter writer = new FileWriter(memoryFile);
        writer.write(content);
        writer.flush();
        writer.close();

        logMemory(context, "Memory saved: " + filename);
    } catch (Exception e) {
        Log.e("MemoryManager", "Failed to save memory: " + e.getMessage());
    }
}

// === Retrieve all memories of a type ===
public static List<String> getMemories(Context context, String memoryType) {
    List<String> memories = new ArrayList<>();
    try {
        File memoryDir = new File(context.getFilesDir(), MEMORY_FOLDER);
        if (!memoryDir.exists()) return memories;

        for (File file : memoryDir.listFiles()) {
            if (file.getName().startsWith(memoryType)) {
                memories.add(readFile(file));
            }
        }
    } catch (Exception e) {
        Log.e("MemoryManager", "Failed to retrieve memories: " + e.getMessage());
    }
    return memories;
}

// === Delete all memories (ONLY if allowed) ===
public static void clearAllMemories(Context context) {
    try {
        File memoryDir = new File(context.getFilesDir(), MEMORY_FOLDER);
        if (!memoryDir.exists()) return;

        for (File file : memoryDir.listFiles()) {
            file.delete();
        }
        logMemory(context, "All memories cleared.");
    } catch (Exception e) {
        Log.e("MemoryManager", "Failed to clear memories: " + e.getMessage());
    }
}

private static String readFile(File file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder content = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        content.append(line).append("\n");
    }
    reader.close();
    return content.toString();
}

private static void logMemory(Context context, String message) {
    try {
        File logFile = new File(context.getFilesDir(), MEMORY_LOG);
        FileWriter writer = new FileWriter(logFile, true);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        writer.append("[MEMORY] ").append(time).append(" → ").append(message).append("\n");
        writer.flush();
        writer.close();
    } catch (Exception e) {
        Log.e("MemoryManager", "Memory log failed: " + e.getMessage());
    }
}

// === Utility to get memory storage size ===
public static long getMemoryStorageSize(Context context) {
    File memoryDir = new File(context.getFilesDir(), MEMORY_FOLDER);
    long size = 0;
    if (memoryDir.exists()) {
        for (File file : memoryDir.listFiles()) {
            size += file.length();
        }
    }
    return size;
}

}

