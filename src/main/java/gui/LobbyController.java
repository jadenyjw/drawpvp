package gui;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class LobbyController {


    @FXML
    protected JFXTextArea chatArea;
    @FXML
    protected JFXTextField chat;
    @FXML
    protected JFXListView<String> players;

    ObservableList<String> items = FXCollections.observableArrayList();

    public void displayChatMessage(String message){
        chatArea.appendText(message + '\n');
    }

    public void playersUpdate(String[] players){
      items.clear();
      for(String s : players){
        items.add(s);
      }
      this.players.setItems(items);
    }
    @FXML
    public void onEnter(ActionEvent e){
        Main.client.sendChatMessage(chat.getText());
        chat.setText("");
    }



}
