package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jaden on 5/10/17.
 */
public class GameController implements Initializable{

    @FXML protected Canvas surface;
    GraphicsContext gc = surface.getGraphicsContext2D();

    private void draw(GraphicsContext gc){
        gc.fillRect(50,60,70,80);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
