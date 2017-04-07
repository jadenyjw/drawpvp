package ai;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class NeuralNet{

	protected MultiLayerNetwork graph;
	protected ImageLoader loader;

	//Constructs this object with a graph.
	public NeuralNet() throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException{
		graph = KerasModelImport.importKerasSequentialModelAndWeights("ml/drawpvp.h5");
		loader = new ImageLoader();
	}

	//Checks if the drawing is the currect one.
	public boolean checkDrawing(BufferedImage image, int n){
		int[] prediction = graph.predict(loader.asMatrix(image));
		return (prediction[0] == n);
	}
}
