package org.ryan.dictionary.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class WordData {
  String word;
  WordPhonetic[] phonetics;
  WordMeaning[] meanings;

  @AllArgsConstructor
  @ToString
  @Getter
  public static class WordPhonetic {
    String text;
    String audio;
  }

  @AllArgsConstructor
  @ToString
  @Getter
  public static class WordMeaning {
    String partOfSpeech;
    WordDefinition[] definitions;
  }

  @AllArgsConstructor
  @ToString
  @Getter
  public static class WordDefinition {
    String definition;
    String[] synonyms;
    String example;
  }
}
