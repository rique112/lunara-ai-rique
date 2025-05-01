// Copyright (c) 2025 Rique (pronounced Ricky) // Trademark Lunara AI ™ // All rights reserved. Unauthorized duplication prohibited.

package com.rique.lunara;

import android.media.MediaPlayer; import android.media.MediaRecorder; import android.os.Environment; import android.util.Log;

import java.io.File; import java.io.IOException; import java.util.HashMap;

public class VoiceTrainer {

private static final String TAG = "VoiceTrainer";
private MediaRecorder recorder;
private MediaPlayer player;
private String currentRecordingPath;
private final HashMap<String, String> voiceProfiles = new HashMap<>();

public VoiceTrainer() {
    File dir = new File(Environment.getExternalStorageDirectory(), "Lunara/voices");
    if (!dir.exists()) dir.mkdirs();
}

public void startRecording(String profileName) {
    currentRecordingPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/Lunara/voices/" + profileName + ".3gp";
    recorder = new MediaRecorder();
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(currentRecordingPath);

    try {
        recorder.prepare();
        recorder.start();
        Log.d(TAG, "Recording started: " + currentRecordingPath);
    } catch (IOException e) {
        Log.e(TAG, "Recording failed", e);
    }
}

public void stopRecording(String profileName) {
    if (recorder != null) {
        recorder.stop();
        recorder.release();
        recorder = null;
        voiceProfiles.put(profileName, currentRecordingPath);
        Log.d(TAG, "Recording saved for profile: " + profileName);
    }
}

public void playVoiceSample(String profileName) {
    String filePath = voiceProfiles.get(profileName);
    if (filePath == null) {
        Log.e(TAG, "No voice sample found for: " + profileName);
        return;
    }
    player = new MediaPlayer();
    try {
        player.setDataSource(filePath);
        player.prepare();
        player.start();
        Log.d(TAG, "Playing voice sample: " + profileName);
    } catch (IOException e) {
        Log.e(TAG, "Playback failed", e);
    }
}

public boolean hasProfile(String profileName) {
    return voiceProfiles.containsKey(profileName);
}

public String getVoicePath(String profileName) {
    return voiceProfiles.get(profileName);
}

}

