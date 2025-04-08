package org.arshpsps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main extends JFrame implements ActionListener {
    private final JMenuBar menuBar;
    private final JTextArea textArea;
    private File file;
    private final JFileChooser fileChooser;

    public Main(String windowTitle) {
        super(windowTitle);
        setLayout(new GridLayout(1, 1));

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        menuBar = new JMenuBar();

        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiSaveAs = new JMenuItem("Save As");
        JMenuItem jmiExit = new JMenuItem("Exit");

        jmiOpen.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiSaveAs.addActionListener(this);
        jmiExit.addActionListener(this);

        JMenu jm = new JMenu("File");
        jm.add(jmiOpen);
        jm.add(jmiSave);
        jm.add(jmiSaveAs);
        jm.add(jmiExit);

        menuBar.add(jm);
        setJMenuBar(menuBar);

        textArea = new JTextArea();
        textArea.setLineWrap(Boolean.TRUE);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        textArea.setFont(new Font("Fira Code", Font.BOLD, 18));
    }

    public static void main(String[] args) {
        JFrame frame = new Main("Totally a Text Editor");
        frame.setVisible(Boolean.TRUE);
        frame.setSize(1400, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Open" -> {
                fileChooser.showOpenDialog(this);
                file = fileChooser.getSelectedFile();
                byte[] stream = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(stream);
                } catch (Exception e) {
                    System.out.println("File not found");
                    System.exit(1);
                }
                textArea.setText(new String(stream));
            }

            case "Save", "Save As" -> {
                if (file == null || ae.getActionCommand().equals("Save As")) {
                    fileChooser.showOpenDialog(this);
                    file = fileChooser.getSelectedFile();
                }
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(textArea.getText().getBytes());
                } catch (Exception e) {
                    System.out.println("File not found");
                    System.exit(1);
                }
            }

            case "Exit" -> {
                if (gracefulExit()) {
                    System.exit(0);
                } //TODO: else throw shit and exit or smthin
            }
        }
    }

    private boolean gracefulExit() {
        System.out.println("Graceful Exit");
        return Boolean.TRUE;
    }
}