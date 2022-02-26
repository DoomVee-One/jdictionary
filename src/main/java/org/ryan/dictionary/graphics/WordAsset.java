package org.ryan.dictionary.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ryan.dictionary.api.WordData;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.FontMetrics;

import java.util.List;
import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Log
public class WordAsset {
  static final int LINE_SPACING = 10;
  static int CHARS_PER_LINE = 64;

  int x, y;
  WordData data;

  public static List<String> wrap(String text) {
    List<String> lines = new ArrayList<>(List.of(""));
    for (String word : text.split(" ")) {
      int last = lines.size() - 1;
      int lineSize = lines.get(last).length();
      if (lineSize + word.length() + 1 < CHARS_PER_LINE) {
        lines.set(last, lines.get(last) + word + " ");
      } else {
        lines.add(word + " ");
      }
    }
    return lines;
  }

  public void paint(Graphics g) {
    int realY = y - Application.yOffset;
    int realX = x - Application.xOffset;
    int lines = 0;

    Font localFont = Application.theme.getFont().deriveFont(17f);
    g.setFont(localFont);
    FontMetrics metrics = g.getFontMetrics(localFont);
    int wordPrefix = metrics.charsWidth("Word: ".toCharArray(), 0, 6);
    int defPrefix = metrics.charsWidth("Def.: ".toCharArray(), 0, 6);
    int exPrefix = metrics.charsWidth("Ex.: ".toCharArray(), 0, 5);
    int height = metrics.getHeight();
    int avgWidth = metrics.charWidth('W');
    CHARS_PER_LINE = (int) (Application.getInstance().getWidth() * 1.0 / avgWidth);

    String word = data.getWord();
    WordData.WordPhonetic[] phonetics = data.getPhonetics();
    WordData.WordMeaning[] meanings = data.getMeanings();

    g.setColor(Application.theme.getHeader());
    g.drawString("Word: ", realX, realY);
    g.setColor(Application.theme.getText());
    g.drawString(word, wordPrefix + realX, realY);
    lines++;

    for (int i = 0; i < phonetics.length; i++) {
      String header = "Phonetics (%d): ";
      g.setColor(Application.theme.getHeader());
      g.drawString(String.format(header, i + 1), realX, realY + (height + LINE_SPACING) * lines);
      int prefix = metrics.charsWidth(header.toCharArray(), 0, header.length());
      g.setColor(Application.theme.getText());
      g.drawString(phonetics[i].getText(), prefix + realX, realY + (height + LINE_SPACING) * lines++);
    }

    for (int i = 0; i < meanings.length; i++) {
      g.setColor(Application.theme.getHeader());
      g.drawString(String.format("%d. Meaning", i + 1), realX, realY + (height + LINE_SPACING) * lines++);
      String header = "Part of Speech: ";
      g.drawString(header, realX + 20, realY + (height + LINE_SPACING) * lines);

      g.setColor(Application.theme.getText());
      int prefix = metrics.charsWidth(header.toCharArray(), 0, header.length());
      g.drawString(meanings[i].getPartOfSpeech(), prefix + realX + 20, realY + (height + LINE_SPACING) * lines++);

      WordData.WordDefinition[] definitions = meanings[i].getDefinitions();
      for (int j = 0; j < definitions.length; j++) {
        g.setColor(Application.theme.getHeader());
        g.drawString(String.format("%d. Definition", j + 1), realX + 20, realY + (height + LINE_SPACING) * lines++);

        List<String> wraps = wrap(definitions[j].getDefinition());
        String defHeader = "Def.: ";
        g.drawString(defHeader, realX + 40, realY + (height + LINE_SPACING) * lines);

        g.setColor(Application.theme.getText());
        for (String line : wraps) {
          g.drawString(line, realX + 40 + defPrefix, realY + (height + LINE_SPACING) * lines++);
        }

        if (definitions[j].getExample() != null) {
          wraps = wrap(definitions[j].getExample());
          String exHeader = "Ex.: ";
          g.setColor(Application.theme.getHeader());
          g.drawString(exHeader, realX + 40, realY + (height + LINE_SPACING) * lines);

          g.setColor(Application.theme.getText());
          for (String line : wraps) {
            g.drawString(line, realX + 40 + exPrefix, realY + (height + LINE_SPACING) * lines++);
          }
        }

        String[] synonyms = definitions[j].getSynonyms();
        if (synonyms == null || synonyms.length == 0) continue;
        g.setColor(Application.theme.getHeader());
        g.drawString("Synonyms", realX + 40, realY + (height + LINE_SPACING) * lines++);
        g.setColor(Application.theme.getText());
        for (String synonym : synonyms) {
          g.drawString("- " + synonym, realX + 60, realY + (height + LINE_SPACING) * lines++);
        }
      }
    }
  }
}
