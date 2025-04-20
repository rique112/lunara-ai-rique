package com.rique.lunara;

import android.content.Context;
import java.io.*;

public class MemoryManager {

    private static final String FILE_NAME = "lunara_memory.txt";

    public static void saveMemory(Context context, String message) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write((message + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadMemory(Context context) {
        StringBuilder memory = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                memory.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memory.toString();
    }
}
