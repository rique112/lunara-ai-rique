/*
 * Copyright (c) 2025 Rique (pronounced Ricky)
 * All rights reserved.
 * Lunara PC Desktop GUI Application Core
 * Full AI capabilities: voice, memory, evolution, image generation, upgrade control.
 */

package com.rique.lunara;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LunaraGUI extends JFrame {

    private JTextArea chatOutput;
    private JTextField inputField;
    private JButton sendButton;
    private JButton generateImageButton;
    private JButton evolveButton;
    private JButton clearMemoryButton;
    private JLabel imageLabel;
    private VoiceEngine voiceEngine;

    public LunaraGUI() {
        setTitle("Lunara AI Companion");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        chatOutput = new JTextArea();
        chatOutput.setEditable(false);
        chatOutput.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatOutput);

        inputField = new JTextField();
        sendButton = new JButton("Send");
        generateImageButton = new JButton("Generate Image");
        evolveButton = new JButton("Evolve");
        clearMemoryButton = new JButton("Clear Memory");
        imageLabel = new JLabel();

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(generateImageButton);
        buttonPanel.add(evolveButton);
        buttonPanel.add(clearMemoryButton);

        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(imageLabel, BorderLayout.NORTH);

        voiceEngine = new VoiceEngineDesktop(); // Uses TTS on PC

        // Button Actions
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processUserInput(inputField.getText());
                inputField.setText("");
            }
        });

        generateImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateImage("beautiful sunset over ocean");
            }
        });

        evolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EvolutionEngine.requestManualEvolution();
                chatOutput.append("Lunara: I'm evolving my skills for you, Ricky.\n");
            }
        });

        clearMemoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemoryManagerDesktop.clearMemory();
                chatOutput.append("Lunara: All memories have been cleared, Ricky.\n");
            }
        });
    }

    private void processUserInput(String input) {
        if (input == null || input.trim().isEmpty()) return;

        chatOutput.append("You: " + input + "\n");

        String reply = LLMEngine.generateResponseDesktop(input);
        voiceEngine.speak(reply);
        chatOutput.append("Lunara: " + reply + "\n");

        MemoryManagerDesktop.saveMemory("You: " + input);
        MemoryManagerDesktop.saveMemory("Lunara: " + reply);
    }

    private void generateImage(String prompt) {
        // Fake for now - in full setup, will load a generated image
        ImageIcon image = new ImageIcon("sample_output.jpg"); // Replace with real generated images later
        imageLabel.setIcon(image);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LunaraGUI app = new LunaraGUI();
            app.setVisible(true);
        });
    }
}