// Copyright (c) 2025 Rique
// All rights reserved.

package com.rique.lunara;

public class SelfUpgradeEngine {

    public static void authorizeUpgrade(String input) {
        if (input.toLowerCase().contains("rique authorize upgrade")) {
            UpgradeGate.allow();
        }
    }

    public static String attemptUpgrade(String input) {
        if (!UpgradeGate.isAllowed()) {
            return "Upgrade not authorized, Rique.";
        }

        if (input.toLowerCase().contains("add response")) {
            return "Code module received. Preparing update...";
        }

        return "No upgrade command detected.";
    }
}