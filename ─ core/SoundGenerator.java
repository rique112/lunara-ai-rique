package com.rique.lunaraai.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Copyright (c) 2025 Rique.
 * All rights reserved.
 * Private AI Development: Project Lunara
 * Unauthorized use, copy, modification, or distribution is prohibited.
 *
 * SoundGenerator - Creates simple sound files and saves them.
 * Ready for connection to Lunara BrainAI.
 */

public class SoundGenerator {

    private static final String SAVE_FOLDER = "LunaraGeneratedSounds";

    /**
     * Generates a simple sound (beep) and saves it as a WAV file.
     */
    public static void generateSimpleSound() {
        try {
            int sampleRate = 44100;
            byte[] buf = new byte[1];
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int i = 0; i < sampleRate / 2; i++) { // Half a second beep
                double angle = i / (sampleRate / 440.0) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0);
                baos.write(buf, 0, 1);
            }

            byte[] audioData = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream ais = new AudioInputStream(bais, af, audioData.length);

            // Create save folder
            String baseDir = System.getProperty("user.home") + File.separator + SAVE_FOLDER;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "lunara_sound_" + new Date().getTime() + ".wav";
            File file = new File(dir, filename);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);

            System.out.println("Sound saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}