/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package passportsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String username;
	private String pswrd;
	private String hostname;
	private String dbname;
	
	
    // Default constructor with database connection parameters
	public DBConnection() {
		this.username = "root";
		this.pswrd = "Lnbti#12";
		this.hostname = "localhost";
		this.dbname = "PassportSystem";
	}
    
    // Method to establish database connection
    public Connection Connectiondb(){
       Connection conn = null;
       
            try {
                        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/PassportSystem","root","Lnbti#12");								  
			return conn;
		}
		
            catch(SQLException e) {
			return conn;
		}
    }
    
}
  

    
    
    


    

