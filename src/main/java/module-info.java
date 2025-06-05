module pwv {
    requires javafx.controls;
    requires javafx.fxml;

    opens pwv to javafx.fxml;
    exports pwv;
}
