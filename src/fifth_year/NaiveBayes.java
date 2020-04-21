package fifth_year;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NaiveBayes {
          private double priorProb;
          private RealMatrix posProb,negProb;
          private ArrayList<String>vocab=null;
          //DisplayHelper displayHelper = null;
          public NaiveBayes(String[][] data, String[] label, ArrayList<String> vocab) throws Exception{
        	  this.vocab= vocab;
        	  double[][] probArray = new double[data.length][];
        	  IntStream.range(0,data.length).forEach(i -> probArray[i] = mapDocToVocab(data[i]));
        	  calcPriorProb(label).calcCondProbs(MatrixUtils.createRealMatrix(probArray),label);
          }
          public String classify(String[] docArray) {
        	  String sentiment = "-";
        	  RealMatrix doc=MatrixUtils.createRowRealMatrix(mapDocToVocab(docArray));
        	  double pLogSums = Math.log(priorProb)+ doc.multiply(posProb.transpose()).getData()[0][0];
        	  double nLogSums = Math.log(1-priorProb)+ doc.multiply(negProb.transpose()).getData()[0][0];
        	  //displayHelper.displayClassify(doc, pLogSums, nLogSums);
        	  if(pLogSums > nLogSums) sentiment = "+";
        	   return "text classified as expressing (" + sentiment + ") sentiment\n";
        	  
          }
          private NaiveBayes calcPriorProb(String[] label) {
        	  int sum=0;
        	  for (int i=0; i < label.length; i++) if (label[i].equals("+")) sum +=1;
        	  priorProb = sum/ (double) label.length;
        	  //displayHelper= new DisplayHelper(vocab).setPriors(sum, label.length);
        	  return this;
        	  
          }
          private NaiveBayes calcCondProbs(RealMatrix prob, String[] label) {
        	  RealMatrix nProbNum = MatrixUtils.createRowRealMatrix(new double [vocab.size()]);
        	  for (int i=0; i<vocab.size(); i++) nProbNum.setEntry(0, i, 1.0);
        	  RealMatrix pProbNum = MatrixUtils.createRowRealMatrix(new double [vocab.size()]);
        	  for (int i=0; i<vocab.size();i++) pProbNum.setEntry(0, i, 1.0);
        	  double nProbDenom = vocab.size();
        	  double pProbDenom = vocab.size();
        	  for (int i=0;i< label.length; i++) {
        		  if(label[i].equals("+")) {
        			  pProbNum=pProbNum.add(prob.getRowMatrix(i));
        			  for(int j=0;j<prob.getData()[0].length; j++) pProbDenom += prob.getEntry(i,j);
        		  }else {
        			  nProbNum=nProbNum.add(prob.getRowMatrix(i));
        			  for(int j=0;j<prob.getData()[0].length; j++) nProbDenom += prob.getEntry(i,j);
        		  }
        	  }
        	  posProb= log(pProbNum.scalarMultiply(1/pProbDenom));
        	  negProb= log(nProbNum.scalarMultiply(1/nProbDenom));
        	  //displayHelper.displayTrainingData().displayCalcCondProbs(nProbNum, pProbNum, nProbDenom, pProbDenom);
        	  return this;
          }
          private RealMatrix log(RealMatrix matrix) {
        	  double[] returnData= new double[matrix.getData()[0].length];
        	  IntStream.range(0,returnData.length).forEach(j-> returnData[j] = Math.log(matrix.getData()[0][j]));
        	  return MatrixUtils.createRowRealMatrix(returnData);
          }
          private double[] mapDocToVocab(String[] doc) {
        	  double[] mappedDoc = new double[vocab.size()];
        	  IntStream.range(0, vocab.size()).forEach(i -> mappedDoc[i] = 0);
        	  for(int i=0; i <doc.length; i++)
        	        for(int j =0; j<vocab.size(); j++) if (doc[i].equalsIgnoreCase(vocab.get(j))) mappedDoc[j] += 1;
        	  return mappedDoc;
          }
//          private double[] mapDocToVocab(String[] doc){
//              double[] mappedDoc = new double[vocab.size()];
//              IntStream.range(0,vocab.size()).forEach(i -> mappedDoc[i] = 0);
//              for(int i= 0; i< doc.length; i++)
//                  for(int j=0; j<vocab.size(); j++) if (doc[i].equalsIgnoreCase(vocab.get(j))) mappedDoc[j] += 1;
//              return mappedDoc;
//          }
}
