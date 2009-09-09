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
  Double[] fittedParameters;
  LinearRegression(Matrix a) {
    sourceData = a;
    fittedParameters = new Double(2);
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
    double beta1 = xybar / xxbar;
    double beta0 = ybar - beta1 * xbar;

    // print results
    logger.info("y   = " + beta1 + " * x + " + beta0);

    this.fittedParameters[0] = beta1; // a
    this.fittedParameters[1] = beta0; // b
  }
  public Double PredictOnce(Double x) {
    return fittedParameters[0] * x + fittedParameters[1];
  }
  public void Predict() {
    predictions = new Matrix(sourceData.matrix.length, 1);
    for(int i = 0; i < predictions.length; i++) {
      predictions.matrix[i] = PredictOnce(sourceData.matrix[i][0]);
    }
  }
  public void ComputeError() {
    Double acc = 0.0;
    for(int i = 0; i < predictions.length; i++) {
      acc += sqr(abs(predictions.matrix[i] - sourceData.matrix[i][0])); // Euclidean distance (L2-norm)
    }
    error = acc;
  }
}
public static void Main(String[] args) {

  }
