package org.ryan.dictionary.graphics.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.ryan.dictionary.api.WordData;
import org.ryan.dictionary.graphics.controller.PrimarySceneController;

import java.util.Arrays;

public class WordView extends VBox {
  private static final int SHIFT_WIDTH = 30;
  WordData data;

  public WordView(WordData data) {
    this.data = data;
    /* Word */
    this.getChildren().add(makeKeyValue("Word", this.data.getWord()));
    /* Phonetics */
    Arrays.stream(this.constructPhonetics()).forEach(this.getChildren()::add);
    /* Meanings */
    this.getChildren().add(constructMeanings());
  }

  private HBox[] constructPhonetics() {
    WordData.WordPhonetic[] dataPhonetics = this.data.getPhonetics();
    HBox[] phonetics = new HBox[dataPhonetics.length];

    for (int i = 0; i < dataPhonetics.length; i++) {
      WordData.WordPhonetic phonetic = dataPhonetics[i];
      phonetics[i] = makeKeyValue("Phonetics (%d)".formatted(i + 1), phonetic.getText());
      Label special = (Label) phonetics[i].getChildren().get(1);
      special.setFont(Font.font("Times New Roman", 16));
    }
    return phonetics;
  }

  private VBox constructMeanings() {
    VBox meanings = new VBox();
    WordData.WordMeaning[] dataMeanings = this.data.getMeanings();
    for (int i = 0; i < dataMeanings.length; i++) {
      WordData.WordMeaning meaning = dataMeanings[i];
      Label title = new Label((i + 1) + ". Meaning");
      title.getStyleClass().add("keyword");
      adjustFont(title);
      meanings.getChildren().add(title);
      meanings.getChildren().add(constructMeaning(meaning));
    }
    return meanings;
  }

  private static VBox constructMeaning(WordData.WordMeaning meaning) {
    VBox meaningNode = new VBox();
    meaningNode.setPadding(new Insets(0, 0, 0, SHIFT_WIDTH));
    meaningNode.getChildren().add(makeKeyValue("Part of Speech", meaning.getPartOfSpeech()));
    WordData.WordDefinition[] definitions = meaning.getDefinitions();
    for(int i = 0; i < definitions.length; i++) {
      Label title = new Label((i + 1) + ". Definition");
      title.getStyleClass().add("keyword");
      adjustFont(title);
      meaningNode.getChildren().add(title);
      meaningNode.getChildren().add(constructDefinition(definitions[i]));
    }
    return meaningNode;
  }

  private static VBox constructDefinition(WordData.WordDefinition definition) {
    VBox definitionNode = new VBox();
    definitionNode.setPadding(new Insets(0, 0, 0, SHIFT_WIDTH));
    definitionNode.getChildren().add(makeKeyValue("Def.", definition.getDefinition()));
    if(definition.getExample() != null) {
      definitionNode.getChildren().add(makeKeyValue("Ex.", definition.getExample()));
    }
    VBox synonyms = new VBox();
    for(String synonym : definition.getSynonyms()) {
      Label syn = new Label("- " + synonym);
      syn.getStyleClass().add("normal");
      adjustFont(syn);
      synonyms.getChildren().add(syn);
    }
    definitionNode.getChildren().add(synonyms);
    return definitionNode;
  }

  private static HBox makeKeyValue(String key, String value) {
    HBox entry = new HBox();
    Label keyNode = new Label(key + ": ");
    keyNode.getStyleClass().add("keyword");
    keyNode.setWrapText(false);
    adjustFont(keyNode);
    entry.getChildren().add(keyNode);
    Label valueNode = new Label(value);
    valueNode.setWrapText(true);
    valueNode.setTextAlignment(TextAlignment.JUSTIFY);
    valueNode.setMaxWidth(400);
    valueNode.getStyleClass().add("normal");
    adjustFont(valueNode);
    entry.getChildren().add(valueNode);
    return entry;
  }

  public static void adjustFont(Labeled label) {
    label.setFont(PrimarySceneController.FONT);
  }
}
