package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import networking.DClient;
import networking.DServer;

import java.io.IOException;
import java.net.InetAddress;


public class MenuController {

  @FXML
  private JFXButton newGameButton;

  @FXML
  protected void hostLobbyScreen(ActionEvent e) throws IOException{
    Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
    Parent pane = FXMLLoader.load(Main.class.getResource("/views/lobby.fxml"));
    stage.getScene().setRoot(pane);
    DServer server = new DServer();
    DClient client = new DClient(InetAddress.getLocalHost().getHostAddress(), "Ur mum");
    client.joinGame();
  }
}
