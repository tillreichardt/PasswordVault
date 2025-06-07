package pwv.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pwv.service.UserService;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import pwv.gui.App;
import pwv.repository.impl.UserRepositoryImpl;
import pwv.util.EmailValidator;

public class CreateUserController implements Initializable{
    @FXML private MenuBar scenePane;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Label errorLabel;
    @FXML private TextField emailField;
    @FXML private TextField nameField;
    @FXML private ImageView bannerImage;
    @FXML private VBox centerVBox;

    private String email;
    private String name;
    private Parent root;

    private EmailValidator emailValidator = new EmailValidator();
    private UserRepositoryImpl userRepo = new UserRepositoryImpl();
    private UserService userService = new UserService(userRepo);

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
        centerVBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
        centerVBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        nextButton.setDefaultButton(true);
    }

    @FXML
    public void nextButtonAction() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pwv/fxml/createUserPassword.fxml"));
        root = loader.load();

        CreateUserPasswordController controller = loader.getController();
        

        email = emailField.getText();
        name = nameField.getText();
        if (email.isEmpty() || name.isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
            return;
        }
        if(userService.checkEmailExists(email)) {
            errorLabel.setText("Email is already in use.");
            return;
        }
        if(emailValidator.isValid(email) == false) {
            errorLabel.setText("Invalid email format.");
            return;
        }
        controller.setEmail(email);
        controller.setName(name);
        
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void aleadyHaveAnAccount() throws IOException {
        App.setRoot("login");
    }
}
