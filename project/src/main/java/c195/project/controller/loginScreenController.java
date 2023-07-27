package c195.project.controller;

import c195.project.Main;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Locale;
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
        //check username and pw
        //provide error messages
        ObservableList<appointments> appointments = FXCollections.observableArrayList();
        ResourceBundle rbs = ResourceBundle.getBundle("nat", Locale.FRANCE);

        String pw = idTxt.getText();
        String DBpw = userQuery.getPassword(pw).toString();

        wrongInfo = "You have entered incorrect information";

        if (!DBpw.contains(pwTxt.getText()) && (Locale.getDefault().getLanguage().equals("fr"))) {
            userQuery.errorMessage(rbs.getString("wronginfo"));
            return;
        } else if (!DBpw.contains(pwTxt.getText())) {
            userQuery.errorMessage(wrongInfo);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("apptScreen.fxml"));
        loader.setLocation(Main.class.getResource("apptScreen.fxml"));
        Parent root = loader.load();
        apptScreenController apptScreenController = loader.getController();
        apptScreenController.sendUser(userQuery.getPw(pw));

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Window");
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
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