package utilities;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class DataManipulation {

	//This method normalizes the matrix with the formula: (x - mu)/sigma
	public static INDArray dataPreprocess(INDArray image){
		Number mean = image.meanNumber();
		Number std = image.stdNumber();
		image = image.sub(mean).div(std);
		return image;
	}
	//This method returns the String representation of the ID.
	public static String idToString(int n){

		String drawing = null;
		try{
			File dictionary = new File(DataManipulation.class.getResource("/text/dictionary.txt").getFile());
			FileReader fr = new FileReader(dictionary);
			BufferedReader br = new BufferedReader(fr);

			for (int x = 0; x < n; x++){
				br.readLine();
			}

			drawing = br.readLine();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return drawing;

	}


	
	
}
