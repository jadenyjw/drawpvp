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
    private JFXButton backButton;


    @FXML
    protected void hostGame() throws IOException{
        if(username.getText().length() > 0 && username.getText().length() <= 20) {
            Main.isHost = true;
            Main.client = new DClient(InetAddress.getLocalHost().getHostAddress(), username.getText());
            Main.client.joinGame();
        }
    }
    @FXML
    protected void joinGame() throws IOException{

        if(username.getText().length() > 0 && username.getText().length() <= 20) {
            Main.isHost = false;
            Main.client = new DClient(ip.getText(), username.getText());
            Main.client.joinGame();
        }
    }
    @FXML
    protected void goBack() {
        if(Main.server != null){
            Main.server.close();
            Main.server = null;
        }
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/main_menu2.fxml"));
            Parent root = loader.load();
            Main.primaryStage.getScene().setRoot(root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void displayGameJoinFailed(){


    }

    public static void displayGameJoinSucceeded(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/lobby.fxml"));
            Parent root = loader.load();
            Main.lobby = loader.getController();
            Main.primaryStage.getScene().setRoot(root);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }




}
