// Copyright (c) 2025 Rique (pronounced Ricky) - All rights reserved.
// ModelCopyHelper.java - Copies ONNX models from assets to internal storage

package com.rique.lunara;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

public class ModelCopyHelper {

    private static final String TAG = "ModelCopyHelper";

    public static void copyAllModels(Context context) {
        try {
            String[] modelFiles = context.getAssets().list("models");
            if (modelFiles == null) return;

            File targetDir = new File(context.getFilesDir(), "models");
            if (!targetDir.exists()) targetDir.mkdirs();

            for (String modelName : modelFiles) {
                File targetFile = new File(targetDir, modelName);
                if (!targetFile.exists()) {
                    InputStream in = context.getAssets().open("models/" + modelName);
                    FileOutputStream out = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    out.close();
                    Log.i(TAG, "Model copied: " + modelName);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to copy models: " + e.getMessage());
        }
    }
}
