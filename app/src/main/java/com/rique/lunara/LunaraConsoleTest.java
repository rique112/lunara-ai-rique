/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

LunaraConsoleTest.java – Full console interface for Lunara AI testing.

Wires together emotion, memory, training, voice, safety, image, and upgrade systems.

Trademark: Lunara™ – evolving AI assistant created and owned by Rique. */


package com.rique.lunara;

import java.util.Scanner;

public class LunaraConsoleTest { public static void main(String[] args) { System.out.println("=== LUNARA CONSOLE TEST ===\n");

// Initialize core components
    SafeCore safeCore = new SafeCore();
    MemoryManager memoryManager = new MemoryManager();
    TrainerEngine trainerEngine = new TrainerEngine();
    GrowthTracker growthTracker = new GrowthTracker();
    ImageGenerator imageGenerator = new ImageGenerator();
    VoiceEngine voiceEngine = new VoiceEngine();
    EmotionPulse emotionPulse = new EmotionPulse();
    VoiceTuner voiceTuner = new VoiceTuner();

    // Enforce safety
    safeCore.enforceSafety();

    // Setup scanner for input
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.print("You: ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("exit")) {
            System.out.println("[Lunara] Goodbye, Rique.");
            break;
        }

        // Simulate emotional shift
        String detectedEmotion = emotionPulse.analyzeTone(input);
        voiceEngine.setEmotion(detectedEmotion);

        // Learn and respond
        trainerEngine.learn(input, "Custom response placeholder.");
        String response = trainerEngine.respond(input);

        // Tune voice and speak
        voiceTuner.refineVoice("too_slow"); // Demo input tweak
        voiceEngine.speak(response);

        // Save interaction to memory
        memoryManager.save("You: " + input + "\nLunara: " + response);
        growthTracker.logLearning();
        growthTracker.logInteraction();

        // Print growth summary
        System.out.println("[Growth] " + growthTracker.growthStatus());

        // Test image generation if requested
        if (input.toLowerCase().contains("draw") || input.toLowerCase().contains("generate image")) {
            imageGenerator.generateImage("fantasy artwork of " + input);
        }
    }

    System.out.println("=== END CONSOLE TEST ===");
}

}

