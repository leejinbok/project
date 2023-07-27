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

public class apptScreenController implements Initializable {
    @FXML
    private Label totNumLbl;
    @FXML
    private RadioButton allAppts;
    @FXML
    private RadioButton monthRadio;
    @FXML
    private RadioButton weekRadio;
    @FXML
    private Label allApptLbl;
    @FXML
    private Label monthApptLbl;
    @FXML
    private Label weekApptLbl;
    @FXML
    private ComboBox<Month> monthBox;
    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private ComboBox<contacts> contactBox;
    @FXML
    private Label totalNumberLbl;
    @FXML
    private RadioButton viewWeekRadio;
    @FXML
    private RadioButton viewMonthRadio;
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

    private users currUser = new users(1,"bob","1234", LocalDateTime.now(),"script", Timestamp.valueOf(LocalDateTime.now()),"script");
    Stage stage;
    Parent scene;

    public void exitButton(ActionEvent actionEvent) throws IOException {
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


    public void addApptButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addApptScreen.fxml"));
        loader.setLocation(Main.class.getResource("addApptScreen.fxml"));
        Parent root = loader.load();
        addApptScreenController ApptScreenController = loader.getController();
        System.out.println(currUser.getUser_name());
        ApptScreenController.sendUser(currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }

    public void addCustomerBtn(ActionEvent actionEvent) throws IOException, SQLException {
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
        monthBox.setItems(apptQuery.month());
        typeBox.setItems(apptQuery.type);
        try {
            totalNumberLbl.setText(String.valueOf(apptQuery.apptCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendUser (ObservableList<users> users) {
        users.forEach(users1 -> currUser.setUser_ID(users1.getUser_ID()));
        users.forEach(users1 -> currUser.setUser_name(users1.getUser_name()));
        users.forEach(users1 -> currUser.setPassword(users1.getPassword()));
        users.forEach(users1 -> currUser.setCreate_date(users1.getCreate_date()));
        users.forEach(users1 -> currUser.setCreated_by(users1.getCreated_by()));
        users.forEach(users1 -> currUser.setLast_update(users1.getLast_update()));
        users.forEach(users1 -> currUser.setLast_updated_by(users1.getLast_updated_by()));
    }

    public void deleteButton(ActionEvent actionEvent) throws SQLException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you wish to delete this customer?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get()==ButtonType.OK) {
                apptQuery.deleteAppts(((appTblView.getSelectionModel().getSelectedItem())).getAppointmentID());
                appTblView.setItems(apptQuery.getAllAppointments());
                try {
                    totalNumberLbl.setText(String.valueOf(apptQuery.apptCount()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    public void modApptBtn(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modApptScreen.fxml"));
        loader.setLocation(Main.class.getResource("modApptScreen.fxml"));
        Parent root = loader.load();

        modApptScreenController modApptScreenController = loader.getController();
        appointments appointments = appTblView.getSelectionModel().getSelectedItem();
        modApptScreenController.sendAppointments(appointments, currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }

    public void reportButton(ActionEvent actionEvent) throws IOException, SQLException {
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
    }


    public void monthCb(ActionEvent actionEvent) {
    }

    public void typeCb(ActionEvent actionEvent) {
    }

    public void genReport(ActionEvent actionEvent) {
        try {
            totNumLbl.setText(String.valueOf(showMonthType(monthBox.getSelectionModel().getSelectedItem(), typeBox.getValue())));
        } catch (NullPointerException e) {
            userQuery.errorMessage("Please select month and appointment type to generate report!");
        }
    }

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

    public void allAppts(ActionEvent actionEvent) {
        ObservableList<appointments> tempAllAppointments = apptQuery.getAllAppointments();

        if (!tempAllAppointments.isEmpty()) {
            appTblView.setItems(tempAllAppointments);
        }
    }
}
//have a combobox to restrict appointment scheduling
//error messages - scheduling appointment outside business hours
//error messages - scheduling overlapping appointments
//error messages - entering incorrect id / pw
//popup message - if any appointment within 15 minutes
//showing report - total number of customer appointments by type and month
/*schedule for each contact in your organization that includes:
appt ID
title
type
description
start date/time
end date/time
customer id
 */
//additional report of your choice
