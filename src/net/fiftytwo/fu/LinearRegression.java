package net.fiftytwo.fu;

//import java.math;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.List;
//import net.fiftytwo.fu.Modeler;
import org.apache.log4j.Logger;

public class LinearRegression extends Modeler {
  /* LinearRegression fits the two-constant, one-variable equation y = ax + b
   * to the data referenced by sourceData.
   * In statistical terminology, a = beta0, b = beta1.
   * These are stored in the fittedParameters array.
   */
  double error = Double.NaN;
  double[] fittedParameters = new double[2];
  LinearRegression(Matrix a) {
    sourceData = a;
  }
  Matrix sourceData;
  double[] predictions;

  public void Fit() {
    // iterate 0 to NUM_SLICES to estimate a and b 
    // in y = ax + b
    // copied from http://www.cs.princeton.edu/introcs/97data/LinearRegression.java.html
    // first pass: read in data, compute xbar and ybar
    double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
    for(int i = 0; i < sourceData.matrix.length; i++){
      sumx  += sourceData.matrix[i][0];
      sumx2 += sourceData.matrix[i][0] * sourceData.matrix[i][0];
      sumy  += sourceData.matrix[i][1];
    }
    double xbar = sumx / sourceData.matrix.length;
    double ybar = sumy / sourceData.matrix.length;

    // second pass: compute summary statistics
    double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
    for(int i = 0; i < sourceData.matrix.length; i++){
      xxbar += (sourceData.matrix[i][0] - xbar) * (sourceData.matrix[i][0] - xbar);
      yybar += (sourceData.matrix[i][1] - ybar) * (sourceData.matrix[i][1] - ybar);
      xybar += (sourceData.matrix[i][0] - xbar) * (sourceData.matrix[i][1] - ybar);
    }
    double beta1 = 0.0;
    double beta0 = 0.0;
    beta1 = ybar - beta1 * xbar;
    beta0 = xybar / xxbar;

    // print results
    //logger.info("y   = " + beta0 + " * x + " + beta1);
    //this.fittedParameters = new Double[];

    this.fittedParameters[0] = beta0; // a
    this.fittedParameters[1] = beta1; // b
  }
  public Double PredictOnce(Double x) {
    // y = ax + b
    return fittedParameters[0] * x + fittedParameters[1];
  }
  public void Predict() {
    predictions = new double[sourceData.matrix.length];
    for(int i = 0; i < predictions.length; i++) {
      predictions[i] = PredictOnce(sourceData.matrix[i][0]);
    }
  }
  public void ComputeL1NormError() {
    Double acc = 0.0;
    for(int i = 0; i < predictions.length; i++) {
      acc += Math.abs(predictions[i] - sourceData.matrix[i][0]);
    }
    error = acc;
  }
  public void ComputeL2NormError() {
    Double acc = 0.0;
    for(int i = 0; i < predictions.length; i++) {
      acc += Math.abs(predictions[i] - sourceData.matrix[i][0]); // Euclidean distance (L2-norm)
    }
    error = acc;
  }
  private Double myAbs(Double x) {
    if(x == -0.0) return x * -1.0;
    else if (x < 0) return x * -1.0;
    else return x;
  }
  public static void main (String args[]){
    Logger logger = Logger.getLogger(LinearRegression.class);
    logger.info("starting LinearRegression test");
    long starttime = System.currentTimeMillis();
    Matrix myData;
    // TODO: fix matrix IO:
    //try{
    //  myData.read("test.matrix");
    //} catch (Exception e){
      myData = new Matrix(10000, 2);
      myData.fillLinear();
    //}
    //myData.fillNan();
    LinearRegression myLinearRegression = new LinearRegression(myData);
    long runtime = System.currentTimeMillis();
    myLinearRegression.Fit();
    myLinearRegression.Predict();
    myLinearRegression.ComputeL2NormError();
    logger.info("linear regression test case 1: y = x");
    logger.info("algorithm run time for " + myData.matrix.length + " slices was " + (System.currentTimeMillis() - runtime) + " ms.");
    logger.info("fitted parameters: " + myLinearRegression.fittedParameters[0] + "x + " + myLinearRegression.fittedParameters[1]);
    logger.info("error statistic: " + myLinearRegression.error);
    logger.info("slice 0: " + myData.matrix[0][0] + " " + myData.matrix[0][1]);
    logger.info("slice 1: " + myData.matrix[1][0] + " " + myData.matrix[1][1]);
    logger.info("slice 2: " + myData.matrix[2][0] + " " + myData.matrix[2][1]);
    //myData.write("test.matrix");

    //let's test the csv code
    //myData.writecsvfile("matrix.csv");
    //myData readcsv = new matrix();
    //readcsv.readcsvfile("matrix.csv");

    logger.info("total run time was " + (System.currentTimeMillis() - starttime) + " ms.");
  }

}
