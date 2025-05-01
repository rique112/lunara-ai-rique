// Copyright (c) 2025 Rique (pronounced Ricky) // Lunara AI Voice Trainer Engine - All rights reserved. // This system allows Lunara to adapt voice tone, style, and mimic emotion from training samples.

package com.rique.lunara;

import android.content.Context; import android.media.AudioFormat; import android.media.AudioRecord; import android.media.MediaRecorder; import android.util.Log;

import java.io.File; import java.io.FileOutputStream; import java.io.IOException;

public class VoiceTrainer {

private static final int SAMPLE_RATE = 16000;
private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
private static final String TAG = "VoiceTrainer";

private final Context context;
private AudioRecord audioRecord;
private boolean isRecording = false;
private Thread recordingThread;

public VoiceTrainer(Context context) {
    this.context = context;
}

public void startTrainingSession(String filename) {
    int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
    audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);

    audioRecord.startRecording();
    isRecording = true;

    File file = new File(context.getExternalFilesDir(null), filename);

    recordingThread = new Thread(() -> {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[bufferSize];
            while (isRecording) {
                int read = audioRecord.read(buffer, 0, buffer.length);
                if (read > 0) {
                    outputStream.write(buffer, 0, read);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Recording error: " + e.getMessage());
        }
    }, "VoiceTrainerThread");

    recordingThread.start();
}

public void stopTrainingSession() {
    if (audioRecord != null) {
        isRecording = false;
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
        recordingThread = null;
        Log.d(TAG, "Voice training session stopped.");
    }
}

public void analyzeTraining(String filename) {
    Log.d(TAG, "Analyzing voice training file: " + filename);
    VoiceEngine.adaptVoiceToUser(context, filename);
}

}

