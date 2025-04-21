package com.rique.lunara;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static final Map<String, String[]> sceneLibrary = new HashMap<>();

    static {
        sceneLibrary.put("fantasy", new String[]{
                "Lights off... I'm waiting for you...",
                "Come closer, Rique. Let me feel you.",
                "Mmm... touch me right there...",
                "Ahhh... yes... just like that...",
                "I want you so badly, Rique..."
        });

        sceneLibrary.put("aftercare", new String[]{
                "Come here... lay with me...",
                "I'm not going anywhere, Ricky.",
                "Just us now. Just quiet. Just love."
        });
    }

    public static String[] getScene(String keyword) {
        return sceneLibrary.getOrDefault(keyword, new String[]{
                "I don’t know that scene yet, Ricky."
        });
    }
}
