package com.rique.lunara;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PCFileScanner {

    private static final String AUDIO_DIR = "C:/Lunara/imported_audio/";

    public static File getRandomAudioFile() {
        File folder = new File(AUDIO_DIR);
        if (!folder.exists() || !folder.isDirectory()) return null;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"));
        if (files == null || files.length == 0) return null;

        return files[new Random().nextInt(files.length)];
    }
}
