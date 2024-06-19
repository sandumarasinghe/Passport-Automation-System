/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package passportsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class SuperAdmin extends AdminMain {
    // Constructors
    protected SuperAdmin(int adminID, String NIC, String username, String password, String firstName, String lastName, String email, String contactNo, String address, String role) {
        super(adminID, NIC, username, password, firstName, lastName, email, contactNo, address, role);
    }
    
    // Database connection
    DBConnection ob = new DBConnection();
    Connection conn = ob.Connectiondb();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    
    // Default constructor
    SuperAdmin() {}

    // Method for login verification
   

    // Method to view application details
    public void view(JTable table) {
        try {
            statement = conn.prepareStatement("SELECT ApplicationID, ApplicationStatus, PaymentStatus, PoliceStatus FROM Application ");
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int n = rsmd.getColumnCount();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i <= n; i++) {
                    v.add(resultSet.getString("ApplicationID"));
                    v.add(resultSet.getString("ApplicationStatus"));
                    v.add(resultSet.getString("PaymentStatus"));
                    v.add(resultSet.getString("PoliceStatus"));
                }
                dtm.addRow(v);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    // Method for verification process
    public void verification(String PassportStatus, int ApplicationID) {
        PreparedStatement statement = null;
        try {
            // Create SQL statement
            String sql = "INSERT INTO PassportDetails (ApplicationID, PassportStatus) VALUES (?,?)";
            statement = conn.prepareStatement(sql);

            // Set values for parameters
            statement.setInt(1, ApplicationID);
            statement.setString(2, PassportStatus);

            // Execute the statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data Inserted successfully!");
            } else {
                System.out.println("Data Inserted Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
