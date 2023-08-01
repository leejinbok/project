package c195.project.controller;

import c195.project.Main;
import c195.project.helper.apptQuery;
import c195.project.helper.customersQuery;
import c195.project.helper.divisionsQuery;
import c195.project.helper.userQuery;
import c195.project.model.*;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * addCustScreenController - add customers to the program
 */
public class addCustScreenController implements Initializable {
    @FXML
    private TextField custNameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField postalTxt;
    @FXML
    private ComboBox<countries> country;
    @FXML
    private ComboBox<firstLevelDivisions> state;
    @FXML
    private TableView<customers> custTbl;
    @FXML
    private TableColumn<customers, Integer> customerIdCol;
    @FXML
    private TableColumn<customers, String> customerNameCol;
    @FXML
    private TableColumn<customers, String> addressCol;
    @FXML
    private TableColumn<customers, String> postalCol;
    @FXML
    private TableColumn<customers, String> phoneCol;
    @FXML
    private TableColumn<customers, String> divisionCol;
    private LocalDateTime currTime = LocalDateTime.now();
    private customers currentCustomer = new customers(1, "Bob", "1-1", "12345", ":123-4567", LocalDateTime.now(), "Bob", Timestamp.valueOf(LocalDateTime.now()) , "Bob",1);
    private users currUser = new users(1,"bob","1234", LocalDateTime.now(),"script", Timestamp.valueOf(LocalDateTime.now()),"script");
    private firstLevelDivisions currDivision = new firstLevelDivisions(1, "Alabama", LocalDateTime.now(),"admin",Timestamp.valueOf(LocalDateTime.now()),"admin",1);
    Stage stage;
    Parent scene;

    /**
     * actions to take once save button is pressed
     * @param actionEvent - on press of Save button, take action
     * @throws SQLException - throws SQL and RunTimeExceptions. Catches and displays NullPointExceptions
     */
    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            if (custNameTxt.getText().isEmpty() ||
                    addressTxt.getText().isEmpty() ||
                    postalTxt.getText().isEmpty() ||
                    phoneTxt.getText().isEmpty()) {
                userQuery.errorMessage("Please fill in all the boxes");
                return;
            }

            currentCustomer.setCustomer_name(custNameTxt.getText());
            currentCustomer.setAddress(addressTxt.getText());
            currentCustomer.setPostal(postalTxt.getText());
            currentCustomer.setPhone(phoneTxt.getText());
            LocalDateTime updateTime = LocalDateTime.now();
            Timestamp ts = Timestamp.valueOf(currTime);
            Timestamp ut = Timestamp.valueOf(updateTime);

            currentCustomer.setDivId(currDivision.getDivision_id());
            currentCustomer.setCreated_by(currUser.getUser_name());
            currentCustomer.setLast_updated_by(currUser.getUser_name());
            if (country.getValue() == null) {
                userQuery.errorMessage("Please select a country");
                return;
            } else if (state.getValue() == null) {
                userQuery.errorMessage("Please select a state");
                return;
            }
            setDivName(divisionsQuery.returnDivisionId(state.getValue().toString()));


            customersQuery.insert(currentCustomer.getCustomer_name(), currentCustomer.getAddress(), currentCustomer.getPostal(), currentCustomer.getPhone(), ts, currentCustomer.getCreated_by(), ut, currentCustomer.getLast_updated_by(), currentCustomer.getDivId());
            custTbl.setItems(customers.getAllCustomers());
            userQuery.infoMessage("New Customer saved");

        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p><b>LAMBDA EXPRESSION</b></p>
     * a function to manual set values of current division from observable list of divisions from SQL lookup.
     * @param divs - takes in parameter divs from SQL lookup of division names
     */
    public void setDivName (ObservableList<firstLevelDivisions> divs) {
        divs.forEach(newDiv -> currDivision.setDivision_id(newDiv.getDivision_id()));
        divs.forEach(newDiv -> currDivision.setDivision(newDiv.getDivision()));
        divs.forEach(newDiv -> currDivision.setCountry_id(newDiv.getCountry_id()));
        divs.forEach(newDiv -> currDivision.setCreate_date(newDiv.getCreate_date()));
        divs.forEach(newDiv -> currDivision.setCreated_by(newDiv.getCreated_by()));
        divs.forEach(newDiv -> currDivision.setLast_update(newDiv.getLast_update()));
        divs.forEach(newDiv -> currDivision.setLast_updated_by(newDiv.getLast_updated_by()));
    }

    /**
     * Actions taken when cancel button is pressed.
     * @param actionEvent - on press of cancel button
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
     * Actions taken when country ComboBox is pressed. It populates the appropriate division values.
     * @param actionEvent - on press of country ComboBox
     * @throws SQLException - throws SQLException if queries to SQL DB result in errors.
     */
    public void countryCB(ActionEvent actionEvent) throws SQLException {
        if (country.getValue()!= null) {
            int Id = country.getValue().getCountry_ID();
            state.setItems(divisionsQuery.selectDivision(Id));
        }
    }

    /**
     * receives current user object from main appointment screen to carry user values over.
     * @param users - parameter of users is passed through previous screen apptScreen
     */
    public void sendUser(users users) {
        currUser = users;
    }

    /**
     * Objects that are initialized when screen is first run.
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
        custTbl.setItems(customers.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divId"));

        country.setItems(countries.getAllCountries());
        country.setPromptText("Please choose a country");
        state.setPromptText("Please choose a state");
        state.setItems(firstLevelDivisions.getAllDivisions());
        state.setVisibleRowCount(10);
    }

    /**
     * actions taken when delete button is pressed. Generates a confirmation to confirm delete
     * @param actionEvent - on delete button press
     * @throws SQLException - throws SQLExceptions for possible load errors in SQL DB query
     */
    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        customers currCustomer = custTbl.getSelectionModel().getSelectedItem();
        if (currCustomer == null) {
            userQuery.errorMessage("Please select a customer to delete");
            return;
        }
        int customerID = currCustomer.getCustomer_id();
        String customerName = currCustomer.getCustomer_name();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you wish to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK) {
            if (apptQuery.getCustomerAppointments(currCustomer.getCustomer_id()).size() > 0) {
                userQuery.errorMessage("Cannot delete customer with an appointment!");
                return;
            }
            customersQuery.deleteCustomers(currCustomer.getCustomer_id());
            userQuery.infoMessage("Customer ID |  " + customerID + "  | with Name |  " + customerName + "  | has been successfully deleted");
            custTbl.setItems(customers.getAllCustomers());
        }
    }

    /**
     * <p>actions taken when modify button is pressed.</p>
     * Passes customer, division, and user values to next screen.
     * @param actionEvent - on press of modify button on screen.
     * @throws IOException - throws exceptions for when FXML loader cannot obtain resources correctly
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public void editOnAction(ActionEvent actionEvent) throws IOException, SQLException, NullPointerException {
        customers customer = custTbl.getSelectionModel().getSelectedItem();
        if (customer == null) {
            userQuery.errorMessage("Please select a customer to delete");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("modCustScreen.fxml"));
        loader.setLocation(Main.class.getResource("modCustScreen.fxml"));
        Parent root = loader.load();

        modCustScreenController modCustScreenController = loader.getController();
        int divid = customer.getDivId();
        setDivName(divisionsQuery.returnDivisionName(divid));
        modCustScreenController.sendCustomers(customer, currDivision, currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }
}
