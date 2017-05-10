package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import networking.DClient;
import networking.DServer;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    private AnchorPane mainLayout;
    public static DServer server;
    public static DClient client;

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
        loader.setLocation(Main.class.getResource("/views/main_menu.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
