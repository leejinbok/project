package c195.project.controller;

import c195.project.Main;
import c195.project.helper.apptQuery;
import c195.project.helper.userQuery;
import c195.project.model.appointments;
import c195.project.model.users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * <p><b>Controller for log-in screen.</b></p>
 * Displays system zone ID and checks for system default language (FR/EN).
 * <p>Checks for user information and passes the user object to the next screen.</p>
 * Logs all successful and unsuccessful attempts at log-in.
 */
public class loginScreenController implements Initializable {
    @FXML
    private Label zoneIdLbl;
    @FXML
    private Button enterButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginScreenLabel;
    @FXML
    private Label loginIdLbl;
    @FXML
    private Label loginPwLbl;
    Stage stage;
    Parent scene;
    @FXML
    private TextField pwTxt;
    @FXML
    private TextField idTxt;

    private String wrongInfo = "";

    /**
     * <p><b>Log-in check / validation</b></p>
     * checks to match ID and password values to the database and allow user access. Logs on success/fail
     * checks all appointment scheduled times to see if there's any appointments within 15 minutes of current login time.
     * @param actionEvent - on press of login button, with correct values for id/pw filled out - user will be granted access to appointments screen.
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     */
    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter("logon_activity.txt", true)));

        ObservableList<appointments> allAppointments = apptQuery.getAllAppointments();
        LocalDateTime localTimePlus15 = LocalDateTime.now().plusMinutes(15);
        LocalDateTime localTimeMinus15 = LocalDateTime.now().minusMinutes(15);
        boolean upcomingAppointment = false;
        int appointmentId = 0;
        LocalDateTime apptTime = LocalDateTime.now();

        ResourceBundle rbs = ResourceBundle.getBundle("nat", Locale.FRANCE);

        String id = idTxt.getText();
        String DBpw = userQuery.getPassword(id).toString();

        wrongInfo = "You have entered incorrect information";
        if (idTxt.getText().isEmpty() || pwTxt.getText().isEmpty()) {
            userQuery.errorMessage("Please enter a username and password");
            out.println("Login Unsuccessful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
            out.flush();
            return;
        }
        if (!DBpw.contains(pwTxt.getText()) && (Locale.getDefault().getLanguage().equals("fr"))) {
            userQuery.errorMessage(rbs.getString("wronginfo"));
            out.println("Login Unsuccessful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
            out.flush();
            return;
        } else if (!DBpw.contains(pwTxt.getText())) {
            userQuery.errorMessage(wrongInfo);
            out.println("Login Unsuccessful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
            out.flush();
            return;
        }

        out.println("Login Successful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
        out.flush();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("apptScreen.fxml"));
        loader.setLocation(Main.class.getResource("apptScreen.fxml"));
        Parent root = loader.load();
        apptScreenController apptScreenController = loader.getController();
        apptScreenController.sendUser(userQuery.select(id));

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Window");
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();

        for (appointments appt : allAppointments) {
            if (appt.getStartTime().isEqual(localTimeMinus15) || appt.getStartTime().isAfter(localTimeMinus15) &&
                    appt.getStartTime().isBefore(localTimePlus15) || appt.getStartTime().equals(localTimePlus15)) {
            upcomingAppointment = true;
            appointmentId = appt.getAppointmentID();
            apptTime = appt.getStartTime();
            }
        }
        if (upcomingAppointment) {
            userQuery.infoMessage("You have an upcoming appointment!\n" +
                    "Appointment ID: " + appointmentId + "\n" +
                    "Start Time: " + apptTime);
        } else {
            userQuery.infoMessage("No upcoming appointments!");
        }
        out.close();
    }

    /**
     * exits program on press of cancel button.
     * @param actionEvent - on press of cancel button
     */
    public void cancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * initialized on start; sets zoneID text to current system zone ID.
     * <p>sets current resource bundle to check for FR settings and change labels appropriately</p>
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ZoneId myZoneId = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + myZoneId.getId());
        ResourceBundle rb = ResourceBundle.getBundle("nat",Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            loginScreenLabel.setText(rb.getString("login"));
            cancelButton.setText(rb.getString("cancel"));
            enterButton.setText(rb.getString("enter"));
            loginIdLbl.setText(rb.getString("loginid"));
            loginPwLbl.setText(rb.getString("password"));

        }
    }
}