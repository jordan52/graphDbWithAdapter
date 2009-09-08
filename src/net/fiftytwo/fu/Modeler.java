package net.fiftytwo.fu;

public abstract class Modeler {
  Double error;
  Matrix sourceData;
  Double[] fittedParameters;
  void Fit();
  void Predict();
}
public class LinearRegression extends Modeler {
  /* LinearRegression fits the two-constant, one-variable equation y = ax + b
   * to the data referenced by sourceData.
   * In statistical terminology, a = beta0, b = beta1.
   * These are stored in the fittedParameters array.
   */
  Double error = NaN;
  Double[2] fittedParameters;
  LinearRegression(Matrix a) {
    sourceData = a;
  }
  Matrix sourceData;
  Matrix predictions;

  public void Fit() {
    //iterate 0 to NUM_SLICES to estimate a and b 
    //in y = ax + b
    //copied from http://www.cs.princeton.edu/introcs/97data/LinearRegression.java.html
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
    double beta1 = ybar - beta1 * xbar;
    double beta0 = xybar / xxbar;

    // print results
    logger.info("y   = " + beta0 + " * x + " + beta1);

    this.fittedParameters[0] = beta0; // a
    this.fittedParameters[1] = beta1; // b
  }
  public Double PredictOnce(Double x) {
    // y = ax + b
    return fittedParameters[0] * x + fittedParameters[1];
  }
  public void Predict() {
    predictions = new Matrix(sourceData.matrix.length, 1);
    for(int i = 0; i < predictions.length; i++) {
      predictions.matrix[i] = PredictOnce(sourceData.matrix[i][0]);
    }
  }
  public void ComputeL1NormError() {
    Double acc = 0.0;
    for(int i = 0; i < predictions.length; i++) {
      acc += abs(predictions.matrix[i] - sourceData.matrix[i][0]);
    }
  }
  public void ComputeL2NormError() {
    Double acc = 0.0;
    for(int i = 0; i < predictions.length; i++) {
      acc += sqr(abs(predictions.matrix[i] - sourceData.matrix[i][0])); // Euclidean distance (L2-norm)
    }
    error = acc;
  }
}
public static void main (string [] args){

  logger.info("starting LinearRegression(Modeler) harness");
  long starttime = system.currenttimemillis();

  Matrix myData;
  try{
    myData.read("test.matrix");
  } catch (exception e){
    myData = new Matrix(num_slices, 2);
    myData.fillLinear();
  }
  //myData.fillNan();
  myLinearRegression = new LinearRegression(myData);
  long runtime = system.currenttimemillis();
  myLinearRegression.Fit();
  myLinearRegression.Predict();
  myLinearRegression.ComputeL2NormError();
  logger.info("linear regress test case 1: y = x");
  logger.info("algorithm run time for " + num_slices + " slices was " + (system.currenttimemillis() - runtime) + " ms.");
  myData.write("test.matrix");

  //let's test the csv code
  myData.writecsvfile("matrix.csv");
  myData readcsv = new matrix();
  readcsv.readcsvfile("matrix.csv");

  logger.info("total run time was " + (system.currenttimemillis() - starttime) + " ms.");
}

//}
