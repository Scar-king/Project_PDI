import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        stage.show();
    }

    @FXML
    public void viewStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Table.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UpdateStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void deleteStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
