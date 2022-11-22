module com.example.grpro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.grpro to javafx.fxml;
    exports com.example.grpro;
}