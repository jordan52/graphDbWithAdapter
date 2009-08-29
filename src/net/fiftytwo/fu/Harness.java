package net.fiftytwo.fu;

import org.apache.log4j.Logger;

public class Harness {

	static Logger logger = Logger.getLogger(Harness.class);
	
	public static int NUM_SLICES = 100000;
	public static Matrix matrix;
	public static Matrix trainingMatrix;
	
	//y = ax + b
	public double a;
	public double b;

	public void initTrainingMatrix(){
		//either read it off disk or create it procedurally:
		trainingMatrix = new Matrix(NUM_SLICES,4);
		for(int i = 0; i<NUM_SLICES;i++){
			trainingMatrix.matrix[i][0] = i;
			trainingMatrix.matrix[i][1] = i;
			trainingMatrix.matrix[i][2] = Double.NaN;
			trainingMatrix.matrix[i][3] = Double.NaN;
		}
	}

	public void train(){
		//iterate 0 to NUM_SLICES to estimate a and b 
 		//in y = ax + b
		//copied from http://www.cs.princeton.edu/introcs/97data/LinearRegression.java.html
        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            sumx  += trainingMatrix.matrix[i][0];
            sumx2 += trainingMatrix.matrix[i][0] * trainingMatrix.matrix[i][0];
            sumy  += trainingMatrix.matrix[i][1];
        }
        double xbar = sumx / NUM_SLICES;
        double ybar = sumy / NUM_SLICES;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for(int i = 0; i < NUM_SLICES; i++){
            xxbar += (trainingMatrix.matrix[i][0] - xbar) * (trainingMatrix.matrix[i][0] - xbar);
            yybar += (trainingMatrix.matrix[i][1] - ybar) * (trainingMatrix.matrix[i][1] - ybar);
            xybar += (trainingMatrix.matrix[i][0] - xbar) * (trainingMatrix.matrix[i][1] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        logger.info("y   = " + beta1 + " * x + " + beta0);

        this.a = beta1;
		this.b = beta0;
	}

	public void run(int currentSlice){

		Double x = matrix.matrix[currentSlice][0];
		Double y = matrix.matrix[currentSlice][1];
		Double fit = Double.NaN;

		//if both x and y exist, estimate the fit.
		if(!( Double.isNaN(x) || Double.isNaN(y))){
			//estimate the "goodness of fit"
			matrix.matrix[currentSlice][2] = fit; 
		} else if( Double.isNaN(x)){
			//x = (y-b)/a ... is that right?
			matrix.matrix[currentSlice][0] =  (y-this.b)/this.a;
		}  else if(Double.isNaN(y)){
			//y = ax+b
			matrix.matrix[currentSlice][1] = this.a*x + this.b;
		}
	}

	public static void main (String [] args){

		logger.info("Starting Harness");
		long startTime = System.currentTimeMillis();
		
		Harness h = new Harness();
		h.initTrainingMatrix();
		try{
			Harness.matrix.read("test.matrix");
		} catch (Exception e){
			Harness.matrix = new Matrix( NUM_SLICES, 4);
			for(int i = 0; i<NUM_SLICES;i++){
				Harness.matrix.matrix[i][0] = i;
				Harness.matrix.matrix[i][1] = Double.NaN;
				Harness.matrix.matrix[i][2] = Double.NaN;
				Harness.matrix.matrix[i][3] = Double.NaN;
			}
		}
		h.train();

		long runTime = System.currentTimeMillis();
		for(int i = 0; i < NUM_SLICES; i++){
			h.run(i);
		}
		
		logger.info("Algorithm run time for " + NUM_SLICES + " slices was " + (System.currentTimeMillis() - runTime) + " ms.");
		
		Harness.matrix.write("test.matrix");
		
		//Let's test the CSV code
		Harness.matrix.writeCsvFile("matrix.csv");
		Matrix readCsv = new Matrix();
		readCsv.readCsvFile("matrix.csv");
		
		logger.info("Total run time was " + (System.currentTimeMillis() - startTime) + " ms.");
	}

}
