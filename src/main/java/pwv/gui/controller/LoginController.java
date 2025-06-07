package pwv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pwv.repository.impl.UserRepositoryImpl;
import pwv.service.UserService;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable{
    @FXML private MenuBar scenePane;
    @FXML private Button loginButton;
    @FXML private Button createNewUserButton;
    @FXML private Label errorLabel;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ImageView bannerImage;
    @FXML private VBox centerVBox;

    private Stage stage;
    private String email;
    private String password;

    UserRepositoryImpl userRepo = new UserRepositoryImpl();
    UserService userService = new UserService(userRepo);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailField.requestFocus();
       Image img = new Image(
            getClass().getResource("/pwv/banner.png").toExternalForm(),
            400,    // requestedWidth
            0,      // requestedHeight (0 = auto)
            true,   // preserveRatio
            true    // smooth
        );
        bannerImage.setImage(img);
        bannerImage.setPreserveRatio(true);

        // Damit die VBox sich nicht selbst aufbläht:
        centerVBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
        centerVBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        loginButton.setDefaultButton(true);
    }

    public void login(ActionEvent event){
        
        email = emailField.getText();
        password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Input is required.");
        } else {
            try{
                if(userService.checkPassword(email, password)){
                    System.out.println("Logging in with email: " + email);
                }
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                Image icon = new Image(getClass().getResource("/pwv/icon.png").toExternalForm());
                alertStage.getIcons().add(icon);
                alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/pwv/style/style.css").toExternalForm()
                );

                alert.setTitle("An error has occurred.");
                alert.setHeaderText("invalid login credentials.");
                alert.showAndWait();
            }
        }
    }

    public void createNewUser(){}
}
