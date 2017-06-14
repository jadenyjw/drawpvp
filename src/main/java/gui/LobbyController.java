package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.ResourceBundle;


public class LobbyController implements Initializable{


    @FXML
    protected JFXTextArea chatArea;
    @FXML
    protected JFXTextField chat;
    @FXML
    protected JFXListView<String> playersView;
    @FXML
    protected JFXButton startButton;
    public Notification lobbyAlerts = new Notification();

    public void showlobbyMessage(String title, String message){
        Platform.runLater(new Runnable() {
            public void run() {
                lobbyAlerts.display(title,message);
            }
        });}


    ObservableList<String> items = FXCollections.observableArrayList();

    public void displayChatMessage(final String message){
        Platform.runLater(new Runnable() {
            public void run() {
                chatArea.appendText(message + '\n');
            }
        });

    }

    public void playersUpdate(final String[] players){

      Platform.runLater(new Runnable() {
            public void run() {
                items.clear();
                for(String s : players){
                    items.add(s);
                }
                playersView.setItems(items);
            }
        });
    }

    public void initGame(){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/draw_room.fxml"));
            Pane root = loader.load();
            Main.game = loader.getController();
            Main.primaryStage.getScene().setRoot(root);
            Platform.runLater(new Runnable() {
                public void run() {
                    lobbyAlerts.display("Welcome!","The game has started");
                }
            });


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void startGame(){
        Main.client.startGame();
    }
    @FXML
    public void onEnter(){

        if(!(chat.getText().trim().equals(""))){
            Main.client.sendChatMessage(chat.getText());
            Platform.runLater(new Runnable() {
                public void run() {
                    chat.setText("");
                }
            });

        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.isHost){
            try{
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                for (; interfaces.hasMoreElements();)
                {
                    NetworkInterface e = interfaces.nextElement();

                    Enumeration<InetAddress> a = e.getInetAddresses();
                    for (; a.hasMoreElements();)
                    {
                        InetAddress addr = a.nextElement();
                        if(addr.isSiteLocalAddress()){
                            chatArea.appendText("Detected IP address of: " + addr.getHostAddress() + "\n");
                            break;
                        }
                    }
                }
            }
            catch(SocketException e){
                e.printStackTrace();
            }

        }
        else{
            startButton.setVisible(false);
        }
    }
}
