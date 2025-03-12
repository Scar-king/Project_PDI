import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUPController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Method to sign up the user
    @FXML
    public void signUp(ActionEvent event) throws IOException {
        // Get the values from the TextField and PasswordField
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate inputs
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields are required", AlertType.ERROR);
            return; // Do not proceed with the sign-up process
        }

        // Username validation (No spaces or special characters, minimum length 3)
        if (!isValidUsername(username)) {
            showAlert("Error", "Username must be at least 3 characters and contain no special characters", AlertType.ERROR);
            return;
        }

        // Email validation
        if (!isValidEmail(email)) {
            showAlert("Error", "Please enter a valid email address", AlertType.ERROR);
            return;
        }

        // Password validation (Minimum 8 characters, one uppercase, one digit, and one special character)
        if (!isValidPassword(password)) {
            showAlert("Error", "Password must be at least 8 characters and contain at least one uppercase letter, one digit, and one special character", AlertType.ERROR);
            return;
        }

        // Optionally, you can hash the password here for better security
        // password = hashPassword(password); // You can hash the password for better security before saving it.

        // Add user data to the database
        try (Connection connection = UserConnection.getConnection()) {
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            // Execute the update and check if the insertion is successful
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Sign-up successful!", AlertType.INFORMATION);
            } else {
                showAlert("Error", "Sign-up failed!", AlertType.ERROR);
            }

        } catch (SQLException e) {
            // Print out the exception stack trace for debugging purposes
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage(), AlertType.ERROR);
        }

        // After sign-up, redirect to the Login Page
        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to go back to the Login Page from SignUp Page
    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to show an alert with the given title, message, and alert type
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validate username: No spaces or special characters, minimum length 3
    private boolean isValidUsername(String username) {
        return username.length() >= 3 && !username.contains(" ") && username.matches("[a-zA-Z0-9]+");
    }

    // Validate email using a regex pattern
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validate password: Minimum 8 characters, one uppercase letter, one digit, and one special character
    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")  // At least one uppercase letter
                && password.matches(".*[0-9].*")  // At least one digit
                && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");  // At least one special character
    }
}
