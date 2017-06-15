package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import networking.DClient;
import networking.DServer;

import java.io.IOException;

public class Main extends Application {
    //Declare variables
    public static Stage primaryStage;
    public static Scene primaryScene;
    private BorderPane mainLayout;
    public static DServer server;
    public static DClient client;

    public static LobbyController lobby;
    public static GameController game;

    public static boolean isHost = false;

    //Start method
    public void start(Stage primaryStage) throws IOException{
        //Create primaryStage and set its title
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DrawPVP");
        //Display the main menu
        showMainMenu();
    }

    @Override
    //Close the server if another one exists
    public void stop(){
        if(!server.equals(null)){
            server.close();
        }
    }
    //showMainMenu method
    public void showMainMenu() throws IOException
    {
        //FXMLLoader works with scene builder files
        FXMLLoader loader = new FXMLLoader();
        //Choose the main menu file and load it
        loader.setLocation(Main.class.getResource("/views/main_menu2.fxml"));
        mainLayout = loader.load();
        //Create a new scene with the BorderPane layout
        primaryScene = new Scene(mainLayout);
        //Display the scene on the stage
        primaryStage.setScene(primaryScene);
        //Add our icon to the program, show the stage
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        primaryStage.show();


    }
    //Main method, run the game
    public static void main(String[] args)
    {
        launch(args);
    }
}
