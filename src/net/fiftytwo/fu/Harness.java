package net.fiftytwo.fu;

public class Harness {

	
	public static int NUM_SLICES = 10000;
	public static float matrix[][] = new float[4][NUM_SLICES];
	public static float trainingMatrix[][] = new float[4][NUM_SLICES];
	
	
	public float a;
	public float b;

	public void initTrainingMatrix(){
		//either read it off disk or create it procedurally:
		for(int i = 0; i < NUM_SLICES; i++){
			//trainingMatrix[0][i] = (someValueFor X);
			//trainingMatrix[0][i] = (someValueFor Y);
		}
	}

	public void initMatrix(){
		//read it off disk
		for(int i = 0; i<NUM_SLICES;i++){
			matrix[0][i] = i;
			matrix[1][i] = i;
			matrix[2][i] = Float.NaN;
			matrix[3][i] = Float.NaN;
		}
	
	}
	
	public void saveMatrix(){
		//write it to disk
	}

	public void train(){
		//iterate 0 to NUM_SLICES to estimate a and b 
 		//in y = ax + b
		float estimatedA = 0.0f, estimatedB = 0.0f, x, y;
		for(int i = 0; i < NUM_SLICES; i++){
			x = trainingMatrix[0][i];
			y = trainingMatrix[1][i];

			//figure out what a and b should be.
		}
		
		this.a = estimatedA;
		this.b = estimatedB;
	}


	public void run(int currentSlice){
		float x = matrix[0][currentSlice];
		float y = matrix[1][currentSlice];

		float fit =0.0f;
		//if both x and y exist, estimate the fit.
		if( x != Float.NaN && y != Float.NaN){
			//estimate the "goodness of fit"
			matrix[2][currentSlice] = fit; 
		} else if( x == Float.NaN){
			//x = (y-b)/a ... is that right?
			matrix[0][currentSlice] = (y-this.b)/this.a;
		}  else if( y == Float.NaN){
			//y = ax+b
			matrix[1][currentSlice] = this.a*x + this.b;
		}

	}

	public static void main (String [] args){

		Harness h = new Harness();
		h.initTrainingMatrix();
		h.initMatrix();

		h.train();

		for(int i = 0; i < NUM_SLICES; i++){
			h.run(i);
		}
		
		h.saveMatrix();
	}

}
