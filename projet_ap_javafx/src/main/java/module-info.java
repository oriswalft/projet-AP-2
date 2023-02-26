module com.example {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;

    opens com.example.Controllers to javafx.fxml;
    opens com.example.Controllers.Visiteur to javafx.fxml;
    exports com.example;
}
