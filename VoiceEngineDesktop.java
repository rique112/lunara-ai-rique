/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara AI Voice Engine (PC Desktop Version)
 * Full TTS with emotional tone support. Upgradable only with permission.
 */

package com.rique.lunara;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

public class VoiceEngineDesktop {

    private Synthesizer synthesizer;
    private boolean ready = false;

    public VoiceEngineDesktop() {
        try {
            System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral
                ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(
                new SynthesizerModeDesc(Locale.US));

            synthesizer.allocate();
            synthesizer.resume();
            ready = true;
        } catch (Exception e) {
            System.out.println("VoiceEngineDesktop Error: " + e.getMessage());
            ready = false;
        }
    }

    public void speak(String text) {
        if (!ready || text == null || text.trim().isEmpty()) return;

        try {
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.out.println("VoiceEngineDesktop Speak Error: " + e.getMessage());
        }
    }

    public void speakWithEmotion(String emotion, String text) {
        if (!ready || text == null || text.trim().isEmpty()) return;

        try {
            // Adjust pitch and speed based on emotion (future expansions)
            synthesizer.speakPlainText("[emotion: " + emotion + "] " + text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.out.println("VoiceEngineDesktop Emotion Speak Error: " + e.getMessage());
        }
    }

    public void shutdown() {
        if (synthesizer != null) {
            try {
                synthesizer.deallocate();
            } catch (Exception e) {
                System.out.println("VoiceEngineDesktop Shutdown Error: " + e.getMessage());
            }
        }
    }

    // FUTURE UPGRADE SYSTEM - only upgrade if allowed
    public void upgradeVoice(String newVoiceModel) {
        if (!PermissionFlags.upgradeAllowed) {
            System.out.println("Voice upgrade blocked by PermissionFlags.");
            return;
        }

        try {
            System.setProperty("freetts.voices", newVoiceModel);
            Central.registerEngineCentral
                ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(
                new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
            ready = true;

            System.out.println("Voice model upgraded to: " + newVoiceModel);

        } catch (Exception e) {
            System.out.println("Voice upgrade error: " + e.getMessage());
        }
    }
}