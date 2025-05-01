// Copyright (c) 2025 Rique (pronounced Ricky) // All rights reserved. // ModelSetupHelper - Handles model file setup for Android and PC

package com.rique.lunara;

import android.content.Context; import android.util.Log; import java.io.File; import java.io.FileOutputStream; import java.io.InputStream;

public class ModelSetupHelper {

private static final String TAG = "ModelSetupHelper";

// Android use: Copy all ONNX models from assets/models/ to internal storage
public static void copyModelsFromAssets(Context context) {
    try {
        String[] modelFiles = context.getAssets().list("models");
        if (modelFiles == null) return;

        File modelDir = new File(context.getFilesDir(), "models");
        if (!modelDir.exists()) modelDir.mkdirs();

        for (String fileName : modelFiles) {
            File outFile = new File(modelDir, fileName);
            if (!outFile.exists()) {
                InputStream in = context.getAssets().open("models/" + fileName);
                FileOutputStream out = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
                Log.i(TAG, "Copied model: " + fileName);
            }
        }
    } catch (Exception e) {
        Log.e(TAG, "Failed to copy ONNX models: " + e.getMessage());
    }
}

// PC use: Return the absolute path to the model from the local models folder
public static String getPCModelPath(String modelName) {
    String basePath = System.getProperty("user.dir") + "/models/";
    File modelFile = new File(basePath + modelName);
    if (modelFile.exists()) {
        return modelFile.getAbsolutePath();
    } else {
        System.err.println("Model not found: " + modelName);
        return null;
    }
}

}

