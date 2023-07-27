package c195.project.controller;

import c195.project.Main;
import c195.project.model.appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class reportScreenController implements Initializable {
    @FXML
    private TableColumn<?,?> userIdCol;
    @FXML
    private TableColumn<?,?> custIdCol;
    @FXML
    private TableColumn<?,?> endTimeCol;
    @FXML
    private TableColumn<?,?> startTimeCol;
    @FXML
    private TableColumn<?,?> typeCol;
    @FXML
    private TableColumn<?,?> descriptionCol;
    @FXML
    private TableColumn<?,?> titleCol;
    @FXML
    private TableColumn<?,?> apptIdCol;
    @FXML
    private TableView<appointments> contactTblView;
    Stage stage;
    Parent scene;
    private appointments customAppointment = new appointments(1,"initial","first","here","initial", LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),"Bob", Timestamp.valueOf(LocalDateTime.now()),"Bob",1,1,1);

    public void sendReport(ObservableList<appointments> contactAppointment) {

        for (appointments currAppt : contactAppointment) {
            customAppointment.setAppointmentID(currAppt.getAppointmentID());
            customAppointment.setTitle(currAppt.getTitle());
            customAppointment.setDescription(currAppt.getDescription());
            customAppointment.setType(currAppt.getType());
            customAppointment.setStartTime(currAppt.getStartTime());
            customAppointment.setEndTime(currAppt.getEndTime());
            customAppointment.setCustomer_id(currAppt.getCustomer_id());
            customAppointment.setUser_id(currAppt.getUser_id());

            contactTblView.setItems(contactAppointment);
            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        }
    }

    public void returnButton(ActionEvent actionEvent) {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Main.class.getResource("apptScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
