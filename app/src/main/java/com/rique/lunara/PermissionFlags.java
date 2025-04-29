/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Trademark: Lunara™ is a protected AI companion owned and controlled exclusively by Rique.

Unauthorized use, replication, or modification of this system is strictly forbidden.

This class defines all permission flags for Lunara's core systems.

Lunara CANNOT bypass these flags.

Controlled only by Rique — via manual override, GUI, or secure command.

Includes learning, upgrades, internet, memory growth, voice, camera, and more. */


package com.rique.lunara;

import java.io.; import java.util.;

public class PermissionFlags {

// === Core Permission Flags ===
public static boolean upgradeAllowed = false;
public static boolean internetAllowed = false;
public static boolean voiceLearningAllowed = false;
public static boolean memoryExpansionAllowed = false;
public static boolean imageGenerationAllowed = false;
public static boolean fileAccessAllowed = false;
public static boolean moduleCreationAllowed = false;
public static boolean emergencyShutdown = false;
public static final String ownerSignature = "Rique"; // Manual override only

// === Extended Permissions (fully customizable) ===
public static boolean allowCameraAccess = false;
public static boolean allowMicAccess = false;
public static boolean allowBodyAnalysis = false;
public static boolean allowWriteToMemory = false;
public static boolean allowSelfUpgrade = false;

private static final String LOG_PATH = System.getProperty("user.home") + File.separator + "LunaraPermissionLog.txt";

// === Utility methods for logging and flag control ===
public static void setPermission(String name, boolean value) {
    switch (name.toLowerCase()) {
        case "upgrade": upgradeAllowed = value; break;
        case "internet": internetAllowed = value; break;
        case "voice": voiceLearningAllowed = value; break;
        case "memory": memoryExpansionAllowed = value; break;
        case "image": imageGenerationAllowed = value; break;
        case "files": fileAccessAllowed = value; break;
        case "modules": moduleCreationAllowed = value; break;
        case "camera": allowCameraAccess = value; break;
        case "mic": allowMicAccess = value; break;
        case "body": allowBodyAnalysis = value; break;
        case "write": allowWriteToMemory = value; break;
        case "selfupgrade": allowSelfUpgrade = value; break;
        case "shutdown": emergencyShutdown = value; break;
        default:
            log("Attempted to set unknown permission: " + name);
            return;
    }
    log("Permission '" + name + "' set to " + value);
}

public static boolean check(String name) {
    switch (name.toLowerCase()) {
        case "upgrade": return upgradeAllowed;
        case "internet": return internetAllowed;
        case "voice": return voiceLearningAllowed;
        case "memory": return memoryExpansionAllowed;
        case "image": return imageGenerationAllowed;
        case "files": return fileAccessAllowed;
        case "modules": return moduleCreationAllowed;
        case "camera": return allowCameraAccess;
        case "mic": return allowMicAccess;
        case "body": return allowBodyAnalysis;
        case "write": return allowWriteToMemory;
        case "selfupgrade": return allowSelfUpgrade;
        case "shutdown": return emergencyShutdown;
        default: return false;
    }
}

private static void log(String msg) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_PATH, true))) {
        writer.write(new Date() + ": " + msg + "\n");
    } catch (IOException e) {
        System.err.println("[Permission Log Error] " + e.getMessage());
    }
}

}

