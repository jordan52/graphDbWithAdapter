package net.fiftytwo.fu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Harness {
	
	public static int NUM_SLICES = 10000;
	public static float matrix[][];
	public static float trainingMatrix[][];
	
	public double a;
	public double b;

	public void initTrainingMatrix(){
		//either read it off disk or create it procedurally:
		trainingMatrix = new float[4][NUM_SLICES];
		for(int i = 0; i < NUM_SLICES; i++){
			//trainingMatrix[0][i] = (someValueFor X);
			//trainingMatrix[0][i] = (someValueFor Y);
		}
	}

	public static float[][] loadMatrix(String filename){
		//read it off disk
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
	        float[][] m = (float[][]) in.readObject();
	        in.close();
	        return m;
		} catch (Exception e){
			System.out.println("UNABLE TO READ MATRIX TO FILE, making one up for fun");
			float m[][] = new float[4][NUM_SLICES];
			for(int i = 0; i<NUM_SLICES;i++){
				m[0][i] = i;
				m[1][i] = i;
				m[2][i] = Float.NaN;
				m[3][i] = Float.NaN;
			}
			return m;
		}
	
	}
	
	public static void saveMatrix(String filename, float[][] m) {
		//write it to disk
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(m);
			out.flush();
			out.close();
		} catch (Exception e){
			System.out.println("UNABLE TO WRITE MATRIX TO FILE");
		}
	}

	public void train(){
		//iterate 0 to NUM_SLICES to estimate a and b 
 		//in y = ax + b
		//copied from http://www.cs.princeton.edu/introcs/97data/LinearRegression.java.html
        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            sumx  += matrix[0][i];
            sumx2 += matrix[0][i] * matrix[0][i];
            sumy  += matrix[1][i];
        }
        double xbar = sumx / NUM_SLICES;
        double ybar = sumy / NUM_SLICES;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            xxbar += (matrix[0][i] - xbar) * (matrix[0][i] - xbar);
            yybar += (matrix[1][i] - ybar) * (matrix[1][i] - ybar);
            xybar += (matrix[0][i] - xbar) * (matrix[1][i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        System.out.println("y   = " + beta1 + " * x + " + beta0);

        this.a = beta1;
		this.b = beta0;
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
			matrix[0][currentSlice] = (float) ((y-this.b)/this.a);
		}  else if( y == Float.NaN){
			//y = ax+b
			matrix[1][currentSlice] = (float) (this.a*x + this.b);
		}

	}

	public static void main (String [] args){

		Harness h = new Harness();
		h.initTrainingMatrix();
		Harness.matrix = Harness.loadMatrix("test.matrix");

		h.train();

		for(int i = 0; i < NUM_SLICES; i++){
			h.run(i);
		}
		
		Harness.saveMatrix("test.matrix", Harness.matrix);
	}

}
