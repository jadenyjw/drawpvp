package gui;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    //Declare variables
    @FXML protected Canvas surface;
    @FXML protected VBox canvasBox;
    @FXML protected JFXSlider slider;
    protected GraphicsContext gc;
    protected NeuralNet net;
    protected int currentDrawing;
    protected int seconds;
    protected JFXSnackbar bar;
    public Notification gameAlerts = new Notification();

    //showAlert displays pop up window
    public void showAlert(String[] message){
        Platform.runLater(new Runnable() {
            public void run() {
                gameAlerts.display(message);
            }
        });}

    //showMessage displays pop up message that disappears after 5s
    public void showMessage(String title, String message){
        Platform.runLater(new Runnable() {
        public void run() {
            bar.show(message, 5000);


        }
    });}
    //Erase the canvas
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

    //items ArrayList keeps track of players
    ObservableList<String> items = FXCollections.observableArrayList();

    //Chat method
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

    //Remembers the randomly chosen drawing
    public void setDrawing(int n){
        currentDrawing = n;
    }

    //Update the listView if someone leaves
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

    //Resizes the image so the AI can judge it
    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    //Return to lobby after game is finished
    public void goBackToLobby(){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/lobby.fxml"));
            Pane root = loader.load();
            Main.lobby = loader.getController();
            Main.primaryStage.getScene().setRoot(root);
            Main.client.requestPlayers();

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    //Create the drawing room
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set restrictions for slider
        slider.setMax(10);
        slider.setMin(1);

        bar = new JFXSnackbar((Pane)canvasBox.getParent());

        //Use GraphicsContext to draw on canvas
        gc = surface.getGraphicsContext2D();
        //Set the size of the canvas to the VBox it is in
        surface.widthProperty().bind(canvasBox.widthProperty());
        surface.heightProperty().bind(canvasBox.heightProperty());

        //Add event handlers for drawing
        surface.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        //Left button clicked
                        if(event.isPrimaryButtonDown()){
                            gc.setLineWidth(slider.getValue());
                            //Start the stroke
                            gc.beginPath();
                            //Move to the mouse position
                            gc.moveTo(event.getX(), event.getY());
                            //Draw the path
                            gc.stroke();
                        }
                        //Right mouse button
                        else if(event.isSecondaryButtonDown()){
                            //Erase the lines
                            gc.clearRect(event.getX(), event.getY(), slider.getValue()*5, slider.getValue()*5);
                        }


                    }
                });
        //Mouse is moving and button is pressed
        surface.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    //Continue the line if the left button is pressed
                    public void handle(MouseEvent event) {
                        if(event.isPrimaryButtonDown()) {
                            gc.lineTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        //Erase if the right button is pressed
                        else if (event.isSecondaryButtonDown()){
                            gc.clearRect(event.getX(), event.getY(), slider.getValue()*5, slider.getValue()*5);
                        }
                    }
                });


        //Add event handler for keyboard controls.
        //Key is pressed
        surface.getParent().getParent().addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    //if the c button is pressed, clear the canvas
                    public void handle(KeyEvent e) {
                        if(e.getCode() == KeyCode.C) {
                            clear();
                        }
                        //skip the drawing if d is pressed
                        else if(e.getCode() == KeyCode.D){
                            Main.client.sendCorrectDrawing();
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

        //This loop schedules a judging of the drawing every 4 seconds.
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if(currentDrawing != -1){
                            judge();
                        }
                    }
                }, 0, 4000);

        //Time of 60 seconds until the current drawing is automatically skipped.
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
