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
  static final int PADDING = 20;

  int x, y;
  WordData data;

  public static List<String> wrap(int prefixChars, String text) {
    List<String> lines = new ArrayList<>(List.of(""));
    for (String word : text.split(" ")) {
      int last = lines.size() - 1;
      int lineSize = lines.get(last).length();
      if (PADDING + prefixChars + lineSize + word.length() + 1 < CHARS_PER_LINE) {
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

    Font localFont = Application.theme.getFont();
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
      g.drawString(header, realX + PADDING, realY + (height + LINE_SPACING) * lines);

      g.setColor(Application.theme.getText());
      int prefix = metrics.charsWidth(header.toCharArray(), 0, header.length());
      g.drawString(meanings[i].getPartOfSpeech(), prefix + realX + PADDING, realY + (height + LINE_SPACING) * lines++);

      WordData.WordDefinition[] definitions = meanings[i].getDefinitions();
      for (int j = 0; j < definitions.length; j++) {
        g.setColor(Application.theme.getHeader());
        g.drawString(String.format("%d. Definition", j + 1), realX + PADDING, realY + (height + LINE_SPACING) * lines++);

        List<String> wraps = wrap((int) ((realX + 40.0 + defPrefix) / avgWidth), definitions[j].getDefinition());
        String defHeader = "Def.: ";
        lines = drawWrappedData(g, realY, realX, lines, defPrefix, height, wraps, defHeader);

        if (definitions[j].getExample() != null) {
          wraps = wrap((int) ((realX + 40.0 + exPrefix) / avgWidth), definitions[j].getExample());
          String exHeader = "Ex.: ";
          g.setColor(Application.theme.getHeader());
          lines = drawWrappedData(g, realY, realX, lines, exPrefix, height, wraps, exHeader);
        }

        String[] synonyms = definitions[j].getSynonyms();
        if (synonyms == null || synonyms.length == 0) continue;
        g.setColor(Application.theme.getHeader());
        g.drawString("Synonyms", realX + 2*PADDING, realY + (height + LINE_SPACING) * lines++);
        g.setColor(Application.theme.getText());
        for (String synonym : synonyms) {
          g.drawString("- " + synonym, realX + 3*PADDING, realY + (height + LINE_SPACING) * lines++);
        }
      }
    }
  }

  private int drawWrappedData(Graphics g, int realY, int realX, int lines, int exPrefix, int height, List<String> wraps, String exHeader) {
    g.drawString(exHeader, realX + 2*PADDING, realY + (height + LINE_SPACING) * lines);

    g.setColor(Application.theme.getText());
    for (String line : wraps) {
      g.drawString(line, realX + 2*PADDING + exPrefix, realY + (height + LINE_SPACING) * lines++);
    }
    return lines;
  }
}
