module com.example {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;

    opens com.example.Controllers to javafx.fxml;
    exports com.example;
}
