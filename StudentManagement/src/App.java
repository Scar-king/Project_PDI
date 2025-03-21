import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\LoginPage.fxml"));
        Image icon = new Image("Image\\itc.png");
        stage.getIcons().add(icon);
        stage.setTitle("Login");
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}