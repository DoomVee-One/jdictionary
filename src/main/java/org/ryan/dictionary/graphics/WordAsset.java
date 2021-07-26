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
    static final Font font = new Font("Times New Roman", Font.PLAIN, 17);
    static final int LINE_SPACING = 10;
    static int CHARS_PER_LINE = 64;

    int x, y;
    WordData data;

    public static List<String> wrap(String text) {
        List<String> lines = new ArrayList<>(List.of(""));
        for(String word : text.split(" ")) {
            int last = lines.size() - 1;
            int lineSize = lines.get(last).length();
            if(lineSize + word.length() + 1 < CHARS_PER_LINE) {
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

        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int defPrefix = metrics.charsWidth("Def.: ".toCharArray(), 0, 6);
        int exPrefix = metrics.charsWidth("Ex.: ".toCharArray(), 0, 5);
        int height = metrics.getHeight();
        int avgWidth = metrics.charWidth('W');
        CHARS_PER_LINE = (int) (Application.app.getWidth() * 1.0/ avgWidth);

        String word = data.getWord();
        WordData.WordPhonetic[] phonetics = data.getPhonetics();
        WordData.WordMeaning[] meanings = data.getMeanings();

        g.drawString("Word: " + word, realX, realY);
        lines++;

        for(int i = 0; i < phonetics.length; i++) {
            g.drawString(String.format("Phonetics (%d): %s", i+1, phonetics[i].getText()), realX, realY + (height + LINE_SPACING) * lines++);
        }

        for(int i = 0; i < meanings.length; i++) {
            g.drawString(String.format("%d. Meaning", i+1), realX, realY + (height + LINE_SPACING) * lines++);
            g.drawString(String.format("Part of Speech: %s", meanings[i].getPartOfSpeech()), realX + 20, realY + (height + LINE_SPACING) * lines++);

            WordData.WordDefinition[] definitions = meanings[i].getDefinitions();
            for(int j = 0; j < definitions.length; j++) {
                g.drawString(String.format("%d. Definition", j+1), realX + 20, realY + (height + LINE_SPACING) * lines++);

                List<String> wraps = wrap(definitions[j].getDefinition());
                g.drawString(String.format("Def.: %s", wraps.remove(0)), realX + 40, realY + (height + LINE_SPACING) * lines++);
                for(String line : wraps) {
                    g.drawString(line, realX + 40 + defPrefix, realY + (height + LINE_SPACING) * lines++);
                }

                if(definitions[j].getExample() != null) {
                    wraps = wrap(definitions[j].getExample());
                    g.drawString(String.format("Ex.: %s", wraps.remove(0)), realX + 40, realY + (height + LINE_SPACING) * lines++);
                    for(String line : wraps) {
                        g.drawString(line, realX + 40 + exPrefix, realY + (height + LINE_SPACING) * lines++);
                    }
                }

                String[] synonyms = definitions[j].getSynonyms();
                if(synonyms == null || synonyms.length == 0) continue;
                g.drawString("Synonyms", realX + 40, realY + (height + LINE_SPACING) * lines++);
                for (String synonym : synonyms) {
                    g.drawString("- " + synonym, realX + 60, realY + (height + LINE_SPACING) * lines++);
                }
            }
        }
    }
}
