package org.ryan.dictionary.graphics.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.ryan.dictionary.App;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;
import org.ryan.dictionary.graphics.view.WordView;

import java.util.Objects;

@Log4j2
public class PrimarySceneController {
  public static final Font FONT = Font.loadFont("file:src/main/resources/fonts/mononoki.ttf", 16);

  @FXML
  public ScrollPane resultPane;

  @FXML
  public TextField query;

  @FXML
  public Button send;

  @FXML
  public void initialize() {
    send.setFont(FONT);
    query.setFont(FONT);
  }

  @FXML
  public void handleSearchAction(ActionEvent actionEvent) {
    search();
  }

  private void search() {
    String queryString = query.getText();
    log.info("Searching for {}.", queryString);
    query.setDisable(true);
    send.setDisable(true);

    WordData data = DictionaryProcessor.fetchData(queryString);
    if(data == null) {
      showError(queryString);
    } else {
      resultPane.setContent(new WordView(data));
    }

    send.setDisable(false);
    query.setDisable(false);
  }

  private void showError(String queryString) {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(App.getInstance().getStage());
    VBox dialogBox = new VBox(20);
    dialogBox.getStyleClass().add("popup");
    Text text = new Text("Could not find %s in the dictionary!".formatted(queryString));
    text.setWrappingWidth(300);
    text.setFont(FONT);
    text.getStyleClass().add("error");
    dialogBox.getChildren().add(text);
    Scene dialogScene = new Scene(dialogBox, 400, 100);
    dialogScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/light.css")).toExternalForm());
    dialog.setScene(dialogScene);
    dialog.show();
  }

  public void handleInput(KeyEvent keyEvent) {
    if(keyEvent.getCode() == KeyCode.ENTER) {
      search();
    }
  }
}
