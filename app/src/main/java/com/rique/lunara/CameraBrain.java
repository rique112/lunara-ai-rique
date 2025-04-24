/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 *
 * This file is part of the Lunara AI system.
 * No part of this code may be copied, modified, distributed, or used
 * without explicit permission from the creator.
 */

package com.rique.lunara;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraBrain {

    private static final int CAMERA_REQUEST_CODE = 123;
    private static final Executor cameraExecutor = Executors.newSingleThreadExecutor();
    private static boolean isRunning = false;

    public static void startCamera(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            launchCamera(activity);
        }
    }

    private static void launchCamera(Activity activity) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(activity);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        // Placeholder: You can add image processing, OCR, or object recognition here
                        Log.d("CameraBrain", "Frame received: " + image.getWidth() + "x" + image.getHeight());
                        image.close();  // Important to avoid memory leak
                    }
                });

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) activity, CameraSelector.DEFAULT_BACK_CAMERA, imageAnalysis);
                isRunning = true;
                Toast.makeText(activity, "Lunara is watching through the camera...", Toast.LENGTH_SHORT).show();

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(activity));
    }

    public static boolean isCameraActive() {
        return isRunning;
    }

    public static void stopCamera() {
        isRunning = false;
        // In full app, you would add shutdown logic here.
    }
}