package org.ryan.dictionary.graphics;

import com.google.common.io.Resources;

import lombok.Getter;
import lombok.extern.java.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SuppressWarnings("UnstableApiUsage")
@Getter
@Log
public enum Theme {
  ONE_DARK("mononoki", 0x282c34, 0x61afef, 0xabb2bf);

  private Font font;
  private final Color background, header, text;
  Theme(String font, int background, int header, int text) {
    try {
      String path = "fonts/" + font + ".ttf";
      URL url = Resources.getResource(path);
      URI uri = url.toURI();
      File file = new File(uri);
      this.font = Font.createFont(Font.TRUETYPE_FONT, file);
    } catch(IOException | URISyntaxException | FontFormatException e) {
      System.err.println("Error: Something happened! Check below:");
      e.printStackTrace();
    }

    this.background = new Color(background);
    this.header = new Color(header);
    this.text = new Color(text);
  }
}
