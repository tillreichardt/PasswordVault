package pwv.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pwv.repository.impl.UserRepositoryImpl;
import pwv.service.UserService;

public class LoginController {
    @FXML
    private MenuBar scenePane;
    @FXML
    private Button loginButton;
    @FXML
    private Button createNewUserButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private String email;
    private String password;

    UserRepositoryImpl userRepo = new UserRepositoryImpl();
    UserService userService = new UserService(userRepo);

    public void login(ActionEvent event){
        
        email = emailField.getText();
        password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
            alertStage.getIcons().add(icon);

            alert.setTitle("Login Error");
            alert.setHeaderText("Please enter both email and password.");
            alert.showAndWait();
        } else {
            try{
                if(userService.checkPassword(email, password)){
                    System.out.println("Logging in with email: " + email);
                }
            } catch (IllegalArgumentException e) {
                errorLabel.setText("Login failed: " + e.getMessage());
            }
           
            
        }
    }

    public void createNewUser(){}
}
