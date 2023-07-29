package c195.project.controller;

import c195.project.Main;
import c195.project.helper.apptQuery;
import c195.project.helper.contactsQuery;
import c195.project.helper.userQuery;
import c195.project.model.appointments;
import c195.project.model.contacts;
import c195.project.model.customers;
import c195.project.model.users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * addApptScreenController - Controller to add appointments to the program.
 */
public class addApptScreenController implements Initializable {

    @FXML
    private TextField titleTxt;
    @FXML
    private TextField descTxt;
    @FXML
    private TextField locTxt;
    @FXML
    private ComboBox<customers> customerCb;
    @FXML
    private ComboBox<contacts> contactIdCb;
    @FXML
    private TextField userIdTxt;
    @FXML
    private ComboBox typeCb;
    @FXML
    private ComboBox startTime;
    @FXML
    private ComboBox endTime;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TableView<appointments> appTblView;
    @FXML
    private TableColumn<?,?> apptIdCol;
    @FXML
    private TableColumn<?,?> titleCol;
    @FXML
    private TableColumn<?,?> descriptionCol;
    @FXML
    private TableColumn<?,?> locationCol;
    @FXML
    private TableColumn<?,?> contactCol;
    @FXML
    private TableColumn<?,?> typeCol;
    @FXML
    private TableColumn<?,?> startTimeCol;
    @FXML
    private TableColumn<?,?> endTimeCol;
    @FXML
    private TableColumn<?,?> custIdCol;
    @FXML
    private TableColumn<?,?> userIdCol;
    @FXML
    private TableColumn<?,?> createCol;
    @FXML
    private TableColumn<?,?> createdByCol;
    @FXML
    private TableColumn<?,?> lastUpdateCol;
    @FXML
    private TableColumn<?,?> lastUpdatedByCol;
    private static users currUser;
    private appointments currentAppointment = new appointments(1,"initial","first","here","initial",LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),"Bob",Timestamp.valueOf(LocalDateTime.now()),"Bob",1,1,1);

    Stage stage;
    Parent scene;

    /**
     * a method to instantiate actions to save information on press of Save button.
     * @param actionEvent - Save button to save appointment data.
     */
    public void saveButtonOnAction(ActionEvent actionEvent) {
        try{
            if (titleTxt.getText().isEmpty() ||
                    descTxt.getText().isEmpty() ||
                    locTxt.getText().isEmpty()) {
                userQuery.errorMessage("Please fill in all the boxes");
                return;
            } else if (startDatePicker.getValue() == null) {
                userQuery.errorMessage("Please pic a start date");
                return;
            } else if (typeCb.getValue() == null) {
                userQuery.errorMessage("Please pick an appointment type");
                return;
            } else if (startTime.getValue() == null) {
                userQuery.errorMessage("Please select a start time");
                return;
            } else if (endTime.getValue() == null) {
                userQuery.errorMessage("Please select an end time");
                return;
            } else if (customerCb.getValue() == null) {
                userQuery.errorMessage("Please select a customer");
                return;
            } else if (contactIdCb.getValue() == null) {
                userQuery.errorMessage("Please select a contact");
            }
            LocalDateTime sTime = (LocalDateTime.of(startDatePicker.getValue(), (LocalTime) startTime.getValue()));
            LocalDateTime eTime = (LocalDateTime.of(startDatePicker.getValue(), (LocalTime) endTime.getValue()));
            Timestamp tsStart = Timestamp.valueOf(sTime);
            Timestamp tsEnd = Timestamp.valueOf(eTime);
            if (eTime.isBefore(sTime)) {
                userQuery.errorMessage("Start must be before end");
                return;
            }
            ZonedDateTime localStart = sTime.atZone(ZoneId.systemDefault());
            ZonedDateTime estStart = localStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime startEastern = estStart.toLocalTime();
            LocalTime businessStart = LocalTime.of(8,0);
            if (startEastern.isBefore(businessStart)) {
                userQuery.errorMessage("Start time is out of business hours");
                return;
            }
            ZonedDateTime localEnd = eTime.atZone(ZoneId.systemDefault());
            ZonedDateTime estEnd = localEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime endEastern = estEnd.toLocalTime();
            LocalTime businessEnd = LocalTime.of(22,0);
            if (endEastern.isAfter(businessEnd)) {
                userQuery.errorMessage("End time is out of business hours");
                return;
            }

            Instant instant = Instant.now();
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);


            customers c = customerCb.getValue();
            int customerId = c.getCustomer_id();
            contacts co = contactIdCb.getValue();
            int contactId = co.getContact_ID();

            //setting current appointment object to all the filled out fields
            currentAppointment.setTitle(titleTxt.getText());
            currentAppointment.setDescription(descTxt.getText());
            currentAppointment.setLocation(locTxt.getText());
            currentAppointment.setType(String.valueOf(typeCb.getValue()));
            currentAppointment.setStartTime(sTime);
            currentAppointment.setEndTime(eTime);
            currentAppointment.setCreate_date(ldt);
            currentAppointment.setCreated_by(currUser.getUser_name());
            currentAppointment.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
            currentAppointment.setLast_updated_by(currUser.getUser_name());
            currentAppointment.setUser_id(currUser.getUser_ID());
            currentAppointment.setCustomer_id(customerId);
            currentAppointment.setContact_id(contactId);

            //create logic statements
            if (currentAppointment.getStartTime().isAfter(currentAppointment.getEndTime())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR!");
                alert.setContentText("End time cannot be before Start Time!");
                alert.showAndWait();
                return;
            } else if (currentAppointment.getStartTime().equals(currentAppointment.getEndTime())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR!");
                alert.setContentText("Start time cannot be equal to End time!");
                alert.showAndWait();
                return;
            }

            // need to have a logic to compare to other appointments
            if (currentAppointment.getTitle().isEmpty() ||
                    currentAppointment.getDescription().isEmpty() ||
                    currentAppointment.getLocation().isEmpty() ||
                    currentAppointment.getType().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR!");
                alert.setContentText("Please fill in all the fields");
                alert.showAndWait();
                return;
            }

            if (afterStartEq(apptQuery.bookedStartTimes(currentAppointment.getCustomer_id())) &&
                beforeEnd(apptQuery.bookedStartTimes(currentAppointment.getCustomer_id()))) {
                userQuery.errorMessage("TIME OVERLAP1");
                return;
            } else if (afterStart(apptQuery.bookedEndTimes(currentAppointment.getCustomer_id())) &&
                beforeEndEq(apptQuery.bookedEndTimes(currentAppointment.getCustomer_id()))) {
                userQuery.errorMessage("TIME OVERLAP2");
                return;
            } else if (beforeStartEq(apptQuery.bookedStartTimes(currentAppointment.getCustomer_id())) &&
                afterEndEq(apptQuery.bookedEndTimes(currentAppointment.getCustomer_id()))) {
                userQuery.errorMessage("TIME OVERLAP 3");
                return;
            }

            apptQuery.insert(currentAppointment.getTitle(), currentAppointment.getDescription(), currentAppointment.getLocation(), currentAppointment.getType(), tsStart, tsEnd, Timestamp.valueOf(LocalDateTime.now()), currentAppointment.getCreated_by(), currentAppointment.getLast_update(), currentAppointment.getLast_updated_by(), currentAppointment.getCustomer_id(),currentAppointment.getUser_id(),currentAppointment.getContact_id());
            appTblView.setItems(apptQuery.getAllAppointments());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param stTimes - observable list of all start times in the database
     * @return - boolean value if local time and date in database is equal to or before the requesting start time
     */
    public boolean beforeStartEq(ObservableList<LocalDateTime> stTimes) {
        for (LocalDateTime ldt : stTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isBefore((LocalTime) startTime.getValue()) && lDate.isEqual(startDatePicker.getValue()) ||
                    lTime.equals(startTime.getValue()) && lDate.isEqual(startDatePicker.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param stTimes - observable list of all start times in the database
     * @return - boolean value if local time and date in database is after the requesting start time
     */
    public boolean afterStart(ObservableList<LocalDateTime> stTimes) {
        for (LocalDateTime ldt : stTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isAfter((LocalTime) startTime.getValue()) && lDate.isEqual(startDatePicker.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param stTimes - observable list of all start times in the database
     * @return - boolean value if local time and date in database is after or equal to the requesting start time
     */
    public boolean afterStartEq(ObservableList<LocalDateTime> stTimes) {
        for (LocalDateTime ldt : stTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isAfter((LocalTime) startTime.getValue()) && lDate.isEqual(startDatePicker.getValue())||
                    lTime.equals( startTime.getValue()) && lDate.isEqual(startDatePicker.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param endTimes - observable list of all end times in the database
     * @return - boolean value if local time and date in database is after or equal to the requesting end time
     */
    public boolean afterEndEq(ObservableList<LocalDateTime> endTimes) {
        for (LocalDateTime ldt : endTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isAfter((LocalTime) endTime.getValue()) && lDate.isEqual(startDatePicker.getValue()) ||
                    lTime.equals( endTime.getValue()) && lDate.isEqual(startDatePicker.getValue())){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param endTimes - observable list of all end times in the database
     * @return - boolean value if local time and date in database is before the requesting end time
     */
    public boolean beforeEnd(ObservableList<LocalDateTime> endTimes) {
        for (LocalDateTime ldt : endTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isBefore((LocalTime) endTime.getValue()) && lDate.isEqual(startDatePicker.getValue())){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param endTimes - observable list of all end times in the database
     * @return - boolean value if local time and date in database is before or equal to the requesting end time
     */
    public boolean beforeEndEq(ObservableList<LocalDateTime> endTimes) {
        for (LocalDateTime ldt : endTimes) {
            LocalTime lTime = ldt.toLocalTime();
            LocalDate lDate = ldt.toLocalDate();
            if (lTime.isBefore((LocalTime) endTime.getValue()) && lDate.isEqual(startDatePicker.getValue()) ||
                    lTime.equals(endTime.getValue()) && lDate.isEqual(startDatePicker.getValue())){
                return true;
            }
        }
        return false;
    }

    /**
     * Control to go back to main appointment screen
     * @param actionEvent - upon press of Cancel button, returns to main Appointment screen.
     */
    public void cancelButton(ActionEvent actionEvent) {
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

    /**
     * to preserve user object information when updating created by or last modified by fields
     * @param user - user object received from the main appointment screen.
     */
    public void sendUser(users user) {
        currUser = user;
        userIdTxt.setText(String.valueOf(currUser.getUser_ID()));
    }

    /**
     * initializes on screen start. Initializes tableview of all appointments and populate datePicker and comboBoxes.
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
        appTblView.setItems(apptQuery.getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        createCol.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("created_by"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("last_updated_by"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_id"));

        startDatePicker.setValue(LocalDate.now());
        startTime.setItems(apptQuery.getAppointmentStart(0));
        endTime.setItems(apptQuery.getAppointmentEnd(0));
        contactIdCb.setItems(contactsQuery.getAllContacts());
        customerCb.setItems(customers.getAllCustomers());
        try {
            typeCb.setItems(apptQuery.appType());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
