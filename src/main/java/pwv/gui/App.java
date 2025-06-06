package pwv.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();  
            logout(stage);
        });

        stage.setTitle("Password Vault");
        Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
        stage.getIcons().add(icon);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pwv/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void logout(Stage stage) {
    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
        alertStage.getIcons().add(icon);

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("exiting...");
            stage.close();
        }
    }


    public static void main(String[] args) {
        launch();
    }

}