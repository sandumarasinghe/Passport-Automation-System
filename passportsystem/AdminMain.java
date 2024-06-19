

package passportsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AdminMain {
    private int adminID;
    private String NIC;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String address;
    private String role;

    //Default Constructor
    public AdminMain(){};
    //User-Build Constructor
    public AdminMain(int adminID, String NIC, String username, String password, String firstName, String lastName, String email, String contactNo, String address, String role) {
        this.adminID = adminID;
        this.NIC = NIC;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
    }
  
    
    // Database connection
    DBConnection ob = new DBConnection();
    Connection conn = ob.Connectiondb();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    
     public void loginVerification(String username, String password) {
        try {
            String sql = "SELECT username, password, role FROM Admin WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                // Retrieve the role from the result set
                String role = resultSet.getString("role");

                // Check the role and redirect accordingly
                switch (role) {
                    case "SystemAdministrator":
                        SystemAdminGUI SA = new SystemAdminGUI();
                        SA.setVisible(true);
                        // Redirect to administrator interface
                        // Example: openAdminInterface();
                        break;
                    case "SuperAdministratorS":
                        Verification v = new Verification();
                        v.setVisible(true);
                        // Redirect to supervisor interface
                        // Example: openSupervisorInterface();
                        break;
                    // Add more cases for other roles if needed
                    default:
                        // Handle unrecognized role
                        break;
                }
                JOptionPane.showMessageDialog(null,"Login Successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                // Handle incorrect login credentials here (e.g., show an error message)
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
  
}
