package gui;

import com.jfoenix.controls.JFXSlider;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import ai.*;
import javafx.util.Duration;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;

/**
 * Created by jaden on 5/10/17.
 */
public class GameController implements Initializable{

    @FXML protected Canvas surface;
    @FXML protected JFXSlider slider;
    protected GraphicsContext gc;
    protected NeuralNet net;
    protected int currentDrawing;

    @FXML
    public void clear(){
        gc.clearRect(0, 0, surface.getWidth(), surface.getHeight());
    }

    public void judge() {
        Platform.runLater(new Runnable() {
            public void run() {
                WritableImage img = surface.snapshot(null, null);
                BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
                try {
                    if(net.checkDrawing(resize(bImage, 225, 225), currentDrawing)){
                        System.out.println("Correct!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void setDrawing(int n){
        currentDrawing = n;
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setMax(10);
        slider.setMin(1);
        gc = surface.getGraphicsContext2D();
        surface.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if(e.isPrimaryButtonDown()){
                            gc.fillOval(e.getX(), e.getY(), slider.getValue(), slider.getValue());
                        }
                        else if(e.isSecondaryButtonDown()){
                            gc.clearRect(e.getX(), e.getY(), slider.getValue(), slider.getValue());
                        }

                    }
                });
        try{
            net = new NeuralNet();
        } catch (InvalidKerasConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }

        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        judge();
                    }
                }, 0, 5000);
    }
}
