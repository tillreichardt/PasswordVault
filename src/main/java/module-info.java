module pwv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pwv to javafx.fxml;
    exports pwv;
}
