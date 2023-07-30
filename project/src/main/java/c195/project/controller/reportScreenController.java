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

/**
 * controller for contacts report screen
 */
public class reportScreenController {
    @FXML
    private TableColumn<appointments, Integer> userIdCol;
    @FXML
    private TableColumn<appointments, Integer> custIdCol;
    @FXML
    private TableColumn<appointments,LocalDateTime> endTimeCol;
    @FXML
    private TableColumn<appointments,LocalDateTime> startTimeCol;
    @FXML
    private TableColumn<appointments,String> typeCol;
    @FXML
    private TableColumn<appointments,String> descriptionCol;
    @FXML
    private TableColumn<appointments,String> titleCol;
    @FXML
    private TableColumn<appointments, Integer> apptIdCol;
    @FXML
    private TableView<appointments> contactTblView;
    Stage stage;
    Parent scene;
    private appointments customAppointment = new appointments(1,"initial","first","here","initial", LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),"Bob", Timestamp.valueOf(LocalDateTime.now()),"Bob",1,1,1);

    /**
     * <p><b>LAMBDA EXPRESSION</b></p>
     * a function to manually set values of current custom appointment values from previous screen.
     * <p>lambda expression in forEach is used to succinctly and cleanly pass in the values from appointments ObservableList to current appointments</p>
     * @param contactAppointment - takes in parameter divs from SQL lookup of division names
     */
    public void sendReport(ObservableList<appointments> contactAppointment) {
        contactAppointment.forEach(currAppt -> customAppointment.setAppointmentID(currAppt.getAppointmentID()));
        contactAppointment.forEach(currAppt -> customAppointment.setTitle(currAppt.getTitle()));
        contactAppointment.forEach(currAppt -> customAppointment.setDescription(currAppt.getDescription()));
        contactAppointment.forEach(currAppt -> customAppointment.setType(currAppt.getType()));
        contactAppointment.forEach(currAppt -> customAppointment.setStartTime(currAppt.getStartTime()));
        contactAppointment.forEach(currAppt -> customAppointment.setEndTime(currAppt.getEndTime()));
        contactAppointment.forEach(currAppt -> customAppointment.setCustomer_id(currAppt.getCustomer_id()));
        contactAppointment.forEach(currAppt -> customAppointment.setContact_id(currAppt.getContact_id()));
        contactAppointment.forEach(currAppt -> customAppointment.setLocation(currAppt.getLocation()));
        contactAppointment.forEach(currAppt -> customAppointment.setUser_id(currAppt.getUser_id()));
        contactAppointment.forEach(currAppt -> customAppointment.setCreated_by(currAppt.getCreated_by()));
        contactAppointment.forEach(currAppt -> customAppointment.setCreate_date(currAppt.getCreate_date()));
        contactAppointment.forEach(currAppt -> customAppointment.setLast_updated_by(currAppt.getLast_updated_by()));
        contactAppointment.forEach(currAppt -> customAppointment.setLast_update(currAppt.getLast_update()));

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
    /**
     * returns to main appointments screen
     * @param actionEvent - on press of return button on screen.
     */
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
}
