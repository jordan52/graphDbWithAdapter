package net.fiftytwo.fu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Harness {
	
	public static int NUM_SLICES = 10000;
	public static Matrix matrix;
	public static Matrix trainingMatrix;
	
	//y = ax + b
	public double a;
	public double b;

	public void initTrainingMatrix(){
		//either read it off disk or create it procedurally:
		trainingMatrix = new Matrix(4,NUM_SLICES);
		for(int i = 0; i < NUM_SLICES; i++){
			//trainingMatrix[0][i] = (someValueFor X);
			//trainingMatrix[0][i] = (someValueFor Y);
		}
	}

	public void train(){
		//iterate 0 to NUM_SLICES to estimate a and b 
 		//in y = ax + b
		//copied from http://www.cs.princeton.edu/introcs/97data/LinearRegression.java.html
        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            sumx  += matrix.matrix[0][i];
            sumx2 += matrix.matrix[0][i] * matrix.matrix[0][i];
            sumy  += matrix.matrix[1][i];
        }
        double xbar = sumx / NUM_SLICES;
        double ybar = sumy / NUM_SLICES;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            xxbar += (matrix.matrix[0][i] - xbar) * (matrix.matrix[0][i] - xbar);
            yybar += (matrix.matrix[1][i] - ybar) * (matrix.matrix[1][i] - ybar);
            xybar += (matrix.matrix[0][i] - xbar) * (matrix.matrix[1][i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        System.out.println("y   = " + beta1 + " * x + " + beta0);

        this.a = beta1;
		this.b = beta0;
	}


	public void run(int currentSlice){
		double x = matrix.matrix[0][currentSlice];
		double y = matrix.matrix[1][currentSlice];

		double fit =0.0f;
		//if both x and y exist, estimate the fit.
		if( x != Double.NaN && y != Double.NaN){
			//estimate the "goodness of fit"
			matrix.matrix[2][currentSlice] = fit; 
		} else if( x == Double.NaN){
			//x = (y-b)/a ... is that right?
			matrix.matrix[0][currentSlice] =  (y-this.b)/this.a;
		}  else if( y == Double.NaN){
			//y = ax+b
			matrix.matrix[1][currentSlice] = this.a*x + this.b;
		}

	}

	public static void main (String [] args){

		Harness h = new Harness();
		h.initTrainingMatrix();
		try{
			Harness.matrix.read("test.matrix.matrix");
		} catch (Exception e){
			Harness.matrix = new Matrix(4, NUM_SLICES);
			for(int i = 0; i<NUM_SLICES;i++){
				Harness.matrix.matrix[0][i] = i;
				Harness.matrix.matrix[1][i] = i;
				Harness.matrix.matrix[2][i] = Double.NaN;
				Harness.matrix.matrix[3][i] = Double.NaN;
			}
		}
		h.train();

		for(int i = 0; i < NUM_SLICES; i++){
			h.run(i);
		}
		
		Harness.matrix.write("test.matrix.matrix");
	}

}
