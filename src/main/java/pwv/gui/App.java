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
        try {
            scene = new Scene(loadFXML("login"));
            stage.setScene(scene);
            stage.show();
            // scene.getStylesheets().add(getClass().getResource("/pwv/style/style.css").toExternalForm());
            String css = this.getClass().getResource("/pwv/style/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setOnCloseRequest(event -> {
                event.consume();  
                logout(stage);
            });

            stage.setResizable(true);
            stage.setMinWidth(1100);
            stage.setMinHeight(700);
            stage.setTitle("Password Vault");
            Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
            stage.getIcons().add(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/pwv/style/alertStyle.css").toExternalForm()
        );

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("exiting...");
            stage.close();
        }
    }


    public static void main(String[] args) {
        launch();
    }

}