import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;

public class UpdateStudentController {

    @FXML
    private TextField studentIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField endYearField;
    
    @FXML
    private Button updateButton;
    
    @FXML
    public void updateStudent(ActionEvent event) {
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        
        // Validate numeric inputs
        try {
            int age = Integer.parseInt(ageField.getText());
            int startYear = Integer.parseInt(startYearField.getText());
            int endYear = Integer.parseInt(endYearField.getText());
            
            Student student = new Student(studentID, name, gender, age, majorField.getText(), email, startYear, endYear);
            
            boolean updated = DatabaseUtil.updateStudentInDB(student);
            
            if (updated) {
                showAlert("Success", "Student updated successfully!", AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update student. Please check the ID and try again.", AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for Age, Start Year, and End Year.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) { 
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
}
