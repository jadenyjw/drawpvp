package game;
import ai.*;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;

import java.io.IOException;

public class Game {
    public Game() throws UnsupportedKerasConfigurationException, IOException, InvalidKerasConfigurationException {

        NeuralNet net = new NeuralNet();

    }

}
