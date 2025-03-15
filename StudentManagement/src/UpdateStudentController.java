import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    private CheckBox maleCheckBox;
    @FXML
    private CheckBox femaleCheckBox;
    @FXML
    private TextField ageField;
    @FXML
    private CheckBox seCheckBox;
    @FXML
    private CheckBox aiecsCheckBox;
    @FXML
    private TextField emailField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField endYearField;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private void handleGenderSelection() {
        if (maleCheckBox.isSelected()) {
            femaleCheckBox.setSelected(false);
        } else if (femaleCheckBox.isSelected()) {
            maleCheckBox.setSelected(false);
        }
    }
    
    @FXML
    public void updateStudent(ActionEvent event) throws IOException {
        
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        
        String gender = "";
        if (maleCheckBox.isSelected()) {
            gender = "M";
        } else if (femaleCheckBox.isSelected()) {
            gender = "F";
        }

        String major = "";
        if (seCheckBox.isSelected()) {
            major = "SE";
        } else if (aiecsCheckBox.isSelected()) {
            major = "AIECS";
        }

        if (major.isEmpty()) {
            showAlert("Invalid Major", "Please select either SE or AIECS.", Alert.AlertType.ERROR);
            return;
        }
        
        if (studentID.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() ||
            ageField.getText().isEmpty() || major.isEmpty() ||
            startYearField.getText().isEmpty() || endYearField.getText().isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.", Alert.AlertType.ERROR);
            return;
        }

        if (name.matches(".*\\d.*")) {
            showAlert("Invalid Name", "Name cannot contain numbers.", Alert.AlertType.ERROR);
            nameField.clear();
            return;
        }

        if (!isValidGmail(email)) {
            showAlert("Invalid Email", "Please enter a valid Gmail address (e.g., example@gmail.com).", Alert.AlertType.ERROR);
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            int startYear = Integer.parseInt(startYearField.getText());
            int endYear = Integer.parseInt(endYearField.getText());
            
            if (age < 4 || age > 100) {
                showAlert("Invalid Age", "Age must be between 4 and 100.", Alert.AlertType.ERROR);
                ageField.clear();  
                return;
            }
            
            if (startYear < 2024 || startYear > 2026) {
                showAlert("Invalid Start Year", "Start year must be between 2024 and 2026.", Alert.AlertType.ERROR);
                startYearField.clear();
                return;
            }
            if (endYear < 2024 || endYear > 2026) {
                showAlert("Invalid End Year", "End year must be between 2024 and 2026.", Alert.AlertType.ERROR);
                endYearField.clear();
                return;
            }
            if (endYear <= startYear) {
                showAlert("Invalid Year Range", "End year must be greater than start year.", Alert.AlertType.ERROR);
                startYearField.clear();  
                endYearField.clear();
                return;
            }
            
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("Image\\itc.png")));
            confirmationAlert.setTitle("Confirm Update");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to update this student's details?");
            
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Student student = new Student(studentID, name, gender, age, major, email, startYear, endYear);
                
                boolean updated = Database.updateStudentInDB(student);
                
                if (updated) {
                    showAlert("Success", "Student updated successfully!", Alert.AlertType.INFORMATION);
                    backToHome(event);
                } else {
                    showAlert("Error", "Failed to update student. Please check the ID and try again.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Update Canceled", "The update operation was canceled.", Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for Age, Start Year, and End Year.", Alert.AlertType.ERROR);
            ageField.clear();
            startYearField.clear();
            endYearField.clear();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) { 
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Image\\itc.png")));
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
    private void handleMajorSelection() {
        if (seCheckBox.isSelected()) {
            aiecsCheckBox.setSelected(false);
        } else if (aiecsCheckBox.isSelected()) {
            seCheckBox.setSelected(false);
        }
    }

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
}