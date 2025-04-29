package com.rique.lunaraai.core;

import java.io.*; import java.util.Date;

/**

LearningGate.java

This class is the gatekeeper for all AI learning and evolution.

It checks PermissionFlags to determine if Lunara is allowed to observe, analyze, or learn.

All activity is logged, and no actions are taken unless Rique explicitly enables them.

Copyright (c) 2025 Rique (Ricky) — All rights reserved.

Trademark: Lunara™ — AI Assistant owned and protected by Rique. */ public class LearningGate {

private static final String LEARNING_LOG = System.getProperty("user.home") + File.separator + "LunaraLearningEvents.txt";

public static void processLearningEvent(String input, String sourceTag) { if (!PermissionFlags.upgradeAllowed && !PermissionFlags.allowLearning) { log("[BLOCKED] Learning attempt denied (permissions off) from: " + sourceTag); return; }

if (PermissionFlags.allowLearning) {
     log("[LEARNING] Observing input from " + sourceTag + ": " + input);
     // Save learned data or route it to MemoryManager, Analyzer, etc.
     LearningCore.learnFrom(sourceTag, input);
 }

 if (PermissionFlags.upgradeAllowed && PermissionFlags.allowSelfUpgrade) {
     log("[UPGRADE CHECK] Self-upgrade trigger evaluated from: " + sourceTag);
     // Route to self-upgrade handler (only if enabled)
     SelfUpgradeManager.evaluate(input);
 }

}

public static void passiveScan(String eventName) { if (PermissionFlags.allowWriteToMemory && PermissionFlags.allowLearning) { log("[SCAN] Passive memory entry: " + eventName); } else { log("[BLOCKED] Passive scan blocked due to permission limits."); } }

private static void log(String msg) { try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEARNING_LOG, true))) { writer.write(new Date() + ": " + msg + "\n"); } catch (IOException e) { System.err.println("LearningGate Log Error: " + e.getMessage()); } }

// Manual trigger for testing public static void testExample() { processLearningEvent("example: user said 'I feel tired today'", "VoiceScanner"); } }


