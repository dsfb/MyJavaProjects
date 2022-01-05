package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TextEditor extends JFrame {
    private JPanel windowContent = new JPanel();
    private JTextArea textArea = null;
    private JTextField textField = null;

    public void save() {
        File file = new File(textField.getText());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(textArea.getText());
        } catch (IOException e) {
        }
    }

    public void load() {
        try {
            textArea.setText(new String(Files.readAllBytes(Paths.get(textField.getText()))));
        } catch (IOException e) {
            textArea.setText("");
        }
    }

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windowContent.setSize(new Dimension(100, 100));
        this.add(this.windowContent, BorderLayout.NORTH);

        setSize(400, 400);
        setVisible(true);

        textField = new JTextField();
        textField.setName("FilenameField");
        textField.setPreferredSize(new Dimension(200, 25));
        this.windowContent.add(textField);

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.setSize(new Dimension(100, 25));
        this.windowContent.add(saveButton);

        textArea = new JTextArea(150, 50);
        textArea.setName("TextArea");

        saveButton.addActionListener(actionEvent -> this.save());

        JButton loadButton = null;
        try {
            InputStream stream = getClass().getResourceAsStream("./open-file.png");
            ImageIcon loadIcon = new ImageIcon(ImageIO.read(stream));
            loadButton = new JButton(loadIcon);
        } catch (Exception e) {
            System.out.println(e);
            loadButton = new JButton("Load");
        }
        loadButton.setName("LoadButton");
        loadButton.setSize(new Dimension(100, 25));
        this.windowContent.add(loadButton);
        loadButton.addActionListener(actionEvent -> this.load());

        JPanel panel = new JPanel();
        panel.setSize(new Dimension(150, 150));
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setName("ScrollPane");
        panel.add(textArea);
        this.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem load = new JMenuItem("Load");
        load.setName("MenuLoad");
        fileMenu.add(load);
        load.addActionListener(actionEvent -> this.load());

        JMenuItem save = new JMenuItem("Save");
        save.setName("MenuSave");
        fileMenu.add(save);
        save.addActionListener(actionEvent -> this.save());

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        fileMenu.add(exit);
        exit.addActionListener(actionEvent -> System.exit(0));

        this.setVisible(true);
    }
}
