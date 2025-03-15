import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // Method to get connection to the database
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to load students from the database into the ObservableList
    public static ObservableList<Student> loadStudentsFromDB() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "SELECT * FROM students";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String studentID = resultSet.getString("ID");
                String name = resultSet.getString("Name");
                String gender = resultSet.getString("Gender");
                int age = resultSet.getInt("Age");
                String major = resultSet.getString("Major");
                String email = resultSet.getString("Email");
                int startYear = resultSet.getInt("Start_Year");
                int endYear = resultSet.getInt("End_Year");

                students.add(new Student(studentID, name, gender, age, major, email, startYear, endYear));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public static boolean addStudentToDB(Student student) {
        String query = "INSERT INTO students (ID, Name, Gender, Age, Major, Email, Start_Year, End_Year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGender());
            preparedStatement.setInt(4, student.getAge());
            preparedStatement.setString(5, student.getMajor());
            preparedStatement.setString(6, student.getStudentEmail());
            preparedStatement.setInt(7, student.getStartYear());
            preparedStatement.setInt(8, student.getEndYear());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isStudentDuplicate(String studentID) {
        // Query the database to check if student with the same ID exists
        String query = "SELECT COUNT(*) FROM students WHERE ID = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentID);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1) > 0; // If count is greater than 0, the student exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStudentInDB(Student student) {
        String query = "UPDATE students SET Name = ?, Gender = ?, Age = ?, Major = ?, Email = ?, Start_Year = ?, End_Year = ? WHERE ID = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getMajor());
            preparedStatement.setString(5, student.getStudentEmail());
            preparedStatement.setInt(6, student.getStartYear());
            preparedStatement.setInt(7, student.getEndYear());
            preparedStatement.setString(8, student.getStudentID());
    
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row is updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to delete a student from the database
    public static boolean deleteStudentFromDB(String studentID) {
        String query = "DELETE FROM students WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}