package ai;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;

import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;

import utilities.DataManipulation;

public class NeuralNet{

	protected MultiLayerNetwork graph;
	protected ImageLoader loader;

	//Constructs this object with a graph.
	public NeuralNet() throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException{
		graph = ModelSerializer.restoreMultiLayerNetwork(getClass().getResourceAsStream("/model/model.zip"));
		loader = new ImageLoader();
	}

	//Checks if the drawing is the correct one.
	public boolean checkDrawing(BufferedImage image, int n) throws IOException {

		INDArray matrix = loader.asMatrix(image);
		matrix = DataManipulation.dataPreprocess(matrix.reshape(1, 1, 225, 225));
		INDArray prediction = graph.output(matrix, false);
		ArrayList<Integer> a = top(prediction);
		System.out.println(DataManipulation.idToString(n) + " " + DataManipulation.idToString(a.get(0)) + " " + DataManipulation.idToString(a.get(1))+ " " + DataManipulation.idToString(a.get(2)));
		return (top(prediction).get(0) == n || top(prediction).get(1) == n || top(prediction).get(2) == n);
	}

	//Helper method to determine the top 3 items in the list.
	private ArrayList<Integer> top(INDArray prediction){
		double top1 = 0;
		int top1Index = 0;

		double top2 = 0;
		int top2Index = 0;

		double top3 = 0;
		int top3Index = 0;

		for(int x = 0; x < prediction.length(); x++){
			double a = prediction.getDouble(x);
			if(a > top1){
				top3Index = top2Index;
				top3 = top2;
				top2Index = top1Index;
				top2 = top1;
				top1Index = x;
				top1 = a;
			}
			else if(a > top2){
				top3Index = top2Index;
				top3 = top2;
				top2Index = x;
				top2 = a;
			}
			else if(a > top3){
				top3Index = x;
				top3 = a;
			}
		}

		ArrayList<Integer> b = new ArrayList<Integer>();
		b.add(top1Index);
		b.add(top2Index);
		b.add(top3Index);
		return b;
	}
}
