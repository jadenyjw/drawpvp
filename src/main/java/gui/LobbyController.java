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
    private static JFXTextArea chatLog;
    @FXML
    private JFXTextField chat;

    public static void displayChatMessage(String message){
        System.out.println(message);
        chatLog.appendText(message);
    }

    @FXML
    public void onEnter(ActionEvent e){
        Main.client.sendChatMessage(chat.getText());

    }

}
