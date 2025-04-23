package com.rique.lunara.core;

public class SafeCore {

    public static boolean isSafeCommand(String input) {
        input = input.toLowerCase();
        String[] unsafeKeywords = {"harm", "kill", "delete"};
        for (String keyword : unsafeKeywords) {
            if (input.contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    public static String getDefaultSafeMessage() {
        return "Ricky, I'm here to keep things safe.";
    }
}
