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

    private String error = "";
    private String wrongInfo = "";


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

            return;
        }
        if (!DBpw.contains(pwTxt.getText()) && (Locale.getDefault().getLanguage().equals("fr"))) {
            userQuery.errorMessage(rbs.getString("wronginfo"));
            out.println("Login Unsuccessful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
            return;
        } else if (!DBpw.contains(pwTxt.getText())) {
            userQuery.errorMessage(wrongInfo);
            out.println("Login Unsuccessful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));
            return;
        }

        out.println("Login Successful for user ID : " + idTxt.getText() + " at " + Timestamp.valueOf(LocalDateTime.now()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("apptScreen.fxml"));
        loader.setLocation(Main.class.getResource("apptScreen.fxml"));
        Parent root = loader.load();
        apptScreenController apptScreenController = loader.getController();
        apptScreenController.sendUser(userQuery.getPw(id));

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

    public void cancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

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