package gui;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class LobbyController implements Initializable{


    @FXML
    protected JFXTextArea chatArea;
    @FXML
    protected JFXTextField chat;
    @FXML
    protected JFXListView<String> playersView;

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
    @FXML
    public void onEnter(){
        Main.client.sendChatMessage(chat.getText());
        Platform.runLater(new Runnable() {
            public void run() {
                chat.setText("");
            }
        });

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.isHost){
           
        }
    }
}
