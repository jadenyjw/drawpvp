package ai;

import java.io.IOException;

import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class NeuralNet{

	protected MultiLayerNetwork graph;

	public NeuralNet() throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException{
		graph = KerasModelImport.importKerasSequentialModelAndWeights("ml/drawpvp.h5");
	}

	public MultiLayerNetwork getGraph(){
		return graph;
	}
}
