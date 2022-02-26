package org.ryan.dictionary.graphics;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordAssetTest {

  @Test
  void wrap() {
    String longLine = "This is a very long line, which contains over 200 symbols, hopefully! " +
        "Let's make this line longer, by continuing to type for another 10 seconds, in order to surpass the char limit. " +
        "The line, should soon be too long to be preserved in one.";

    List<String> expected = List.of(
        "This is a very long line, which contains over 200 symbols, hopefully! Let's make this line longer, by continuing to type for ",
        "another 10 seconds, in order to surpass the char limit. The line, should soon be too long to be preserved in one. ");

    assertLinesMatch(expected, WordAsset.wrap(longLine));
  }
}