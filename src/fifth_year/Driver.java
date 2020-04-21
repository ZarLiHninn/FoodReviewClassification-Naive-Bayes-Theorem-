package fifth_year;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

public class Driver {
      static String[][] TRAINING_DATA= {{"Restaurant Review","Classification"},
    		                            {"genuine world class restaurant","positive"},
    		                            {"best new taste in town","positive"},
    		                            {"tasteless bland unappealing","negative"},
    		                            {"unappealing taste lacking style","negative"},
    		                            {"worst restaurant in town","negative"},
    		                            {"bad packaging","negative"}};
      public static void main(String[] args) throws IOException,Exception {
 
    	  String[][] data=new String[Driver.TRAINING_DATA.length-1][];
    	  IntStream.range(0,Driver.TRAINING_DATA.length-1).forEach(i ->
    	  data[i] = Driver.TRAINING_DATA[i+1][0].toString().toLowerCase().split(" "));
    	  String[] label=new String[TRAINING_DATA.length-1];
    	  IntStream.range(0,TRAINING_DATA.length-1).forEach(row->label[row] = TRAINING_DATA[row+1][1]);
    	  HashSet<String>vocab=new HashSet<String>();
    	  IntStream.range(0, data.length).forEach(row->
    	  IntStream.range(0, data[row].length).forEach(column->vocab.add(data[row][column])));
    	  handleCommandLine(new NaiveBayes(data,label, new ArrayList<String>(vocab)));
    	  
      }
      static void handleCommandLine(NaiveBayes nb)throws IOException{
    	  System.out.println();
    	  //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    	  
    		  
                  String a="best taste in restaurant";
    		  String[]values=a.split(" ");
    		  if(values[0].equals("exit"))System.exit(0);
    		  else System.out.println("\n"+nb.classify(values));
    	  
      }
}
