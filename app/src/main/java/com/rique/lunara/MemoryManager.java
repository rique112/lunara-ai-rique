package com.rique.lunara;

public class MemoryManager {

    public static String getReply(String input) {
        String response = LunaraBrain.respondTo(input);

        if (response.contains("Learned:")) {
            String key = input.split(" ")[2];
            String value = input.split(" ", 4)[3];
            KnowledgeEngine.saveMemory(key, value);
        }

        return response;
    }
}
