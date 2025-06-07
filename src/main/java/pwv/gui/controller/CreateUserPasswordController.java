package pwv.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pwv.gui.App;
import pwv.repository.impl.UserRepositoryImpl;
import pwv.service.UserService;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CreateUserPasswordController implements Initializable{
    @FXML private MenuBar scenePane;
    @FXML private Button backButton;
    @FXML private Button signUpButton;
    @FXML private Label errorLabel;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repeatPasswordField;
    @FXML private ImageView bannerImage;
    @FXML private VBox centerVBox;

    private String password;
    private String repeatPassword;
    private String name = "placeholder";
    private String email = "placeholder@placeholder.de";

    private UserRepositoryImpl userRepo = new UserRepositoryImpl();
    private UserService userService = new UserService(userRepo);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordField.requestFocus();
        Image img = new Image(
            getClass().getResource("/pwv/banner.png").toExternalForm(),
            400,    // requestedWidth
            0,      // requestedHeight (0 = auto)
            true,   // preserveRatio
            true    // smooth
        );
        bannerImage.setImage(img);
        bannerImage.setPreserveRatio(true);
        centerVBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
        centerVBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        signUpButton.setDefaultButton(true);
    }

    @FXML
    public void back() throws IOException{
        App.setRoot("createUser");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }   

    @FXML
    public void signUp() throws IOException {
        
        password = passwordField.getText();
        repeatPassword = repeatPasswordField.getText();
        if(password.isEmpty() || repeatPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
            return;
        }
        if (!password.equals(repeatPassword)) {
            errorLabel.setText("Passwords do not match!");
            return;
        }
        userService.registerNewUser(name, email, password);
        System.out.println("User created successfully: " + name + " (" + email + ")");
        App.setRoot("login");
    }
}
