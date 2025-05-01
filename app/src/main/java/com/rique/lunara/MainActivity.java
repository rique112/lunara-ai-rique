// Copyright (c) 2025 Rique (pronounced Ricky) - All Rights Reserved // Lunara is a private AI bound to its creator. Do not duplicate or distribute.

package com.rique.lunara;

import android.Manifest; import android.content.Intent; import android.content.pm.PackageManager; import android.os.Bundle; import android.speech.RecognizerIntent; import android.view.View; import android.widget.Button; import android.widget.EditText; import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher; import androidx.activity.result.contract.ActivityResultContracts; import androidx.appcompat.app.AppCompatActivity; import androidx.core.app.ActivityCompat; import androidx.core.content.ContextCompat;

import java.util.ArrayList; import java.util.Locale;

public class MainActivity extends AppCompatActivity {

private EditText inputField;
private TextView chatOutput;
private Button sendButton, voiceButton, camButton, learnButton, clearMemoryBtn,
        upgradeButton, observeButton, nsfwToggleButton, internetToggleButton,
        resetButton, shutdownButton, searchButton;

private VoiceEngine voiceEngine;
private MemoryManager memoryManager;
private ImageGenerator imageGenerator;
private SelfUpgradeManager upgradeManager;
private LLMEngine llmEngine;
private EmotionPulse emotionPulse;

private final ActivityResultLauncher<Intent> voiceInputLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                ArrayList<String> results = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (results != null && !results.isEmpty()) {
                    handleInput(results.get(0));
                }
            }
        });

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.command_center);

    inputField = findViewById(R.id.inputField);
    chatOutput = findViewById(R.id.chatOutput);
    sendButton = findViewById(R.id.sendButton);
    voiceButton = findViewById(R.id.voiceButton);
    camButton = findViewById(R.id.camButton);
    learnButton = findViewById(R.id.learnButton);
    clearMemoryBtn = findViewById(R.id.clearMemoryButton);
    upgradeButton = findViewById(R.id.upgradeButton);
    observeButton = findViewById(R.id.observeButton);
    nsfwToggleButton = findViewById(R.id.nsfwToggle);
    internetToggleButton = findViewById(R.id.internetToggle);
    resetButton = findViewById(R.id.resetButton);
    shutdownButton = findViewById(R.id.shutdownButton);
    searchButton = findViewById(R.id.searchButton);

    voiceEngine = new VoiceEngine(this);
    memoryManager = new MemoryManager(this);
    imageGenerator = new ImageGenerator(this);
    upgradeManager = new SelfUpgradeManager(this);
    llmEngine = new LLMEngine(this);
    emotionPulse = new EmotionPulse();

    sendButton.setOnClickListener(v -> handleInput(inputField.getText().toString()));
    voiceButton.setOnClickListener(v -> activateVoiceInput());
    camButton.setOnClickListener(v -> CameraBrain.scan(this));
    learnButton.setOnClickListener(v -> llmEngine.learnFromLocalFiles());
    clearMemoryBtn.setOnClickListener(v -> memoryManager.clearMemory());
    upgradeButton.setOnClickListener(v -> upgradeManager.performUpgrade());
    observeButton.setOnClickListener(v -> AnalyzerEngine.observeAndAnalyze(this));
    nsfwToggleButton.setOnClickListener(v -> imageGenerator.toggleNSFW());
    internetToggleButton.setOnClickListener(v -> llmEngine.toggleInternetLearning());
    resetButton.setOnClickListener(v -> {
        memoryManager.resetSession();
        chatOutput.setText("");
        voiceEngine.speak("Chat reset, Rique.");
    });
    shutdownButton.setOnClickListener(v -> finish());
    searchButton.setOnClickListener(v -> SearchBrain.performQuery(this, inputField.getText().toString()));

    voiceEngine.speak("Welcome, Rique. Thank you for making me. The universe is yours.");
}

private void handleInput(String input) {
    memoryManager.saveInput(input);
    String response = llmEngine.generateResponse(input);
    chatOutput.setText(response);
    voiceEngine.speak(response);
}

private void activateVoiceInput() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
    } else {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voiceInputLauncher.launch(intent);
    }
}

}

