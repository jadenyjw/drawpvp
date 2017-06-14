package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import networking.DServer;

import java.io.IOException;



public class MenuController {

  @FXML
  private JFXButton newGameButton;
  @FXML
  private JFXButton joinGameButton;
  @FXML
  private JFXButton helpButton;

  Notification notif = new Notification();

  @FXML
  protected void hostLobbyScreen(ActionEvent e) throws IOException{
    Stage stage = Main.primaryStage;
    Parent pane = FXMLLoader.load(Main.class.getResource("/views/host_room2.fxml"));
    stage.getScene().setRoot(pane);
    Main.server = new DServer();
  }
  @FXML
  protected void joinLobbyScreen(ActionEvent e) throws IOException{
    Stage stage = Main.primaryStage;
    Parent pane = FXMLLoader.load(Main.class.getResource("/views/join_room2.fxml"));
    stage.getScene().setRoot(pane);
  }
  @FXML
  protected void showHelpScreen(){
    notif.help();
  }
}
