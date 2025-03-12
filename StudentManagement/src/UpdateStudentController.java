import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

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
    public void updateStudent(ActionEvent event) throws IOException {

        String studentID = studentIDField.getText();
        String name = nameField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        
        if (studentID.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() ||
            ageField.getText().isEmpty() || majorField.getText().isEmpty() ||
            startYearField.getText().isEmpty() || endYearField.getText().isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.", AlertType.ERROR);
            return;
        }

        if (name.matches(".*\\d.*")) {
            showAlert("Invalid Name", "Name cannot contain numbers.", AlertType.ERROR);
            nameField.clear();
            return;
        }

        gender = gender.toUpperCase();
        if (!gender.equals("M") && !gender.equals("F")) {
            showAlert("Invalid Gender", "Gender must be 'M' or 'F'.", AlertType.ERROR);
            genderField.clear();
            return;
        }

        if (!isValidGmail(email)) {
            showAlert("Invalid Email", "Please enter a valid Gmail address (e.g., example@gmail.com).", AlertType.ERROR);
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            int startYear = Integer.parseInt(startYearField.getText());
            int endYear = Integer.parseInt(endYearField.getText());
            
            if (age < 4 || age > 100) {
                showAlert("Invalid Age", "Age must be between 4 and 100.", AlertType.ERROR);
                ageField.clear();
                return;
            }
            
            if (endYear <= startYear) {
                showAlert("Invalid Year Range", "End year must be greater than start year.", AlertType.ERROR);
                startYearField.clear();
                endYearField.clear();
                return;
            }
            
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            Stage stage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("cropped-Logo-ITC.png")));
            confirmationAlert.setTitle("Confirm Update");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to update this student's details?");
            
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Student student = new Student(studentID, name, gender, age, majorField.getText(), email, startYear, endYear);
                
                boolean updated = DatabaseUtil.updateStudentInDB(student);
                
                if (updated) {
                    showAlert("Success", "Student updated successfully!", AlertType.INFORMATION);
                    backToHome(event);
                } else {
                    showAlert("Error", "Failed to update student. Please check the ID and try again.", AlertType.ERROR);
                }
            } else {
                showAlert("Update Canceled", "The update operation was canceled.", AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for Age, Start Year, and End Year.", AlertType.ERROR);
            
            ageField.clear();
            startYearField.clear();
            endYearField.clear();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) { 
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("cropped-Logo-ITC.png")));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidGmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
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
