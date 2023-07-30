package c195.project.controller;

import c195.project.Main;
import c195.project.helper.apptQuery;
import c195.project.helper.userQuery;
import c195.project.model.*;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * <p>The main screen for the program.</p>
 * The screen contains three different reports:
 * <p>all reports for selected contact</p>
 * <p>number of total appointments for selected month and appointment type</p>
 * <p>number of total appointments</p>
 * There are also various buttons - for customer menu, add/modify/delete appointments, and the cancel button to go back to login screen.
 */
public class apptScreenController implements Initializable {
    @FXML
    private Label totNumLbl;
    @FXML
    private ComboBox<Month> monthBox;
    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private ComboBox<contacts> contactBox;
    @FXML
    private Label totalNumberLbl;
    @FXML
    private TableView<appointments> appTblView;
    @FXML
    private TableColumn<appointments,Integer> apptIdCol;
    @FXML
    private TableColumn<appointments,String> titleCol;
    @FXML
    private TableColumn<appointments,String> descriptionCol;
    @FXML
    private TableColumn<appointments,String> locationCol;
    @FXML
    private TableColumn<appointments,Integer> contactCol;
    @FXML
    private TableColumn<appointments,String> typeCol;
    @FXML
    private TableColumn<appointments,LocalDateTime> startTimeCol;
    @FXML
    private TableColumn<appointments,LocalDateTime> endTimeCol;
    @FXML
    private TableColumn<appointments,Integer> custIdCol;
    @FXML
    private TableColumn<appointments,String> userIdCol;
    @FXML
    private TableColumn<appointments,LocalDateTime> createCol;
    @FXML
    private TableColumn<appointments,String> createdByCol;
    @FXML
    private TableColumn<appointments,Timestamp> lastUpdateCol;
    @FXML
    private TableColumn<appointments,String> lastUpdatedByCol;
    private static users currUser;
            //= new users(1,"bob","1234", LocalDateTime.now(),"script", Timestamp.valueOf(LocalDateTime.now()),"script");
    Stage stage;
    Parent scene;

    /**
     * actions to take on press of exit button on screen. throws runTimeException for incorrect/bad fxml load
     * @param actionEvent - on button press of Exit button
     */
    public void exitButton(ActionEvent actionEvent){
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Main.class.getResource("loginScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * <p>Shows how many appointments are scheduled for the month for the year.</p>
     * <p><b>looks up monthly values for the CURRENT calendar year</b></p>
     * @param month - value of the month selected in the month comboBox.
     * @param type - value of the type selected in the type comboBox.
     * @return - returns size of number of appointments in the selected month
     */
    public int showMonthType(Month month, String type) {
        ObservableList<appointments> totalAppts = apptQuery.getAllAppointments();
        ObservableList<appointments> monthlyAppts = FXCollections.observableArrayList();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthTime = LocalDateTime.of(now.getYear(), month, 1, 0, 0);
        LocalDateTime nextMonthTime = LocalDateTime.of(now.getYear(), month.plus(1), 1, 0, 0);

        if (!totalAppts.isEmpty()) {
            for (appointments appt : totalAppts) {
                if (appt.getType().equals(type) && appt.getStartTime().isAfter(monthTime) && appt.getStartTime().isBefore(nextMonthTime)) {
                    monthlyAppts.add(appt);
                }
            }
        }
        return monthlyAppts.size();
    }

    /**
     * button to add appointments to the schedule. Maintains and sends current user to next screen to maintain user information
     * @param actionEvent - on press of add appointment button, opens new screen and send current user information.
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     */
    public void addApptButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addApptScreen.fxml"));
        loader.setLocation(Main.class.getResource("addApptScreen.fxml"));
        Parent root = loader.load();
        addApptScreenController ApptScreenController = loader.getController();
        ApptScreenController.sendUser(currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * button to customer menu. Opens up a new screen. Maintains and sends current user to next screen to maintain user information
     * @param actionEvent - on press of customer menu button
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     */
    public void addCustomerBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCustScreen.fxml"));
        loader.setLocation(Main.class.getResource("addCustScreen.fxml"));
        Parent root = loader.load();
        addCustScreenController addCustScreenController = loader.getController();
        addCustScreenController.sendUser(currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Initialize method to populate values into tableView, comboBox, and columns. Also initializes and populates total number of appointments.
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

        contactBox.setItems(contacts.getAllContacts());
        contactBox.setPromptText("Contacts");
        monthBox.setItems(apptQuery.month());
        monthBox.setPromptText("Month of CURRENT year");
        try {
            typeBox.setItems(apptQuery.appType());
            typeBox.setPromptText("Appointment Type");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            totalNumberLbl.setText(String.valueOf(apptQuery.apptCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * receives user login data into current appointment screen.
     * @param users - user object retrieved from login screen
     */
    public void sendUser (users users) {
        currUser = users;
    }

    /**
     * button to delete selected appointments from the tableview. Asks user for confirmation of deletion and appointment ID and type that was deleted.
     * @param actionEvent - on press of delete button and selected cell value in the tableView
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public void deleteButton(ActionEvent actionEvent) throws SQLException, NullPointerException {
        appointments appointment = appTblView.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            userQuery.errorMessage("Please select an appointment to delete");
            return;
        }
        int apptID = appointment.getAppointmentID();
        String apptType = appointment.getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you wish to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK) {
            apptQuery.deleteAppts(((appTblView.getSelectionModel().getSelectedItem())).getAppointmentID());
            appTblView.setItems(apptQuery.getAllAppointments());
            try {
                totalNumberLbl.setText(String.valueOf(apptQuery.apptCount()));
                userQuery.infoMessage("Appointment number | " + apptID + " | type | " + apptType + " | has been deleted");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * button to modify selected appointment from appointments tableView. Sends selected appointment and user data to next screen to modify.
     * @param actionEvent - on button press of modify appointment after selecting an appointment from appointments tableView
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public void modApptBtn(ActionEvent actionEvent) throws IOException, SQLException, NullPointerException {
        appointments appointments = appTblView.getSelectionModel().getSelectedItem();
        if (appointments == null) {
            userQuery.errorMessage("Please select an appointment to edit");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("modApptScreen.fxml"));
        loader.setLocation(Main.class.getResource("modApptScreen.fxml"));
        Parent root = loader.load();

        modApptScreenController modApptScreenController = loader.getController();
        modApptScreenController.sendAppointments(appointments, currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * <p><b>First Report</b></p>generates a report for all appointments booked for selected contact from contacts comboBox
     * @param actionEvent - on press of report button
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public void reportButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reportScreen.fxml"));
            loader.setLocation(Main.class.getResource("reportScreen.fxml"));
            Parent root = loader.load();

            reportScreenController reportScreenController = loader.getController();
            reportScreenController.sendReport(apptQuery.getContactAppointments(contactBox.getValue()));

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root).getRoot();
            stage.setScene(scene.getScene());
            stage.centerOnScreen();
            stage.show();
        } catch (NullPointerException e) {
            userQuery.errorMessage("Please select a contact to generate a report for!");
        }
    }

    /**
     * <p><b>Second Report</b></p> - selects month from month comboBox (current year) and type from type comboBox.
     * <p>generates a number for all appointments with selected month and selected type</p>
     * catches nullPointerException for not selecting month or type values.
     * @param actionEvent - on press of generate report button, after selecting month and type boxes.
     */
    public void genReport(ActionEvent actionEvent) {
        try {
            totNumLbl.setText(String.valueOf(showMonthType(monthBox.getSelectionModel().getSelectedItem(), typeBox.getValue())));
        } catch (NullPointerException e) {
            userQuery.errorMessage("Please select month and appointment type to generate report!");
        }
    }

    /**
     * <p><b>Third Report</b></p>
     * On press of month radioButton, select all appointments within a month (+/-)
     * @param actionEvent - on press of radio button, populate the current appointments table with all appointments within a month of today's date
     */
    public void monthRadio(ActionEvent actionEvent) {
        ObservableList<appointments> allAppointments = apptQuery.getAllAppointments();
        ObservableList<appointments> monthAppointments = FXCollections.observableArrayList();

        LocalDateTime startMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime endMonth = LocalDateTime.now().plusMonths(1);

        if (!allAppointments.isEmpty()) {
            for (appointments appt : allAppointments) {
                if (appt.getStartTime().isAfter(startMonth) && appt.getStartTime().isBefore(endMonth)) {
                    monthAppointments.add(appt);
                }
            }
            appTblView.setItems(monthAppointments);
        }
    }

    /**
     * <p><b>Third Report</b></p>
     * On press of week radioButton, select all appointments within a week (+/-)
     * @param actionEvent - on press of week radio button, populate the current appointments table with all appointments within a week of today's date.
     */
    public void weekRadio(ActionEvent actionEvent) {
        ObservableList<appointments> allAppointments = apptQuery.getAllAppointments();
        ObservableList<appointments> weekAppointments = FXCollections.observableArrayList();

        LocalDateTime startWeek = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endWeek = LocalDateTime.now().plusWeeks(1);

        if (!allAppointments.isEmpty()) {
            for (appointments appt : allAppointments) {
                if (appt.getStartTime().isAfter(startWeek) && appt.getStartTime().isBefore(endWeek)) {
                    weekAppointments.add(appt);
                }
            }
            appTblView.setItems(weekAppointments);
        }
    }

    /**
     * <p><b>Third Report</b></p>
     * selected and populates by default. Displays all appointments (if there are any appointments to display)
     * @param actionEvent - by default; or press of all appointments radio button
     */
    public void allAppts(ActionEvent actionEvent) {
        ObservableList<appointments> tempAllAppointments = apptQuery.getAllAppointments();

        if (!tempAllAppointments.isEmpty()) {
            appTblView.setItems(tempAllAppointments);
        }
    }
}

