module pwv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Öffnet das GUI-Paket und das Controller-Paket für JavaFX
    opens pwv.gui to javafx.fxml;
    opens pwv.gui.controller to javafx.fxml;

    // Exporte für das GUI und Controller-Paket
    exports pwv.gui;
    exports pwv.gui.controller;
}
