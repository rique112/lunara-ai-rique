package com.rique.lunara.core;

import java.io.File;

public class ImageGenerator {

    private static final String MODEL_PATH = "models/image-model.onnx";
    private static final String OUTPUT_PATH = "generated/image.png";

    public String generateImage(String prompt) {
        File modelFile = new File(MODEL_PATH);
        if (!modelFile.exists()) {
            return "[ImageGenerator] Model file not found.";
        }

        File output = new File(OUTPUT_PATH);
        output.getParentFile().mkdirs(); // Make sure folders exist

        return "[ImageGenerator] Image created for prompt: " + prompt;
    }
}
