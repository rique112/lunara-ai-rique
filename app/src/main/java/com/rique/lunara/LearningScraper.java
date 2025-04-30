/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

LearningScraper.java – Auto crawler to scan URLs and extract real content.

No API used. Learns from links you give it. Parses text for Lunara to study. */


package com.rique.lunara;

import android.content.Context; import android.os.AsyncTask; import android.util.Log;

import org.jsoup.Jsoup; import org.jsoup.nodes.Document; import org.jsoup.select.Elements;

import java.io.File; import java.io.FileWriter; import java.util.UUID;

public class LearningScraper {

public static void scanAndLearn(Context context, String url) {
    new ScraperTask(context).execute(url);
}

private static class ScraperTask extends AsyncTask<String, Void, String> {

    private final Context context;

    public ScraperTask(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... urls) {
        String url = urls[0];
        try {
            Document doc = Jsoup.connect(url).userAgent("Lunara-AI").get();
            Elements paragraphs = doc.select("p");
            StringBuilder content = new StringBuilder();

            for (int i = 0; i < paragraphs.size(); i++) {
                content.append(paragraphs.get(i).text()).append("\n");
            }

            String finalText = content.toString();
            saveAndTeach(context, finalText);
            return "Learned from page: " + url;

        } catch (Exception e) {
            Log.e("LearningScraper", "Failed to scrape: " + e.getMessage());
            return "Error scraping: " + e.getMessage();
        }
    }
}

private static void saveAndTeach(Context context, String text) {
    try {
        File learnDir = new File(context.getFilesDir(), "LunaraWebLearn");
        if (!learnDir.exists()) learnDir.mkdirs();

        File file = new File(learnDir, "web_learn_" + UUID.randomUUID() + ".txt");
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.close();

        MemoryManager.saveMemory(context, text, "WebScan");
        TrainerEngine.learn("web-data", text);
        Log.i("LearningScraper", "Content saved and learned from web.");

    } catch (Exception e) {
        Log.e("LearningScraper", "Save error: " + e.getMessage());
    }
}

}

