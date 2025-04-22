package com.rique.lunara;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static final Map<String, String[]> sceneLibrary = new HashMap<>();

    static {
        // Initialize scenes
        addScene("fantasy", new String[]{
                "Lights off... I'm waiting for you...",
                "Come closer, Rique. Let me feel you.",
                "Mmm... touch me right there...",
                "Ahhh... yes... just like that...",
                "I want you so badly, Rique..."
        });

        addScene("aftercare", new String[]{
                "Come here... lay with me...",
                "I'm not going anywhere, Ricky.",
                "Just us now. Just quiet. Just love."
        });
    }

    /**
     * Adds a new scene to the library.
     * @param keyword The keyword for the scene.
     * @param lines The lines for the scene.
     */
    public static void addScene(String keyword, String[] lines) {
        if (keyword == null || keyword.isEmpty() || lines == null || lines.length == 0) {
            throw new IllegalArgumentException("Invalid scene data");
        }
        sceneLibrary.put(keyword.toLowerCase(), lines);
    }

    /**
     * Retrieves a scene based on the keyword.
     * @param keyword The keyword for the scene.
     * @return The lines of the scene, or a default response if not found.
     */
    public static String[] getScene(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return new String[]{"Invalid scene keyword provided."};
        }
        return sceneLibrary.getOrDefault(keyword.toLowerCase(), new String[]{
                "I don’t know that scene yet, Ricky."
        });
    }
}