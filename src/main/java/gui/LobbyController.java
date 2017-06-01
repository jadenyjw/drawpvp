package gui;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by jaden on 5/10/17.
 */
public class LobbyController {

    @FXML
    protected JFXTextArea chatArea;
    @FXML
    protected JFXTextField chat;

    public void displayChatMessage(String message){
        chatArea.appendText(message + '\n');
    }
    
    @FXML
    public void onEnter(ActionEvent e){
        Main.client.sendChatMessage(chat.getText());
        chat.setText("");
    }

}
