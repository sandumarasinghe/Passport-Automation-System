package passportsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// Class representing an Applicant in the passport system
public class Applicant {
    // Member variables representing applicant details
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    // Constructor
    public Applicant() {
        // Default constructor
    }

    public Applicant(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    public Applicant(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Establishing database connection
    DBConnection ob = new DBConnection();
    Connection conn = ob.Connectiondb();

    // Sign up operation
    public void signUpApplicant(String username, String password, String email) {
        try {
            // Create SQL statement
            String sql = "INSERT INTO Applicant (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set values for parameters
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);

            // Execute the statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Data insertion Failed!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Login Method
    public boolean loginVerification(String username, String password) {
        try {
            String sql = "SELECT username,password FROM Applicant WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                    System.out.println("Login successful!");
                    return true;
                }
                
                else {
                JOptionPane.showMessageDialog(null,"Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
                }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // Applying for a service
    public void apply(String username, String password, String email) {
        try {
            // Create SQL statement
            String sql = "INSERT INTO Applicant (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set values for parameters
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);

            // Execute the statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Data insertion Failed!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Inserting application details into the database
    public int insertData(String typeOfService, String typeOfTravelDocument, String NIC, String givenName,
            String surname, String permanentAddress, String district, String dateOfBirth, String gender,
            String profession, String contactNo, String email, String maritalStatus, String dualCitizenStatus,
            String dualCitizenNo, String submitDocument, String province) {

        int ApplicationID = -1;

        try {
            // Creating the SQL INSERT statement
            String sql = "INSERT INTO Application(TypeOfService, TypeOfTravelDocument, NIC, GivenName, Surname, PermanentAddress, Province, District, DateOfBirth, Gender, Profession, ContactNo, Email, MaritalStatus, DualCitizenStatus, DualCitizenNo, SubmitDocument) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Setting values for parameters
            statement.setString(1, typeOfService);
            statement.setString(2, typeOfTravelDocument);
            statement.setString(3, NIC);
            statement.setString(4, givenName);
            statement.setString(5, surname);
            statement.setString(6, permanentAddress);
            statement.setString(7, province);
            statement.setString(8, district);
            statement.setString(9, dateOfBirth);
            statement.setString(10, gender);
            statement.setString(11, profession);
            statement.setString(12, contactNo);
            statement.setString(13, email);
            statement.setString(14, maritalStatus);
            statement.setString(15, dualCitizenStatus);
            statement.setString(16, dualCitizenNo);
            statement.setString(17, submitDocument);

            // Executing the statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");

                var generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    ApplicationID = generatedKeys.getInt(1);
                }
            } else {
                System.out.println("Data insertion failed!");
            }

            conn.close(); // Close the connection after use
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ApplicationID;
    }

    // Auto-fill application details from database
    public void applicationAutoFill(String NIC ,JTextField AFNICTF, JTextField NAMETF, JTextField SNAMETF, JTextField DOBTF) {
        DBConnection ob = new DBConnection();
        Connection conn = ob.Connectiondb();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement("SELECT * FROM nic where NIC =? ");
            statement.setString(1,NIC );
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AFNICTF.setText(resultSet.getString("NIC"));
                NAMETF.setText(resultSet.getString("GivenName"));
                SNAMETF.setText(resultSet.getString("SurName"));
                DOBTF.setText(resultSet.getString("DateOfBirth"));
                
                
            } else {
                // Handle if no records found
                // You can clear the JTextFields or show a message
               JOptionPane.showMessageDialog(null, "Invalid National Identity Card Number!", "Error", JOptionPane.ERROR_MESSAGE);
                // Handle incorrect login credentials here (e.g., show an error message)
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    // View application details
    public void viewDetails(String ApplicantId, JTextField ASTF, JTextField SDTF, JTextField STTF,
            JTextField PSTF) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement("SELECT Application.ApplicationStatus, " +
                    "Application.ScheduleDate, " +
                    "Application.ScheduleTime, " +
                    "passportdetails.PassportStatus " +
                    "FROM passportdetails " +
                    "INNER JOIN Application ON Application.ApplicationID = passportdetails.ApplicationID " +
                    "WHERE Application.ApplicationID = ?;");
            statement.setString(1, ApplicantId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                ASTF.setText(resultSet.getString("ApplicationStatus"));
                SDTF.setText(resultSet.getString("ScheduleDate"));
                STTF.setText(resultSet.getString("ScheduleTime"));
                PSTF.setText(resultSet.getString("PassportStatus"));

            } else {
                // Handle if no records found
                // You can clear the JTextFields or show a message

                ASTF.setText("");
                SDTF.setText("");
                STTF.setText("");
                PSTF.setText("");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

}
