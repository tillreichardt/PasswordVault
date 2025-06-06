package pwv.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LogoutController {
    @FXML
    private Button logoutButton;
    @FXML
    private MenuBar scenePane;

    Stage stage;

    public void logout(ActionEvent event) {
    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Do you want to save before logging out?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
        alertStage.getIcons().add(icon);

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Logging out...");
            stage.close();
        }
    }
}
