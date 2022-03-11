package org.ryan.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;

@Log4j2
@Getter
public class App extends Application {
  private static App instance;
  private Stage stage;
  public static final int WIDTH = 600;
  public static final int HEIGHT = 800;

  public static void main(String[] args) {
    launch(args);
  }

  public static Parent loadFXML(String name) {
    String path = "scenes/%s.fxml".formatted(name);
    URL url = App.class.getClassLoader().getResource(path);
    Parent parent = null;
    if(url != null) {
      try {
        parent = FXMLLoader.load(url);
      } catch(IOException exception) {
        log.fatal("Could not load FXML {}! Aborting Process!", url.toString());
        System.exit(1);
      }
    } else {
      log.fatal("Could not find valid resource in: src/main/resources/scenes/{}.fxml", name);
    }
    return parent;
  }

  @Override
  public void start(Stage stage) {
    instance = this;

    Parent root = loadFXML("primary");
    if(root == null) {
      throw new RuntimeException("Failed to load the FXML!");
    }
    Scene primary = new Scene(root, WIDTH, HEIGHT);

    stage.setTitle("Dictionary");
    stage.setScene(primary);
    stage.show();
  }

  public static App getInstance() {
    return instance;
  }
}
