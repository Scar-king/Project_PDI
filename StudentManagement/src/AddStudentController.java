import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddStudentController {

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
    private TextField studentEmailField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField endYearField;

    @FXML
    public void addStudent(ActionEvent event) {

        if (isInputValid()) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("Image\\itc.png"));
        
            alert.setTitle("Confirm Add Student");
            alert.setHeaderText("Are you sure you want to add this student?");
            alert.setContentText("Please confirm that the student information is correct.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                String studentID = studentIDField.getText();
                String name = nameField.getText();
                String gender = getGender();  // Get gender from checkboxes
                int age = Integer.parseInt(ageField.getText());
                // String major = majorField.getText();
                String major = getMajor();
                String studentEmail = studentEmailField.getText();
                int startYear = Integer.parseInt(startYearField.getText());
                int endYear = Integer.parseInt(endYearField.getText());

                if (Database.isStudentDuplicate(studentID)) {
                    showAlert("Error", "A student with the same ID already exists.", AlertType.ERROR);
                    return;
                }
                
                Student newStudent = new Student(studentID, name, gender, age, major, studentEmail, startYear, endYear);

                ViewController.students.add(newStudent);

                boolean dbAdded = Database.addStudentToDB(newStudent);
                if (dbAdded) {
                    showAlert("Success", "Student Added Successfully", AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to add student to the database.", AlertType.ERROR);
                }

                goToHomePage(event);
            }
        }
    }

    private String getMajor(){
        if(seCheckBox.isSelected()) {
            return "SE";
        } else if (aiecsCheckBox.isSelected()) {
            return "AIECS";
        }
        return "";
    }

    private boolean isInputValid() {
        if (studentIDField.getText().isEmpty() || nameField.getText().isEmpty() || !isGenderSelected() ||
            ageField.getText().isEmpty() || !isMajorSelected() || studentEmailField.getText().isEmpty() ||
            startYearField.getText().isEmpty() || endYearField.getText().isEmpty()) {

            showAlert("Error", "All fields must be filled out.", AlertType.ERROR);
            return false;
        }

        String name = nameField.getText();
        if(!isVAlidName(name)){
            showAlert("Error", "Name must contain only letters and spaces.", AlertType.ERROR);
            nameField.clear();
            return false;
        }

        int age;
        try {
            age = Integer.parseInt(ageField.getText());
            if (age < 4 || age > 50) {
                showAlert("Error", "Age must be between 4 and 50!", AlertType.ERROR);
                ageField.clear();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Age must be a valid integer.", AlertType.ERROR);
            ageField.clear();
            return false;
        }

        int startYear, endYear;
        try {
            startYear = Integer.parseInt(startYearField.getText());
            endYear = Integer.parseInt(endYearField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Start year and end year must be valid integers.", AlertType.ERROR);
            startYearField.clear();
            endYearField.clear();
            return false;
        }

        if (startYear < 2024 || startYear > 2026) {
            showAlert("Error", "Start year must be between 2024 and 2026.", AlertType.ERROR);
            startYearField.clear();
            return false;
        }
    
        if (endYear < 2024 || endYear > 2026) {
            showAlert("Error", "End year must be between 2024 and 2026.", AlertType.ERROR);
            endYearField.clear();
            return false;
        }

        if (endYear < startYear) {
            showAlert("Error", "End year must be greater than or equal to start year.", AlertType.ERROR);
            endYearField.clear();
            return false;
        }

        String email = studentEmailField.getText();
        if(!isVAlidEmail(email)){
            showAlert("Error", "Invalid email format. Please enter a valid email (e.g., example@gmail.com).", AlertType.ERROR);
            studentEmailField.clear();
            return false;
        }

        return true;   
    }

    private boolean isGenderSelected() {
        return maleCheckBox.isSelected() || femaleCheckBox.isSelected();
    }

    private String getGender() {
        if (maleCheckBox.isSelected()) {
            return "M";
        } else if (femaleCheckBox.isSelected()) {
            return "F";
        } 
        return "";
    }

    private boolean isMajorSelected() {
        return seCheckBox.isSelected() || aiecsCheckBox.isSelected();
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
    public void handleGenderSelection() {
        if (maleCheckBox.isSelected()) {
            femaleCheckBox.setSelected(false);
        } else if (femaleCheckBox.isSelected()) {
            maleCheckBox.setSelected(false);
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Image\\itc.png")));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isVAlidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isVAlidName(String name){
        return name.matches("^[a-zA-Z ]+$");
    }

    @FXML
    public void goToHomePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("\\FXML\\HomePage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}