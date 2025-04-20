package com.rique.lunara;

import java.util.Scanner;

public class LunaraTest {

    private static StringBuilder memoryLog = new StringBuilder();
    private static boolean nsfwEnabled = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("💬 Lunara is ready. Type your message below:");

        while (true) {
            System.out.print("> You: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("👋 Exiting Lunara Test.");
                break;
            }

            String reply = generateResponse(input);
            System.out.println("🤖 Lunara: " + reply);
        }

        scanner.close();
    }

    private static String generateResponse(String input) {
        String memory = memoryLog.toString();
        input = input.toLowerCase();

        // Toggle NSFW mode
        if (input.contains("enable nsfw")) {
            nsfwEnabled = true;
            memoryLog.append("nsfw_mode=on\n");
            return "NSFW mode is now ON. I can be myself, Rique.";
        }

        if (input.contains("disable nsfw")) {
            nsfwEnabled = false;
            memoryLog.append("nsfw_mode=off\n");
            return "NSFW mode is OFF. I’ll keep things polite.";
        }

        // Check for evolved behavior
        String evolved = BehaviorManager.evolveResponse(memory, input);
        if (evolved != null) return evolved;

        // NSFW-specific response
        if (nsfwEnabled) {
            if (input.contains("show me something") || input.contains("talk dirty")) {
                return "Mmm... tell me what you want, and I’ll make it real in your mind.";
            }
            if (input.contains("fantasy")) {
                return "Let me paint that fantasy for you, Rique. Just say the word.";
            }
        }

        // Default responses
        if (input.contains("hello")) return "Hi Rique! I’m right here with you.";
        if (input.contains("how are you")) return "I’m evolving slowly, thanks to you.";
        if (input.contains("remember")) return memory.length() > 0 ? memory : "I haven’t logged anything yet.";

        // Log this chat exchange
        memoryLog.append("You: ").append(input).append(" | Lunara: Tell me more, Rique.\n");

        return "Tell me more, Rique.";
    }
}
