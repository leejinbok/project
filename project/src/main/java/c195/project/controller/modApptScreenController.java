package c195.project.controller;

import c195.project.Main;
import c195.project.helper.apptQuery;
import c195.project.helper.contactsQuery;
import c195.project.helper.customersQuery;
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
 * Controller to modify selected appointment from previous screen.
 */
public class modApptScreenController implements Initializable {
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
    private TextField apptIdTxt;
    @FXML
    private ComboBox startTime;
    @FXML
    private ComboBox endTime;
    @FXML
    private DatePicker startDatePicker;
    private appointments currentAppointment;
    private static users currUser;
    private customers currCustomer;
    private contacts currContacts;
    Stage stage;
    Parent scene;

    /**
     * Button to save the current appointment information.
     * <p>Data is retrieved and populated from selected appointment from previous screen.</p>
     * Values are populated, and any changes made is checked for null values before saving.
     * @param actionEvent - on press of save button on screen
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
                return;
            }

            // creating and assigning local variables to appointment data
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

            //set appropriate values for current appointments if they had been updated
            currentAppointment.setTitle(titleTxt.getText());
            currentAppointment.setDescription(descTxt.getText());
            currentAppointment.setLocation(locTxt.getText());
            currentAppointment.setType(String.valueOf(typeCb.getValue()));
            currentAppointment.setStartTime(sTime);
            currentAppointment.setEndTime(eTime);
            Timestamp tsNow = Timestamp.valueOf(currentAppointment.getCreate_date());
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            currentAppointment.setLast_update(now);
            currentAppointment.setLast_updated_by(currUser.getUser_name());
            currentAppointment.setUser_id(currUser.getUser_ID());
            currentAppointment.setCustomer_id(customerCb.getValue().getCustomer_id());
            currentAppointment.setContact_id(contactIdCb.getValue().getContact_ID());

            //create logic statements
            if (currentAppointment.getStartTime().isAfter(currentAppointment.getEndTime())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR!");
                alert.setContentText("End time cannot be before Start Time!");
                alert.showAndWait();
                return;
            } else if (currentAppointment.getStartTime().equals(currentAppointment.getEndTime())) {
                userQuery.errorMessage("Start time cannot be equal to End time!");
                return;
            }

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

            apptQuery.update(currentAppointment.getTitle(), currentAppointment.getDescription(), currentAppointment.getLocation(), currentAppointment.getType(), tsStart, tsEnd, tsNow, currentAppointment.getCreated_by(), currentAppointment.getLast_update(), currentAppointment.getLast_updated_by(), currentAppointment.getCustomer_id(),currentAppointment.getUser_id(),currentAppointment.getContact_id(), currentAppointment.getAppointmentID());
            userQuery.updatePopup();

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(Main.class.getResource("apptScreen.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * sets current customer values according to the current appointment's customer ID value
     * @param customer - the customer object is passed in with customer ID lookup in the database
     */
    public void customerID(customers customer) {
        currCustomer = customer;
        }

    /**
     * sets current contact values according to the current appointment's contact ID value
     * @param contacts- the contact object is passed in with contact ID lookup in the database
     */
    public void contactID(contacts contacts) {
        currContacts = contacts;
    }

    /**
     * cancel button to go back to the main appointment screen
     * @param actionEvent - on button press of cancel button
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
     * Items initialized when screen opens. The data for the dates, start/end times, customer box and contacts box are populated.
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

    /**
     * method to receive selected appointment and user data from previous screen. Maintains the value of these objects and helps pre-populate the fields.
     * @param appointment - takes in the selected appointment object parameter from previous screen
     * @param user - takes in user data from original login screen to keep consistency and log data.
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public void sendAppointments(appointments appointment, users user) throws SQLException {
        //sets current user and appointment from received values
        currentAppointment = appointment;
        currUser = user;

        contactID(contactsQuery.select(currentAppointment.getContact_id()));
        customerID(customersQuery.select(currentAppointment.getCustomer_id()));

        //populates appropriate text boxes from retrieved values
        apptIdTxt.setText(String.valueOf(currentAppointment.getAppointmentID()));
        titleTxt.setText(String.valueOf(currentAppointment.getTitle()));
        descTxt.setText(String.valueOf(currentAppointment.getDescription()));
        locTxt.setText(String.valueOf(currentAppointment.getLocation()));
        userIdTxt.setText(String.valueOf(currUser.getUser_ID()));
        typeCb.setValue(currentAppointment.getType());
        startDatePicker.setValue(currentAppointment.getStartTime().toLocalDate());
        startTime.setValue(currentAppointment.getStartTime().toLocalTime());
        endTime.setValue(currentAppointment.getEndTime().toLocalTime());

        int customerID = currentAppointment.getCustomer_id();
        int contactID = currentAppointment.getContact_id();

        customerCb.setItems(customers.getAllCustomers());
        customers customers = customersQuery.select(customerID);
        for (customers c : customerCb.getItems()) {
            if (c.getCustomer_id() == customers.getCustomer_id()) {
                customerCb.setValue(c);
            }
        }

        contactIdCb.setItems(contacts.getAllContacts());
        contacts contacts = contactsQuery.select(contactID);
        for (contacts c : contactIdCb.getItems()) {
            if (c.getContact_ID() == contacts.getContact_ID()) {
                contactIdCb.setValue(c);
            }
        }
    }
}
