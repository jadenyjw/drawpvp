package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import networking.DClient;
import networking.DServer;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    public static Scene primaryScene;
    private BorderPane mainLayout;
    public static DServer server;
    public static DClient client;

    public static LobbyController lobby;
    public static GameController game;

    public static boolean isHost = false;

    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DrawPVP");
        showMainMenu();
    }

    @Override
    public void stop(){
        if(!server.equals(null)){
            server.close();
        }
    }
    public void showMainMenu() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/main_menu2.fxml"));
        mainLayout = loader.load();
        primaryScene = new Scene(mainLayout);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
