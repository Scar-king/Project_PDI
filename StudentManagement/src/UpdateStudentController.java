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
        int age = Integer.parseInt(ageField.getText());
        String major = majorField.getText();
        String studentEmail = emailField.getText();
        int startYear = Integer.parseInt(startYearField.getText());
        int endYear = Integer.parseInt(endYearField.getText());

        boolean updated = false;
        for (Student student : Controller.students) {
            if (student.getStudentID().equals(studentID)) {
                student.setName(name);
                student.setGender(gender);
                student.setAge(age);
                student.setMajor(major);
                student.setStudentEmail(studentEmail);
                student.setStartYear(startYear);
                student.setEndYear(endYear);
                updated = true;
                break;
            }
        }

        if (updated) {
            showAlert("Student Updated", "The student's details were successfully updated.", AlertType.INFORMATION);
        } else {
            showAlert("Student Not Found", "No student with the provided ID was found.", AlertType.ERROR);
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
