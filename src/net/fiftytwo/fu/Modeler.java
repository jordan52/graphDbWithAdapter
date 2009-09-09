package net.fiftytwo.fu;

public abstract class Modeler {
  double error;
  Matrix sourceData;
  double[] fittedParameters;
  abstract void Fit();
  abstract void Predict();
}


