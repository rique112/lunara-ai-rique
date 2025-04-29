package com.rique.lunaraai.core;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * AnalyzerEngine - Analyzes images, sounds, and videos.
 * Saves analysis reports locally for Lunara AI use.
 */

public class AnalyzerEngine {

    private static final String SAVE_FOLDER = "LunaraAnalysisReports";

    /**
     * Analyze an image file (basic color and size info)
     */
    public static void analyzeImage(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            int width = img.getWidth();
            int height = img.getHeight();
            int color = img.getRGB(width / 2, height / 2);

            String report = "Image Analysis Report\n" +
                    "File: " + imagePath + "\n" +
                    "Width: " + width + " px\n" +
                    "Height: " + height + " px\n" +
                    "Center Pixel Color (RGB): " + color + "\n";

            saveReport(report, "image");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyze an audio file (basic audio properties)
     */
    public static void analyzeSound(String soundPath) {
        try {
            File file = new File(soundPath);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);

            String report = "Sound Analysis Report\n" +
                    "File: " + soundPath + "\n" +
                    "Type: " + fileFormat.getType() + "\n" +
                    "Properties: " + fileFormat.properties().toString() + "\n";

            saveReport(report, "sound");

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyze a video file (basic file info — advanced later)
     */
    public static void analyzeVideo(String videoPath) {
        try {
            File file = new File(videoPath);
            long fileSize = file.length();

            String report = "Video Analysis Report\n" +
                    "File: " + videoPath + "\n" +
                    "File Size: " + fileSize + " bytes\n" +
                    "Frame analysis: (Upgrade coming later)\n";

            saveReport(report, "video");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the analysis report to a text file
     */
    private static void saveReport(String content, String type) {
        try {
            String baseDir = System.getProperty("user.home") + File.separator + SAVE_FOLDER;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "lunara_" + type + "_report_" + new Date().getTime() + ".txt";
            File reportFile = new File(dir, filename);

            FileWriter writer = new FileWriter(reportFile);
            writer.write(content);
            writer.close();

            System.out.println("Report saved to: " + reportFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}