package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.control.MouseHandler;

import javax.swing.*;

@Log
public class Application extends JFrame {
  private static Application instance;
  public static int yOffset = 0;
  public static int xOffset = 0;
  public static Theme theme = Theme.ONE_DARK;

  public static Application getInstance() {
    if(instance == null) {
      instance = new Application();
    }
    return instance;
  }

  private Application() {
    super("Dictionary Viewer");
    setSize(576, 720);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    //setLocationRelativeTo(null);
    add(new DictionaryView());
    addMouseWheelListener(new MouseHandler());
    setResizable(true);
    setVisible(true);
  }

  public static void main(String[] args) {
    Application.getInstance();
  }
}
