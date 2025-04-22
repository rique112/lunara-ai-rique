package com.rique.lunara;

import android.content.Context;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MemoryManager {

    private static final String FILE_NAME = "lunara_memory.txt";
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    // Save a memory entry with timestamp
    public static void saveMemory(Context context, String message) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            String timestamp = TIMESTAMP_FORMAT.format(new Date());
            fos.write((timestamp + " | " + message + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the entire memory
    public static String loadMemory(Context context) {
        StringBuilder memory = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                memory.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memory.toString();
    }

    // Clear all memory
    public static void clearMemory(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Search memory for a keyword
    public static List<String> searchMemory(Context context, String keyword) {
        List<String> matches = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    matches.add(line);
                }
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }
}