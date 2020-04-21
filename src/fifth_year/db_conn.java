/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fifth_year;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class db_conn {

       public static void main(String[]args){
            try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            e.getMessage();
        }
        
        try{
            Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb","postgres","admin");
            JOptionPane.showMessageDialog(null,"Connected");
            String query="Select * FROM food";
		Statement stm=(Statement) conn.createStatement();
		ResultSet rs=stm.executeQuery(query);
		while(rs.next()) {
			String review=rs.getString("review");
			String senti=rs.getString("sentiment");
			
			
			System.out.println(review+"\t"+senti);
        }
                }catch(Exception e){
                System.out.println(e.getMessage());
                }
       
       }
}