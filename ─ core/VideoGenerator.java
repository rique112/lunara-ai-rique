package com.rique.lunaraai.core;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Rational;
import org.jcodec.common.model.Picture;
import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * VideoGenerator - Creates a simple video from multiple images.
 * Saves video to local device. Fully private.
 */

public class VideoGenerator {

    private static final String SAVE_FOLDER = "LunaraGeneratedVideos";

    /**
     * Generates a video file from an array of image paths
     */
    public static void generateVideoFromImages(String[] imagePaths, int fps) {
        try {
            String baseDir = System.getProperty("user.home") + File.separator + SAVE_FOLDER;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "lunara_video_" + new Date().getTime() + ".mp4";
            File output = new File(dir, filename);

            AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(output, fps);

            for (String path : imagePaths) {
                BufferedImage img = ImageIO.read(new File(path));
                encoder.encodeImage(img);
            }

            encoder.finish();
            System.out.println("Video saved to: " + output.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}