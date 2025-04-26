/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * LunaraGUI - Main User Interface for Story + Image Generation
 * Smart real AI Image, Video, and (future) Song generator.
 */

package com.rique.lunara.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rique.lunara.R;
import com.rique.lunara.image.ImageGenerator;

public class LunaraGUI extends Activity {

    private EditText storyInput;
    private TextView storyLog;
    private Button sendButton, generateImageButton;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunara_gui);

        storyInput = findViewById(R.id.inputField);
        storyLog = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.sendButton);
        generateImageButton = findViewById(R.id.generateImageButton);
        imageView = findViewById(R.id.generatedImage);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = storyInput.getText().toString();
                storyLog.append("\nYou: " + userText);
                storyInput.setText("");
            }
        });

        generateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentStory = storyLog.getText().toString();
                if (!currentStory.isEmpty()) {
                    String modelName = ImageGenerator.chooseModelBasedOnContext(currentStory);
                    ImageGenerator.initialize(LunaraGUI.this, modelName);
                    Bitmap generatedBitmap = ImageGenerator.generateImage(currentStory);

                    if (generatedBitmap != null) {
                        imageView.setImageBitmap(generatedBitmap);
                    } else {
                        storyLog.append("\n[Lunara]: Failed to generate image.");
                    }
                }
            }
        });
    }
}
