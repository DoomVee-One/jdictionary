package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.TextField;

import java.util.List;
import java.util.ArrayList;

@Log
public class DictionaryView extends JPanel {
  TextField field;
  WordAsset asset;

  List<Component> gui = new ArrayList<>();

  public DictionaryView() {
    field = new TextField();
    field.setPreferredSize(new Dimension(200, 25));
    field.setFont(new Font("Times New Roman", Font.PLAIN, 17));
    field.addActionListener(e -> search(field.getText()));
    gui.add(field);

    Button button = new Button("Search");
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
      field.setEnabled(true);
    } else {
      log.severe("Something didn't work out during Search.");
    }
  }

  @Override
  public void paint(Graphics g) {
    g.clearRect(0, 0, getWidth(), getHeight());
    if (asset != null) {
      gui.forEach(Component::repaint);
      asset.paint(g);
    }
  }
}
