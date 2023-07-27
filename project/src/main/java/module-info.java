module c195.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;


    opens c195.project to javafx.graphics, javafx.fxml, javafx.base;
    exports c195.project;
    exports c195.project.controller;
    opens c195.project.controller to javafx.graphics, javafx.fxml, javafx.base;
    opens c195.project.helper to javafx.graphics, javafx.fxml, javafx.base;
    opens c195.project.model to javafx.graphics, javafx.fxml, javafx.base;
}