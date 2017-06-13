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
public class Notification implements EventHandler<ActionEvent>{

    Button closeButton;
    Stage window;

    public void display (String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        closeButton = new Button();
        closeButton.setOnAction(this);

        VBox layout = new VBox(2);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
    public void handle(ActionEvent event)
    {
        if(event.getSource()==closeButton)
            window.close();
    }

}
