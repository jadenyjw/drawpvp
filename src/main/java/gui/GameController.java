package gui;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import ai.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;

/**
 * Created by jaden on 5/10/17.
 */
public class GameController implements Initializable{

    @FXML protected Canvas surface;
    @FXML protected VBox canvasBox;
    @FXML protected JFXSlider slider;
    protected GraphicsContext gc;
    protected NeuralNet net;
    protected int currentDrawing;
    protected int seconds;
    public Notification drawingAlerts = new Notification();
    protected JFXSnackbar bar;
    public Notification lobbyAlerts = new Notification();

    public void showAlert(String title, String message){
        Platform.runLater(new Runnable() {
            public void run() {
                lobbyAlerts.display(title,message);
            }
        });}


    public void showMessage(String title, String message){
        Platform.runLater(new Runnable() {
        public void run() {
            bar.show(message, 5000);
        }
    });}

    @FXML
    public void clear(){
        gc.clearRect(0, 0, surface.getWidth(), surface.getHeight());
    }

    @FXML
    protected JFXTextArea chatArea;
    @FXML
    protected JFXTextField chat;
    @FXML
    protected JFXListView<String> playersView;


    ObservableList<String> items = FXCollections.observableArrayList();

    public void displayChatMessage(final String message){
        Platform.runLater(new Runnable() {
            public void run() {
                chatArea.appendText(message + '\n');
            }
        });

    }

    public void judge() {
        Platform.runLater(new Runnable() {
            public void run() {
                WritableImage img = surface.snapshot(null, null);
                BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
                try {
                    if(net.checkDrawing(resize(bImage, 225, 225), currentDrawing)){
                        Main.client.sendCorrectDrawing();
                        clear();
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

    public void playersUpdate(final String[] players){

        Platform.runLater(new Runnable() {
            public void run() {
                items.clear();
                for(String s : players){
                    items.add(s);
                }
                playersView.setItems(items);
            }
        });
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
        bar = new JFXSnackbar((Pane)canvasBox.getParent());

        gc = surface.getGraphicsContext2D();
        surface.widthProperty().bind(canvasBox.widthProperty());
        surface.heightProperty().bind(canvasBox.heightProperty());

        surface.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        gc.setLineWidth(slider.getValue());
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.stroke();

                    }
                });

        surface.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    }
                });

        surface.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });

        surface.getParent().getParent().addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        if(e.getCode() == KeyCode.C) {
                            clear();
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
                        if(currentDrawing != -1){
                            judge();
                        }
                    }
                }, 0, 4000);

        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        if(currentDrawing != -1) {
                            if (seconds == 60) {
                                Main.client.sendCorrectDrawing();
                                clear();
                                seconds = 0;
                            } else {
                                seconds++;
                            }
                        }
                    }
                }, 0, 1000);


    }


}
