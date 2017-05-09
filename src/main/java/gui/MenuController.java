package gui;

import javafx.fxml.FXML;
import gui.Main;

import java.io.IOException;

/**
 * Created by jaden on 4/29/17.
 */
public class MenuController
{
    private Main main;
  @FXML

    private void goElectrical() throws IOException
  {
    Main.showElectricalScene();
  }
}
