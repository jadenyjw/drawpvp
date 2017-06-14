package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by ll on 2017-06-12.
 */
public class Notification {

    Button closeButton;
    Stage window;

    public void display (String[] message)
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText("The game has finished. These are the standings:");
        for(int x = 0; x < message.length; x++){
            label.setText(label.getText() + '\n' + (x+1) + ". " + message[x] + '\n');
        }
        closeButton = new Button("Okay");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(2);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }


}
