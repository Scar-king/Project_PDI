import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ViewController {

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
    @FXML
    private Label maleCountLabel;
    @FXML
    private Label femaleCountLabel;
    @FXML
    private Label totalCountLabel;
    @FXML
    private Label seCountLabel;
    @FXML
    private Label aiecsCountLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

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

        students = Database.loadStudentsFromDB();
        studentTable.setItems(students);

        updateStatistics();
        studentTable.setOnMouseClicked(this::showStudentDetails);
    }

    private void updateStatistics() {
        int totalStudents = students.size();
        int totalMale = (int) students.stream().filter(s -> s.getGender().equalsIgnoreCase("M")).count();
        int totalFemale = (int) students.stream().filter(s -> s.getGender().equalsIgnoreCase("F")).count();        
        int totalSE = (int) students.stream().filter(s -> s.getMajor().equalsIgnoreCase("SE")).count();
        int totalAIECS = (int) students.stream().filter(s -> s.getMajor().equalsIgnoreCase("AIECS")).count();

        // Update Labels
        totalCountLabel.setText(String.valueOf(totalStudents));
        maleCountLabel.setText(String.valueOf(totalMale));
        femaleCountLabel.setText(String.valueOf(totalFemale));
        seCountLabel.setText(String.valueOf(totalSE));
        aiecsCountLabel.setText(String.valueOf(totalAIECS));
    }

    private void showStudentDetails(MouseEvent event) {
    if (event.getClickCount() == 2) { // Detect double-click
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("Image\\itc.png"));
            alert.setTitle("Student Details");
            alert.setHeaderText("Details of Student ID: " + selectedStudent.getStudentID());
            alert.setContentText(
            String.format(
                """
                Name       : %s
                Gender     : %s
                Age        : %d
                Major      : %s
                Email      : %s
                Start Year : %d
                End Year   : %d
                """,
                selectedStudent.getName(),
                selectedStudent.getGender(),
                selectedStudent.getAge(),
                selectedStudent.getMajor(),
                selectedStudent.getStudentEmail(),
                selectedStudent.getStartYear(),
                selectedStudent.getEndYear()
            )
        );
            alert.showAndWait();
        }
    }
}

    @FXML
    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXML\\HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
