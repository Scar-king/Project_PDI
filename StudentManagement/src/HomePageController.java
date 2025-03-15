import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

public class HomePageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void openPortfolioLink(ActionEvent event) {
        try {
            // Open the hyperlink URL in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://developerinfomation.netlify.app/"));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXML\\AddStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void viewStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXML\\Table.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void updateStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXML\\UpdateStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void deleteStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXML\\DeleteStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management Program");
        stage.show();
    }

    @FXML
    public void exitApplication(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("Image\\itc.png"));
        alert.setTitle("Log Out Application");
        alert.setHeaderText("Are you sure you want to Log Out?");
        alert.setContentText("Unsaved data may be lost!");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if(result == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("FXML\\LoginPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student Management Program");
            stage.show();
        }
    }
}