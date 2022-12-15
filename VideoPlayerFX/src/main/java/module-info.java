module com.GrPro.videoplayerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;


    opens com.GrPro.streamService.Controllers to javafx.fxml;
    opens com.GrPro.streamService.Model to javafx.fxml;
    exports com.GrPro.streamService.Controllers;
    exports com.GrPro.streamService.Model;


}