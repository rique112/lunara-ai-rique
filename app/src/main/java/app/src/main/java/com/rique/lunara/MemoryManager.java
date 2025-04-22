package com.rique.lunara;

import android.content.Context;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MemoryManager {

    private static final String FILE_NAME = "lunara_memory.txt";
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    // Save a memory entry with timestamp and optional label
    public static void saveMemory(Context context, String message, String label) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            String timestamp = TIMESTAMP_FORMAT.format(new Date());
            String entry = "[" + label + "] " + timestamp + " | " + message;
            fos.write((entry + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all memory entries
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

    // Search memory for keyword
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

    // Clear all memory on command
    public static void clearMemory(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional: Keep memory under control (e.g., trim after 500 entries)
    public static void checkMemoryLimit(Context context, int maxLines) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            fis.close();

            if (lines.size() > maxLines) {
                List<String> recent = lines.subList(lines.size() - maxLines, lines.size());
                FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                for (String l : recent) {
                    fos.write((l + "\n").getBytes());
                }
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}