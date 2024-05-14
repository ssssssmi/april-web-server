package ru.otus.smi.web.server.others;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Notepad {
    public static void main(String[] args) {
        JFrame window = new JFrame("Notepad J++");

        window.setBounds(800, 400, 400, 400);

        TextArea textArea = new TextArea();
        window.getContentPane().add(textArea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem fileMenuItemCreate =  new JMenuItem("Новый файл");
        JMenuItem fileMenuItemSave =  new JMenuItem("Сохранить файл");
        JMenuItem fileMenuItemExit =  new JMenuItem("Выйти");

        fileMenuItemExit.addActionListener((e) -> System.exit(0));
        fileMenuItemCreate.addActionListener((e) -> {
            textArea.setText("");
        });
        fileMenuItemSave.addActionListener((e) -> {
            try {
                String filename = JOptionPane.showInputDialog("Выберите имя файла");
                Files.write(Path.of(filename), textArea.getText().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
                JOptionPane.showMessageDialog(null, "Файл сохранен: " + filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        fileMenu.add(fileMenuItemCreate);
        fileMenu.add(fileMenuItemSave);
        fileMenu.add(fileMenuItemExit);
        menuBar.add(fileMenu);
        window.setJMenuBar(menuBar);

        window.setVisible(true);
    }
}
