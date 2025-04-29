package com.rique.lunaraai.core;

import java.io.; import java.nio.file.; import java.text.SimpleDateFormat; import java.util.*;

/**

LearningCore.java

Part of the Lunara AI System

Handles self-learning, understanding, and memory-based growth.

Works on both Android and PC (paths adapted where needed). */ public class LearningCore {

private static final String LEARNING_LOG_FOLDER = getStoragePath("LunaraLearningLogs");

public static void learnFrom(String topic, String content) { try { String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()); String filename = "learned_" + topic.replaceAll("\s+", "") + "" + timestamp + ".txt"; Path folderPath = Paths.get(LEARNING_LOG_FOLDER); if (!Files.exists(folderPath)) Files.createDirectories(folderPath);

Path filePath = folderPath.resolve(filename);
     Files.writeString(filePath, content);

     System.out.println("LearningCore: Stored lesson on '" + topic + "'");

 } catch (IOException e) {
     System.err.println("LearningCore Save Error: " + e.getMessage());
 }

}

public static List<String> listLearnedTopics() { List<String> topics = new ArrayList<>(); try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(LEARNING_LOG_FOLDER))) { for (Path entry : stream) { if (Files.isRegularFile(entry)) { topics.add(entry.getFileName().toString()); } } } catch (IOException e) { System.err.println("LearningCore List Error: " + e.getMessage()); } return topics; }

public static String retrieveLesson(String filename) { try { Path path = Paths.get(LEARNING_LOG_FOLDER, filename); return Files.readString(path); } catch (IOException e) { return "Error reading lesson: " + e.getMessage(); } }

public static boolean hasLearned(String keyword) { for (String file : listLearnedTopics()) { if (file.toLowerCase().contains(keyword.toLowerCase())) { return true; } } return false; }

public static void forgetAll() { try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(LEARNING_LOG_FOLDER))) { for (Path entry : stream) { Files.delete(entry); } System.out.println("LearningCore: All learning logs cleared."); } catch (IOException e) { System.err.println("LearningCore Forget Error: " + e.getMessage()); } }

private static String getStoragePath(String folderName) { String os = System.getProperty("os.name").toLowerCase(); String base; if (os.contains("android")) { base = "/data/data/com.rique.lunaraai/files"; } else { base = System.getProperty("user.home"); } return base + File.separator + folderName; } }


