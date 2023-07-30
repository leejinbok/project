package c195.project;

import c195.project.helper.JDBC;
import c195.project.helper.apptQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p><b><h1>Appointment Scheduler Application</h1></b></p>
 * Main class creates application that starts the FXMLLoader to login screen and starts the application.
 */
public class Main extends Application {
    /**
     * start method to start the program.
     * @param stage - parameter stage to start the GUI application
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * launches the application - opens the database connection, launches the application, and closes the database connection once complete.
     * @param args - start main method
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();

    }
}