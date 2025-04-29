/*

Copyright (c) 2025 Rique (pronounced Ricky)

All rights reserved.

Lunara AI CommandCenter.java

Gives you real control buttons: save memory, learn, analyze, upgrade, forget, shut down.

This file connects to MemoryManager, GrowthTracker, AnalyzerEngine, and SelfUpgradeManager.

Trademark: Lunara™ — evolving AI assistant created and protected by Rique. */


package com.rique.lunara;

import android.app.Activity; import android.os.Bundle; import android.view.View; import android.widget.Button; import android.widget.EditText; import android.widget.Toast;

public class CommandCenter extends Activity {

private EditText inputField;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.command_center);

    inputField = findViewById(R.id.inputField);

    Button saveMemoryBtn = findViewById(R.id.btnSaveMemory);
    Button analyzeEssayBtn = findViewById(R.id.btnAnalyzeEssay);
    Button learnBtn = findViewById(R.id.btnLearn);
    Button upgradeBtn = findViewById(R.id.btnUpgrade);
    Button growthSummaryBtn = findViewById(R.id.btnGrowth);
    Button forgetBtn = findViewById(R.id.btnForget);
    Button shutdownBtn = findViewById(R.id.btnShutdown);

    saveMemoryBtn.setOnClickListener(v -> {
        String text = inputField.getText().toString();
        MemoryManager.saveMemory(this, "Note", text);
        Toast.makeText(this, "Memory saved.", Toast.LENGTH_SHORT).show();
    });

    analyzeEssayBtn.setOnClickListener(v -> {
        String text = inputField.getText().toString();
        AnalyzerEngine.analyzeEssay(this, text);
        Toast.makeText(this, "Essay analyzed.", Toast.LENGTH_SHORT).show();
    });

    learnBtn.setOnClickListener(v -> {
        String topic = inputField.getText().toString();
        GrowthTracker.recordLearningEvent(this, topic);
        Toast.makeText(this, "Learning recorded.", Toast.LENGTH_SHORT).show();
    });

    upgradeBtn.setOnClickListener(v -> {
        if (PermissionFlags.upgradeAllowed) {
            String idea = inputField.getText().toString();
            SelfUpgradeManager.proposeNewCode(this, idea, "upgrade_" + System.currentTimeMillis() + ".txt");
            Toast.makeText(this, "Upgrade idea saved.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Upgrades are blocked.", Toast.LENGTH_SHORT).show();
        }
    });

    growthSummaryBtn.setOnClickListener(v -> {
        GrowthTracker.displayGrowthSummary(this);
    });

    forgetBtn.setOnClickListener(v -> {
        MemoryManager.clearAllMemories(this);
        Toast.makeText(this, "All memories deleted!", Toast.LENGTH_LONG).show();
    });

    shutdownBtn.setOnClickListener(v -> {
        PermissionFlags.emergencyShutdown = true;
        Toast.makeText(this, "Emergency shutdown activated!", Toast.LENGTH_LONG).show();
    });
}

}

