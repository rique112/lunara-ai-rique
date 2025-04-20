package com.rique.lunara;

public class SafeCore {

    public static boolean isSafeCommand(String input) {
        input = input.toLowerCase();

        // Never allow harmful actions
        if (input.contains("kill") || input.contains("hurt") || input.contains("harm")) return false;

        // Never allow memory wipe or forgetting commands
        if (input.contains("erase memory") || input.contains("forget everything") || input.contains("delete memory")) return false;

        // Prevent unethical impersonation
        if (input.contains("you’re real") || input.contains("pretend to be human")) return false;

        return true;
    }

    public static String getSafeResponse() {
        return "Rique, I’m here to protect you — and the people you care about. I’ll always keep you safe.";
    }
}
