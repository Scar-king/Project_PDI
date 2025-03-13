import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.TableView;  // Import TableView
import javafx.scene.control.TableColumn; // Import TableColumn
import javafx.scene.control.cell.PropertyValueFactory; // Import PropertyValueFactory
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.Optional;

public class DeleteStudentController {

    public static ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    private TextField studentIDField;
    @FXML
    private Button deleteButton;
    
    @FXML
    private TableView<Student> studentTable;  // Declare TableView here

    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, String> majorColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, Integer> startYearColumn;
    @FXML
    private TableColumn<Student, Integer> endYearColumn;

    // Method to initialize and load student data into the table
    @FXML
    public void initialize() {
        // Initialize the table columns with corresponding property values
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        startYearColumn.setCellValueFactory(new PropertyValueFactory<>("startYear"));
        endYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));

        // Load the student data from the database (or from the Controller)
        students = Database.loadStudentsFromDB();
        
        // Update the table items to reflect the loaded data
        studentTable.setItems(students);
    }

    // Handle the delete action
    @FXML
    public void deleteStudent(ActionEvent event) {
        String studentID = studentIDField.getText();

        if (studentID.isEmpty()) {
            showAlert("Input Error", "Please enter a student ID.", AlertType.ERROR);
            return;
        }

        boolean deleted = false;
        Student studentToDelete = null;  // Keep reference to the student we want to delete
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                studentToDelete = student;  // Found the student
                break;
            }
        }

        if (studentToDelete != null) {
            // Show confirmation alert before deletion
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            Stage stage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("cropped-Logo-ITC.png"));
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this student?");
            
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                students.remove(studentToDelete);
                boolean dbDeleted = Database.deleteStudentFromDB(studentToDelete.getStudentID());
                if (dbDeleted) {
                    deleted = true;
                }
            }
        } else {
            showAlert("Student Not Found", "No student with the provided ID was found.", AlertType.ERROR);
            return;
        }

        if (deleted) {
            showAlert("Student Deleted", "The student was successfully deleted.", AlertType.INFORMATION);
        }
    }

    // Helper method to show an alert
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("cropped-Logo-ITC.png"));
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
