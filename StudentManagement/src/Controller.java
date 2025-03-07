import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    public static ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    private TableView<Student> studentTable;

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void login(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Signup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        startYearColumn.setCellValueFactory(new PropertyValueFactory<>("startYear"));
        endYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));

        if(students.isEmpty()){
            loadStudents();
        }

        studentTable.setItems(students);
    }

       private void loadStudents() {
        students.addAll(
            new Student("p20230018", "Do Davin", "M", 20, "Software Engineering", "dodavin@gmail.com", 2024, 2025),
            new Student("p20230019", "Virak Rith", "M", 23, "Software Engineering", "virakrith@gmail.com", 2024, 2025),
            new Student("p20230020", "Bro Vath", "M", 20, "Software Engineering", "brovath@gmail.com", 2024, 2025),
            new Student("p20230021", "Bro Vong", "M", 23, "Software Engineering", "brovong@gmail.com", 2024, 2025)
        );
    }
}

