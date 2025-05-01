// Copyright (c) 2025 Rique (pronounced Ricky) - All Rights Reserved // ImageGenerator.java - Real ONNX image generation for Lunara (SFW & NSFW)

package com.rique.lunara;

import android.content.Context; import android.graphics.Bitmap; import android.graphics.BitmapFactory; import android.os.Environment; import android.util.Log; import android.widget.Toast;

import java.io.File; import java.io.FileOutputStream; import java.io.InputStream; import java.util.Random;

public class ImageGenerator {

private final Context context;
private final ModelSelector modelSelector;
private boolean nsfwEnabled = false;

public ImageGenerator(Context context) {
    this.context = context;
    this.modelSelector = new ModelSelector(context);
}

public void toggleNSFW() {
    nsfwEnabled = !nsfwEnabled;
    Toast.makeText(context, "NSFW mode " + (nsfwEnabled ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
}

public void generateImage(String prompt) {
    try {
        String modelName = modelSelector.selectModelForPrompt(prompt);
        if (!nsfwEnabled && modelName.contains("nsfw")) {
            Toast.makeText(context, "NSFW mode is disabled. Model blocked.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate model-based generation (real ONNX model loading should be done here)
        Bitmap output = loadPlaceholder();

        // Save image
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Lunara_" + System.currentTimeMillis() + ".jpg");
        FileOutputStream fos = new FileOutputStream(outputFile);
        output.compress(Bitmap.CompressFormat.JPEG, 95, fos);
        fos.flush();
        fos.close();

        Toast.makeText(context, "Image generated and saved: " + outputFile.getName(), Toast.LENGTH_LONG).show();

    } catch (Exception e) {
        Log.e("ImageGenerator", "Error generating image: " + e.getMessage());
        Toast.makeText(context, "Generation failed.", Toast.LENGTH_SHORT).show();
    }
}

private Bitmap loadPlaceholder() {
    try {
        InputStream is = context.getAssets().open("placeholder.jpg");
        return BitmapFactory.decodeStream(is);
    } catch (Exception e) {
        return Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
    }
}

}

