package c195.project.controller;

import c195.project.Main;
import c195.project.helper.customersQuery;
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

public class addCustScreenController implements Initializable {
    public TextField custNameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField postalTxt;
    @FXML
    private ComboBox country;
    @FXML
    private ComboBox state;
    @FXML
    private TableView<customers> custTbl;
    @FXML
    private TableColumn<?,?> customerIdCol;
    @FXML
    private TableColumn<?,?> customerNameCol;
    @FXML
    private TableColumn<?,?> addressCol;
    @FXML
    private TableColumn<?,?> postalCol;
    @FXML
    private TableColumn<?,?> phoneCol;
    @FXML
    private TableColumn<?,?> divisionCol;
    private LocalDateTime currTime = LocalDateTime.now();
    private customers currentCustomer = new customers(1, "Bob", "1-1", "12345", ":123-4567", LocalDateTime.now(), "Bob", Timestamp.valueOf(LocalDateTime.now()) , "Bob",1);
    private users currUser = new users(1,"bob","1234", LocalDateTime.now(),"script", Timestamp.valueOf(LocalDateTime.now()),"script");
    private firstLevelDivisions currDivision = new firstLevelDivisions(1, "Alabama", LocalDateTime.now(),"admin",Timestamp.valueOf(LocalDateTime.now()),"admin",1);
    Stage stage;
    Parent scene;

    public void saveButtonOnAction(ActionEvent actionEvent) {
        try{
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
            } else if (state.getValue() == null) {
                userQuery.errorMessage("Please select a state");
            }
            setDivName(firstLevelDivisions.returnDivisionId(state.getValue().toString()));

                if (custNameTxt.getText().isEmpty() ||
                    addressTxt.getText().isEmpty() ||
                    postalTxt.getText().isEmpty() ||
                    phoneTxt.getText().isEmpty()) {
                userQuery.errorMessage("Please fill in all the boxes");
                return;
            }

            customersQuery.insert(currentCustomer.getCustomer_name(), currentCustomer.getAddress(), currentCustomer.getPostal(), currentCustomer.getPhone(), ts, currentCustomer.getCreated_by(), ut, currentCustomer.getLast_updated_by(), currentCustomer.getDivId());
            custTbl.setItems(customers.getAllCustomers());

        } catch (NullPointerException | SQLException e) {
            userQuery.errorMessage(e.getMessage());
        }
    }
    public void setDivName (ObservableList<firstLevelDivisions> divs) {
        for (firstLevelDivisions newDiv : divs) {
            currDivision.setDivision_id(newDiv.getDivision_id());
            currDivision.setDivision(newDiv.getDivision());
            currDivision.setCountry_id(newDiv.getCountry_id());
            currDivision.setCreated_by(newDiv.getCreated_by());
            currDivision.setCreate_date(newDiv.getCreate_date());
            currDivision.setLast_update(newDiv.getLast_update());
            currDivision.setLast_updated_by(newDiv.getLast_updated_by());
        }
    }

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

    public void countryCB(ActionEvent actionEvent) throws SQLException {
        String Id = country.getValue().toString();
        if (Id.equals("U.S")) {
            state.setVisibleRowCount(10);
            state.setItems(firstLevelDivisions.selectDivision(1));
        } else if (Id.equals("Canada") ) {
            state.setVisibleRowCount(4);
            state.setItems(firstLevelDivisions.selectDivision(2));
        } else if (Id.equals("UK")) {
            state.setVisibleRowCount(6);
            state.setItems(firstLevelDivisions.selectDivision(3));
        }
    }
    public void sendUser(users users) {
        currUser = users;

        System.out.println(currUser.getUser_name());
        System.out.println(currUser.getUser_ID());
    }

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

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you wish to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK) {
            customersQuery.deleteCustomers(((custTbl.getSelectionModel().getSelectedItem())).getCustomer_id());
            custTbl.setItems(customers.getAllCustomers());
        }
    }
    public void editOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modCustScreen.fxml"));
        loader.setLocation(Main.class.getResource("modCustScreen.fxml"));
        Parent root = loader.load();

        modCustScreenController modCustScreenController = loader.getController();
        customers customer = custTbl.getSelectionModel().getSelectedItem();
        int divid = customer.getDivId();
        setDivName(firstLevelDivisions.returnDivisionName(divid));
        modCustScreenController.sendCustomers(customer, currDivision, currUser);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root).getRoot();
        stage.setScene(scene.getScene());
        stage.centerOnScreen();
        stage.show();
    }
}
