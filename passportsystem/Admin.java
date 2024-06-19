
package passportsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTable;

public class Admin extends AdminMain {
    // Constructors
    public Admin(int adminID, String NIC, String username, String password, String firstName, String lastName, String email, String contactNo, String address, String role) {
        super(adminID, NIC, username, password, firstName, lastName, email, contactNo, address, role);
    }
    
    public Admin() {} // Default constructor
    
    // Database connection
    DBConnection ob = new DBConnection();
    Connection conn = ob.Connectiondb();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
  
    // Method to view all applications
    public void view(JTable table) {
        try {
            statement = conn.prepareStatement("SELECT * FROM Application ");
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int n = rsmd.getColumnCount();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i <= n; i++) {
                    
                    v.add(resultSet.getString("ApplicationID"));
                    v.add(resultSet.getString("TypeOfService"));
                    v.add(resultSet.getString("TypeOfTravelDocument")); 
                    v.add(resultSet.getString("NIC"));
                    v.add(resultSet.getString("GivenName"));
                    v.add(resultSet.getString("SurName"));
                    v.add(resultSet.getString("DateOfBirth"));
                    v.add(resultSet.getString("PermanentAddress"));
                    v.add(resultSet.getString("District"));
                    v.add(resultSet.getString("Province"));
                    v.add(resultSet.getString("Gender"));
                    v.add(resultSet.getString("Profession"));
                    v.add(resultSet.getString("ContactNo"));
                    v.add(resultSet.getString("Email"));
                    v.add(resultSet.getString("MaritalStatus"));
                    v.add(resultSet.getString("DualCitizenStatus"));
                    v.add(resultSet.getString("DualCitizenNo"));
                    v.add(resultSet.getString("SubmitDocument"));
                    v.add(resultSet.getString("ApplicationStatus"));
                    v.add(resultSet.getString("PaymentStatus"));
                    v.add(resultSet.getString("PoliceStatus"));
                    v.add(resultSet.getString("ScheduleDate"));
                    v.add(resultSet.getString("ScheduleTime"));
                }
                dtm.addRow(v);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } 
    }
    
    // Method to view selected details of all applications
    public void view1(JTable table) {
        try {
            statement = conn.prepareStatement("SELECT ApplicationID,ApplicationStatus,PaymentStatus,PoliceStatus FROM Application ");
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
        } 
    }
    
    // Method to view details of a specific application
    public void viewFromId(JTable table, String ApplicantId) {
        try {
            statement = conn.prepareStatement("SELECT * FROM Application WHERE ApplicationID = ? ");
            statement.setString(1, ApplicantId);
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int n = rsmd.getColumnCount();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i <= n; i++) {
                    v.add(resultSet.getString("NIC"));
                    v.add(resultSet.getString("GivenName"));
                    v.add(resultSet.getString("SurName"));
                    v.add(resultSet.getString("DateOfBirth"));
                    v.add(resultSet.getString("TypeOfService"));
                    v.add(resultSet.getString("TypeOfTravelDocument"));                 
                    v.add(resultSet.getString("PermanentAddress"));
                    v.add(resultSet.getString("Province"));
                    v.add(resultSet.getString("District"));
                    v.add(resultSet.getString("Gender"));
                    v.add(resultSet.getString("Profession"));
                    v.add(resultSet.getString("ContactNo"));
                    v.add(resultSet.getString("Email"));
                    v.add(resultSet.getString("MaritalStatus"));
                    v.add(resultSet.getString("DualCitizenStatus"));
                    v.add(resultSet.getString("DualCitizenNo"));
                    v.add(resultSet.getString("SubmitDocument"));
                    v.add(resultSet.getString("ApplicationStatus"));
                    v.add(resultSet.getString("PaymentStatus"));
                    v.add(resultSet.getString("PoliceStatus"));
                    v.add(resultSet.getString("ScheduleDate"));
                    v.add(resultSet.getString("ScheduleTime"));
                }
                dtm.addRow(v);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } 
    }
    
    // Method to view selected details of a specific application
    public void viewFromId2(String ApplicantId, JTable jTable2) {
        try {
            statement = conn.prepareStatement("SELECT ApplicationID,ApplicationStatus,PaymentStatus,PoliceStatus FROM Application WHERE ApplicationID = ? ");
            statement.setString(1, ApplicantId);
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int n = rsmd.getColumnCount();
            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
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
            System.err.println("SQLException: " + e);
        }
    }

    // Method to update application status
    public void updateStatus(String applicationId, String AStatus) { 
        try {
            String sql = "UPDATE Application SET ApplicationStatus = ? WHERE ApplicationID = ? ";
            PreparedStatement statement = conn.prepareStatement(sql); 
            statement.setString(1, AStatus);
            statement.setString(2, applicationId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Status updated successfully.");
            } else {
                System.out.println("No rows updated. ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    // Method to update police status
    public void updateStatus2(String applicationId, String AStatus) {  
        try {
            String sql = "UPDATE Application SET PoliceStatus = ? WHERE ApplicationID = ? ";
            PreparedStatement statement = conn.prepareStatement(sql); 
            statement.setString(1, AStatus);
            statement.setString(2, applicationId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Police Status updated successfully.");
            } else {
                System.out.println("No rows updated. ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update payment status
    public void updateStatus3(String applicationId, String AStatus) {    
        try {
            String sql = "UPDATE Application SET PaymentStatus = ? WHERE ApplicationID = ? ";
            PreparedStatement statement = conn.prepareStatement(sql); 
            statement.setString(1, AStatus);
            statement.setString(2, applicationId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment Status updated successfully.");
            } else {
                System.out.println("No rows updated. ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view schedule
    public void viewSchedule(JTable table) {
        try {
            statement = conn.prepareStatement("SELECT ApplicationID, ScheduleDate, ScheduleTime FROM Application ");
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int n = rsmd.getColumnCount();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i <= n; i++) {
                    v.add(resultSet.getString("ApplicationID"));
                    v.add(resultSet.getString("ScheduleDate"));
                    v.add(resultSet.getString("ScheduleTime"));
                }
                dtm.addRow(v);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());    
        }
    }

    // Method to schedule an appointment
    public void scheduleAppointment(Date scheduleDate, String scheduleTime, String ApplicationID) {
        try {
            // Create SQL statement
            String sql = "UPDATE Application SET ScheduleDate = ? ,ScheduleTime = ? WHERE ApplicationID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            
            // Set values for parameters
            statement.setDate(1, new java.sql.Date(scheduleDate.getTime()));
            statement.setString(2, scheduleTime);
            statement.setString(3, ApplicationID);

            // Execute the statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data Updated successfully!");
            } else {
                System.out.println("Data Updation Failed!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //method for  generate report Application Status
    public void generateApplicationStatusCounts(JTable table) {
        try {
            // Create SQL statement
            statement = conn.prepareStatement("SELECT ApplicationStatus, COUNT(*) AS Count FROM Application GROUP BY ApplicationStatus");
            resultSet = statement.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                v.add(resultSet.getString("ApplicationStatus"));
                v.add(resultSet.getInt("Count"));
                dtm.addRow(v);
            }
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } 
    }


    
    //method for  generate report Passport Status
    public void generatePassportStatusCounts(JTable table) {
        try {
            statement = conn.prepareStatement("SELECT PassportStatus, COUNT(*) AS Count FROM passportdetails GROUP BY PassportStatus");
            resultSet = statement.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                v.add(resultSet.getString("PassportStatus"));
                v.add(resultSet.getInt("Count"));
                dtm.addRow(v);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } 
}

}
