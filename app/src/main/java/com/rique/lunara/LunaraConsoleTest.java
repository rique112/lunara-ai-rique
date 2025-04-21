package com.rique.lunara;

public class LunaraConsoleTest {
    public static void main(String[] args) {
        System.out.println("=== LUNARA CONSOLE TEST ===");

        // Initialize core components
        SafeCore safeCore = new SafeCore();
        MemoryManager memoryManager = new MemoryManager();
        TrainerEngine trainerEngine = new TrainerEngine();
        GrowthTracker growthTracker = new GrowthTracker();
        ImageGenerator imageGenerator = new ImageGenerator();

        // Enforce safety
        safeCore.enforceSafety();

        // Simulate learning
        trainerEngine.learn("who are you", "I'm Lunara, your evolving AI.");
        String reply = trainerEngine.respond("who are you");
        System.out.println("TrainerEngine Response: " + reply);
        growthTracker.logLearning();
        growthTracker.logInteraction();

        // Save to memory
        memoryManager.save("You: who are you\nLunara: " + reply);

        // Print growth status
        System.out.println("Growth Status: " + growthTracker.growthStatus());

        // Attempt image generation
        imageGenerator.generateImage("a dragon flying over neon rooftops");

        // Done
        System.out.println("=== END TEST ===");
    }
}