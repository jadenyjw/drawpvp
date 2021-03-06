package gui;

import com.jfoenix.controls.*;
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



    ObservableList<String> items = FXCollections.observableArrayList();

    //Show the chat message.
    public void displayChatMessage(final String message){
        Platform.runLater(new Runnable() {
            public void run() {
                chatArea.appendText(message + '\n');
            }
        });

    }

    //Updates the side view with the list of currently connected players.
    public void playersUpdate(final String[] players){

      Platform.runLater(new Runnable() {
            public void run() {
                int i = items.size();
                items.clear();

                for(String s : players){
                    items.add(s);
                }
                playersView.setItems(items);
                if(items.size() > i){
                    chatArea.appendText("A new player has joined the game.\n");
                }
                else{
                    chatArea.appendText("A player has left the game.\n");
                }
            }
        });
    }

    public void initGame(){
        try{
            JFXSpinner spinner = new JFXSpinner();


            Platform.runLater(new Runnable() {
                public void run() {
                    chatArea.appendText("The host has started the game. Please wait.\n");
                }
            });
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/draw_room.fxml"));
            Pane root = loader.load();
            Main.game = loader.getController();
            Main.primaryStage.getScene().setRoot(root);

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
    //Type a message into chat.
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

        //Get the IP address of the host.
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
