package gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by jaden on 5/10/17.
 */
public class GameController {

    @FXML protected Canvas surface;
    GraphicsContext gc = surface.getGraphicsContext2D();

    private void draw(GraphicsContext gc){
        gc.fillRect(50,60,70,80);
    }


}
