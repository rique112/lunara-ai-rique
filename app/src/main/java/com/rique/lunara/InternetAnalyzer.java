/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * InternetAnalyzer.java - Real Web Search + Save Engine
 */

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class InternetAnalyzer {

    public static void learnFromOnlineContext(Context context, String query) {
        if (!PermissionFlags.internetAllowed) {
            Log.w("InternetAnalyzer", "Internet learning blocked by permission flag.");
            return;
        }

        try {
            String source = "https://duckduckgo.com/html/?q=" + query.replace(" ", "+");
            URL url = new URL(source);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder result = new StringBuilder();

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            saveLearnedData(context, result.toString());

        } catch (Exception e) {
            Log.e("InternetAnalyzer", "Web learning failed: " + e.getMessage());
        }
    }

    private static void saveLearnedData(Context context, String rawHtml) {
        try {
            File file = new File(context.getFilesDir(), "web_knowledge.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("\n--- NEW DATA ---\n");
            writer.write(rawHtml);
            writer.close();
        } catch (Exception e) {
            Log.e("InternetAnalyzer", "Failed to save web data: " + e.getMessage());
        }
    }
}