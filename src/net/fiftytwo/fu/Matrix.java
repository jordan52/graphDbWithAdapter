package net.fiftytwo.fu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Matrix {
	
	static Logger logger = Logger.getLogger(Matrix.class);

	public double matrix[][];  //matrix[height][width]
	public int width;
	public int height;
	
	public Matrix(){
		this.width = 0;
		this.height = 0;
		matrix = null;
	}
	
	public Matrix(int height,int width){
		matrix = new double[height][width];
		this.width = width;
    this.height = height;
  }

  public void fillLinear() {
    // y = x 
    // should fit to: a = 1, b = 0
    for(int i = 0; i < this.height; i++) {
      matrix[i][0] = i;
      matrix[i][1] = i;
    }
  }

  public void read(String filename){
    //read it off disk
    try{
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
      matrix = (double[][]) in.readObject();
      in.close();
    } catch (Exception e){
      logger.error("Unable to read matrix file" + filename, e);
    }
  }

  public void write(String filename) {
    //write it to disk
    try{
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
      out.writeObject(matrix);
      out.flush();
      out.close();
    } catch (Exception e){
      logger.error("Unable to write matrix file" + filename, e);
    }
  }

  //this is real crap. 
  public void readCsvFile(String filename){
    try{			
      CSVReader reader = new CSVReader(new FileReader(filename));
      List<String[]> myEntries = reader.readAll();

      this.width = ((String[]) myEntries.get(0)).length;
      this.height = myEntries.size();

      matrix = new double[this.height][this.width];
      String[] row = null;
      for(int i = 0; i < height; i++){
        row = ((String[])myEntries.get(i));
        for(int j = 0; j < width; j++)
        {
          matrix[i][j] = Double.parseDouble(row[j]);
        }
      }

    } catch (Exception e){
      logger.error("Unable to read matrix csv file" + filename, e);
    }
  }

  //Clumsy...
  public void writeCsvFile(String filename){
    try{
      CSVWriter writer = new CSVWriter(new FileWriter(filename));
      for(int i = 0; i < height; i++){
        double[] dbls = matrix[i];
        String [] stgs = new String[width];
        for(int j = 0; j < width; j++){
          stgs[j] = ""+dbls[j];
        }
        writer.writeNext(stgs);
      }
      writer.close();
    } catch (Exception e){
      logger.error("Unable to write matrix csv file" + filename, e);
    }
  }

  /*
   * Generates a CSV string
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString(){
    return this.toString(",");
  }

  public String toString(String delimiter){
    String newline = System.getProperty("line.separator");
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++)
      {
        sb.append("\"").append(matrix[i][j]).append("\"");
        if(j != width-1 ){
          sb.append(delimiter);
        }
      }
      sb.append(newline);
    }
    return sb.toString();
  }

}
