// Copyright (c) 2025 Rique (pronounced Ricky) - All Rights Reserved // ModelSelector - Loads ONNX model based on prompt and tag logic

package com.rique.lunara;

import android.content.Context; import android.util.Log;

import org.json.JSONArray; import org.json.JSONObject;

import java.io.BufferedReader; import java.io.InputStream; import java.io.InputStreamReader; import java.util.HashMap; import java.util.HashSet; import java.util.Set;

public class ModelSelector {

private final HashMap<String, String> tagToModel = new HashMap<>();
private String defaultModel = "realistic.onnx";

public ModelSelector(Context context) {
    try {
        InputStream inputStream = context.getAssets().open("models/model_manifest.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        parseManifest(jsonBuilder.toString());
    } catch (Exception e) {
        Log.e("ModelSelector", "Failed to load model_manifest.json: " + e.getMessage());
    }
}

private void parseManifest(String json) {
    try {
        JSONObject root = new JSONObject(json);
        JSONArray models = root.getJSONArray("models");
        defaultModel = root.getString("default_model");

        for (int i = 0; i < models.length(); i++) {
            JSONObject model = models.getJSONObject(i);
            String name = model.getString("name");
            JSONArray tags = model.getJSONArray("tags");
            for (int j = 0; j < tags.length(); j++) {
                tagToModel.put(tags.getString(j).toLowerCase(), name);
            }
        }
    } catch (Exception e) {
        Log.e("ModelSelector", "Failed to parse manifest: " + e.getMessage());
    }
}

public String selectModelForPrompt(String prompt) {
    Set<String> matched = new HashSet<>();
    for (String word : prompt.toLowerCase().split(" ")) {
        if (tagToModel.containsKey(word)) {
            matched.add(tagToModel.get(word));
        }
    }
    if (!matched.isEmpty()) {
        return matched.iterator().next(); // Return first matched model
    }
    return defaultModel;
}

public String getDefaultModel() {
    return defaultModel;
}

}

