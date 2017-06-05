package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jaden on 5/10/17.
 */
public class GameController implements Initializable{

    @FXML protected Canvas surface;
    protected GraphicsContext gc;

    /*
    private void draw(GraphicsContext gc){
        gc.fillRect(50,60,70,80);
    }
    */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = surface.getGraphicsContext2D();
        surface.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        gc.fillOval(e.getX(), e.getY(), 2, 2);
                    }
                });
    }
}
