import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentController {

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
    private TextField studentEmailField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField endYearField;

    private ViewController controller;

    public void setController(ViewController controller) {
        this.controller = controller;
    }

    @FXML
    public void addStudent(ActionEvent event) {
        // Validate all fields before proceeding
        if (isInputValid()) {
            // Create a confirmation alert
            Alert alert = new Alert(AlertType.CONFIRMATION);

            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("cropped-Logo-ITC.png"));
        
            alert.setTitle("Confirm Add Student");
            alert.setHeaderText("Are you sure you want to add this student?");
            alert.setContentText("Please confirm that the student information is correct.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                String studentID = studentIDField.getText();
                String name = nameField.getText();
                String gender = genderField.getText().toUpperCase();
                int age = Integer.parseInt(ageField.getText());
                String major = majorField.getText();
                String studentEmail = studentEmailField.getText();
                int startYear = Integer.parseInt(startYearField.getText());
                int endYear = Integer.parseInt(endYearField.getText());

                if (DatabaseUtil.isStudentDuplicate(studentID)) {
                    System.out.println("Duplicate student ID detected: " + studentID);
                    showErrorAlert("A student with the same ID already exists.");
                    return;
                }
                
                Student newStudent = new Student(studentID, name, gender, age, major, studentEmail, startYear, endYear);

                controller.students.add(newStudent);

                boolean dbAdded = DatabaseUtil.addStudentToDB(newStudent);
                if (dbAdded) {
                    showSuccessAlert();
                } else {
                    showErrorAlert("Failed to add student to the database.");
                }

                goToHomePage(event);
            }
        }
    }

    private boolean isInputValid() {
        if (studentIDField.getText().isEmpty() || nameField.getText().isEmpty() || genderField.getText().isEmpty() ||
            ageField.getText().isEmpty() || majorField.getText().isEmpty() || studentEmailField.getText().isEmpty() ||
            startYearField.getText().isEmpty() || endYearField.getText().isEmpty()) {

            showErrorAlert("All fields must be filled out.");
            return false;
        }

        int age;
        try {
            age = Integer.parseInt(ageField.getText());
            if (age < 4 || age > 50) {
                showErrorAlert("Age must be between 4 and 50!");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Age must be a valid integer.");
            return false;
        }

        int startYear, endYear;
        try {
            startYear = Integer.parseInt(startYearField.getText());
            endYear = Integer.parseInt(endYearField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Start year and end year must be valid integers.");
            return false;
        }

        if (endYear < startYear) {
            showErrorAlert("End year must be greater than or equal to start year.");
            return false;
        }

        String gender = genderField.getText().toUpperCase(); 
        if (!gender.equals("M") && !gender.equals("F")) {
            showErrorAlert("Gender must be either 'M' or 'F'.");
            return false;
        }

        return true;
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Student Added Successfully");
        alert.setContentText("The student has been added to the database successfully.");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Adding Student");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToHomePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
