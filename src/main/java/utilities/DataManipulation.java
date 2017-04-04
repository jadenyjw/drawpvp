package utilities;

import org.nd4j.linalg.api.ndarray.INDArray;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class DataManipulation {

	public static INDArray dataPreprocess(INDArray image){
		Number mean = image.meanNumber();
		Number std = image.stdNumber();
		image = image.sub(mean).div(std);
		return image;
	}

	public static String idToString(int n) throws IOException{
		FileReader fr = new FileReader("src/main/resources/text/dictionary.txt");
		BufferedReader br = new BufferedReader(fr);

		for (int x = 0; x < n; x++){
			br.readLine();
		}

    	return br.readLine();
	}


	
	
}
