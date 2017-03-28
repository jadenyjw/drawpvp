package utilities;

import org.nd4j.linalg.api.ndarray.INDArray;

public class DataManipulation {

	public static INDArray dataPreprocess(INDArray image){
		Number mean = image.meanNumber();
		Number std = image.stdNumber();
		image = image.sub(mean).div(std);
		return image;
	}

	public static idToString(int n){
    	return "";
	}


	
	
}
