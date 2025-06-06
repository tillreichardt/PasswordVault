package pwv.gui.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import pwv.gui.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}