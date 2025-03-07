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

public class DeleteStudentController {

    @FXML
    private TextField studentIDField;
    @FXML
    private Button deleteButton;

    // Handle the delete action
    @FXML
    public void deleteStudent(ActionEvent event) {
        String studentID = studentIDField.getText();

        // Search for student by ID and remove them if found
        boolean deleted = false;
        for (Student student : Controller.students) {
            if (student.getStudentID().equals(studentID)) {
                Controller.students.remove(student);  // Remove from the ObservableList
                deleted = true;
                break;
            }
        }

        // Show alert based on whether the student was deleted or not
        if (deleted) {
            showAlert("Student Deleted", "The student was successfully deleted.", AlertType.INFORMATION);
        } else {
            showAlert("Student Not Found", "No student with the provided ID was found.", AlertType.ERROR);
        }
    }

    // Helper method to show an alert
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to go back to the HomePage
    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
