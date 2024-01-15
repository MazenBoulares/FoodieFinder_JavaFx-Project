module com.example.javefx_project_v3.javefx_project_v3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.javefx_project_v3 to javafx.fxml;
    exports com.example.javefx_project_v3;
    exports com.example.javefx_project_v3.Controllers;
    opens com.example.javefx_project_v3.Controllers to javafx.fxml;
}