module com.example.grpro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.grpro to javafx.fxml;
    exports com.example.grpro;
    exports com.example.grpro.controller;
    opens com.example.grpro.controller to javafx.fxml;
}