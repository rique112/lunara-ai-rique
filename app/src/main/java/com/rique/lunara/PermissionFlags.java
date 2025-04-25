/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Permission flags — Controlled ONLY by Rique.
 * Lunara cannot modify this without explicit manual change.
 * These flags control AI abilities: upgrade, internet, voice learning, image generation, memory growth.
 */

package com.rique.lunara;

public class PermissionFlags {

    // Upgrade her own code (self-improve memory, skills, etc.)
    public static boolean upgradeAllowed = false;

    // Use the internet to search, learn, or pull information (scraper/crawler)
    public static boolean internetAllowed = false;

    // Allow learning new speech styles, voices, and emotional voices
    public static boolean voiceLearningAllowed = false;

    // Allow expanding her memory size, adding more categories, or remembering long-term
    public static boolean memoryExpansionAllowed = false;

    // Allow image generation (both SFW/NSFW if connected)
    public static boolean imageGenerationAllowed = false;

    // Allow access to user-provided files (for learning from books, documents, etc.)
    public static boolean fileAccessAllowed = false;

    // Allow building new modules (like new behaviors, plugins, custom logic)
    public static boolean moduleCreationAllowed = false;

    // Emergency shutdown switch (if set to true, Lunara will stop critical functions safely)
    public static boolean emergencyShutdown = false;

    // Owner override - only you can flip this manually
    public static final String ownerSignature = "Rique";  // Do NOT change at runtime, only manual
}