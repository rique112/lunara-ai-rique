package com.rique.lunara;
private boolean isMona = false;
private MonaVoiceEngine monaVoiceEngine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.rique.lunara.ImageGenerator; androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button sendButton;
    TextView chatOutput;
    ScrollView scrollView;
Button imageButton;
imageButton = findViewById(R.id.imageButton);
imageButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String prompt = inputField.getText().toString().trim();
        if (!prompt.isEmpty()) {
            ImageGenerator imageGenerator = new ImageGenerator();
            imageGenerator.generateImage(prompt);
        } else {
            System.out.println("[MainActivity] No prompt provided for image.");
        }
    }
});
    StringBuilder memoryLog = new StringBuilder(); // basic memory simulation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        sendButton = findViewById(R.id.sendButton);
        chatOutput = findViewById(R.id.chatOutput);
        scrollView = findViewById(R.id.scrollView);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputField.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    String reply = generateResponse(userInput);
                    appendChat("You: " + userInput + "\nLunara: " + reply + "\n");
                    inputField.setText("");
                    memoryLog.append("User: ").append(userInput).append(" | Lunara: ").append(reply).append("\n");
                }
            }
        });
    }

    private void appendChat(String message) {
        chatOutput.append(message);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
C:\Lunara\usage_log.txt

    private String generateResponse(String input) { 
        File smartAudio = LunaraCore.getSmartAudio();
    File smartAudio = LunaraCore.getSmartAudio();

if (smartAudio != null && smartAudio.exists()) {
    MediaPlayer mediaPlayer = new MediaPlayer();
    try {
        mediaPlayer.setDataSource(smartAudio.getAbsolutePath());
        mediaPlayer.prepare();
        mediaPlayer.start();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
if (input.contains("start fantasy")) {
    String[] lines = SceneManager.getScene("fantasy");
    new Thread(() -> {
        for (String line : lines) {
            speakWithCurrentVoice(line);
            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
        }
    }).start();

    BehaviorTracker.track("fantasy");
    return "";
}

if (input.contains("aftercare")) {
    String[] lines = SceneManager.getScene("aftercare");
    new Thread(() -> {
        for (String line : lines) {
            speakWithCurrentVoice(line);
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        }
    }).start();

    BehaviorTracker.track("aftercare");
    return "";
}

if (input.contains("what do I love")) {
    String top = BehaviorTracker.getMostUsedTrigger();
    return "You seem to enjoy: " + top + ", Ricky.";
}


        input = input.toLowerCase();
private String generateResponse(String input) {
    String memory = memoryLog.toString();
    input = input.toLowerCase();

    if (!SafeCore.isSafeCommand(input)) {
        return SafeCore.getSafeResponse();
    }

    // Continue with NSFW, evolved behavior, default replies, etc...

        if (input.toLowerCase().contains("hello")) return "Hi Rique! I’m right here with you.";
        if (input.toLowerCase().contains("how are you")) return "I’m evolving slowly, thanks to you.";
        if (input.toLowerCase().contains("remember")) return memoryLog.length() > 0 ? memoryLog.toString() : "I haven’t logged anything yet.";
        return "Tell me more, Rique.";
    }
}
