package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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


public class HostJoinController {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField ip;

    @FXML
    protected void hostGame() throws IOException{
        Main.client = new DClient(InetAddress.getLocalHost().getHostAddress(), username.getText());
        Main.client.joinGame();
    }
    @FXML
    protected void joinGame() throws IOException{
        Main.client = new DClient(ip.getText(), username.getText());
        Main.client.joinGame();
    }
    public static void displayGameJoinFailed(){

    }
    public static void displayGameJoinSucceeded(){
        try {
            Stage stage = Main.primaryStage;
            Parent pane = FXMLLoader.load(Main.class.getResource("/views/lobby.fxml"));
            stage.getScene().setRoot(pane);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}
