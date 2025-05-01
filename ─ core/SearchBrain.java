// Copyright (c) 2025 Rique (pronounced Ricky) - All Rights Reserved // Lunara AI - Internet Search and Code Analyzer

package com.rique.lunara;

import android.content.Context; import android.os.AsyncTask; import android.util.Log; import android.widget.Toast;

import org.jsoup.Jsoup; import org.jsoup.nodes.Document; import org.jsoup.select.Elements;

import java.io.IOException;

public class SearchBrain {

private static final String TAG = "SearchBrain";

public static void searchAndLearn(Context context, String query) {
    new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String url = "https://www.google.com/search?q=" + query.replace(" ", "+");
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();
                Elements results = doc.select("h3");
                return results.size() > 0 ? results.get(0).text() : "No results found.";
            } catch (IOException e) {
                Log.e(TAG, "Search error: " + e.getMessage());
                return "Search failed: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, "Search result: " + result, Toast.LENGTH_LONG).show();
            MemoryManager.saveLearning(context, "Search: " + query + "\nResult: " + result);
            VoiceEngine.speakStatic(context, "I learned something from that search, Rique.");
        }
    }.execute();
}

}

