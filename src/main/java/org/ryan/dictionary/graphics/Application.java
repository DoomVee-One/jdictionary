package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.control.MouseHandler;

import javax.swing.*;

@Log
public class Application extends JFrame {
    public static Application app;
    public static int yOffset = 0;
    public static int xOffset = 0;

    public Application() {
        super("Dictionary Viewer");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new DictionaryView());
        addMouseWheelListener(new MouseHandler());
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        app = new Application();
    }
}
