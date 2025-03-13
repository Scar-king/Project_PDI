import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;

public class HomePageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void addStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void viewStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Table.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void updateStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UpdateStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void deleteStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Unsaved data may be lost!");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if(result == ButtonType.OK){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void showDeveloperDetails(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DeveloperDetails.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void DeveloperToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

}
