package ai;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import utilities.DataManipulation;

public class NeuralNet{

	protected MultiLayerNetwork graph;
	protected ImageLoader loader;

	//Constructs this object with a graph.
	public NeuralNet() throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException{
		ClassLoader classLoader = NeuralNet.class.getClassLoader();
		graph = KerasModelImport.importKerasSequentialModelAndWeights(classLoader.getResource("model/drawpvp.h5").getPath());
		loader = new ImageLoader();
	}

	//Checks if the drawing is the currect one.
	public boolean checkDrawing(BufferedImage image, int n) throws IOException {

		INDArray matrix = loader.asMatrix(image);
		matrix = DataManipulation.dataPreprocess(matrix.reshape(1, 1, 225, 225));
		int[] prediction = graph.predict(matrix);
		System.out.println(DataManipulation.idToString(n) + " " + DataManipulation.idToString(prediction[0]));
		return (prediction[0] == n);
	}
}
