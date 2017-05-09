package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;



public class MenuController {

  @FXML
  private JFXButton newGameButton;

  @FXML
  protected void goElectrical(ActionEvent e) throws IOException{
    Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
    // these two of them return the same stage
    // Swap screen
    Parent pane = FXMLLoader.load(Main.class.getResource("/views/lobby.fxml"));
    stage.getScene().setRoot(pane);
  }
}
