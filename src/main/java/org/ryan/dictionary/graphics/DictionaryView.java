package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.swing.*;

import java.awt.*;

import java.util.List;
import java.util.ArrayList;

@Log
public class DictionaryView extends JPanel {
  TextField field;
  WordAsset asset;

  List<Component> gui = new ArrayList<>();

  public DictionaryView() {
    field = new TextField();
    field.setPreferredSize(new Dimension(360, 30));
    field.setFont(Application.theme.getFont());
    field.addActionListener(e -> search(field.getText()));
    gui.add(field);

    String searchLabel = "Search";
    Button button = new Button(searchLabel);
    button.setPreferredSize(new Dimension(
        20 * searchLabel.length(),
        30
    ));
    button.setFont(Application.theme.getFont());
    button.setBackground(Application.theme.getBackground());
    button.setForeground(Application.theme.getHeader());
    button.addActionListener(e -> search(field.getText()));
    gui.add(button);

    add(field);
    add(button);
  }

  void display(WordAsset word) {
    this.asset = word;
    this.repaint();
  }

  void search(String text) {
    field.setEnabled(false);
    WordData data = DictionaryProcessor.fetchData(text);
    Application.yOffset = 0;
    Application.xOffset = 0;
    if (data != null) {
      WordAsset asset = new WordAsset(60, 60, data);
      this.display(asset);
    } else {
      log.severe("Something didn't work out during Search.");
      this.display(null);
    }
    field.setEnabled(true);
  }

  @Override
  public void paint(Graphics g) {
    Application app = Application.getInstance();
    g.setColor(Application.theme.getBackground());
    g.fillRect(0, 0, app.getWidth(), app.getHeight());
    if (asset != null) {
      gui.forEach(Component::repaint);
      asset.paint(g);
    }
  }
}
