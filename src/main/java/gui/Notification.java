package gui;

import com.jfoenix.controls.JFXButton;
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
    //Declare variables here so they are global
    Button closeButton;
    Stage window;

    //Method display
    public void display (String[] message)
    {
        //Create a new window
        window = new Stage();
        //Force the user to see this message
        window.initModality(Modality.APPLICATION_MODAL);
        //Set size
        window.setMinWidth(300);
        //Create the label for the message
        Label label = new Label();
        label.setText("The game has finished. These are the standings:");
        //Output the leaderboard
        for(int x = 0; x < message.length; x++){
            label.setText(label.getText() + '\n' + (x+1) + ". " + message[x] + '\n');
        }
        //Give the user a button to close the window
        closeButton = new JFXButton("OK");
        closeButton.setOnAction(e -> window.close());

        //Set layout and add label and button
        VBox layout = new VBox(2);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);
        //Set the scene to show the added components
        Scene scene = new Scene(layout);
        //Display the scene
        window.setScene(scene);
        window.showAndWait();

    }
    //help method called by button
    public void help(){
        //Declare the stage, lock the window
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        //Display label containing tutorial
        Label label = new Label();
        label.setText("Drawing controls: \nLeft click to draw\nRight click to erase\nPress C to clear canvas");
        closeButton = new JFXButton("OK");
        closeButton.setOnAction(e -> window.close());
        //Add the components to a VBox
        VBox layout = new VBox(2);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);
        //Add the VBox to the scene and display the window
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
