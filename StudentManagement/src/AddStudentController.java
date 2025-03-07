import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    public void addStudent(ActionEvent event) {
        // Retrieve student data from fields
        String studentID = studentIDField.getText();
        String name = nameField.getText();
        String gender = genderField.getText();
        int age = Integer.parseInt(ageField.getText());
        String major = majorField.getText();
        String studentEmail = studentEmailField.getText();
        int startYear = Integer.parseInt(startYearField.getText());
        int endYear = Integer.parseInt(endYearField.getText());

        // Create new student object
        Student newStudent = new Student(studentID, name, gender, age, major, studentEmail, startYear, endYear);

        // Add new student to the ObservableList in the Controller
        controller.students.add(newStudent);

        // Navigate back to the home page
        goToHomePage(event);
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
