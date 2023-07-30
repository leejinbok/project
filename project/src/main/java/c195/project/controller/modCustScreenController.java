package c195.project.controller;

import c195.project.Main;
import c195.project.helper.customersQuery;
import c195.project.helper.divisionsQuery;
import c195.project.helper.userQuery;
import c195.project.model.countries;
import c195.project.model.customers;
import c195.project.model.firstLevelDivisions;
import c195.project.model.users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class modCustScreenController implements Initializable {
    @FXML
    private TextField idTxt1;
    @FXML
    private TextField custNameTxt1;
    @FXML
    private TextField addressTxt1;
    @FXML
    private TextField postalTxt1;
    @FXML
    private TextField phoneTxt1;
    @FXML
    private ComboBox<countries> country;
    @FXML
    private ComboBox<firstLevelDivisions> state;
    private customers currentCustomer;
    private users currUser;
    private firstLevelDivisions currDivision;
    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException {

        if (country.getValue() == null) {
            userQuery.errorMessage("Please select a country");
            return;
        } else if (state.getValue() == null) {
            userQuery.errorMessage("Please select a state");
            return;
        }
        setDivName(divisionsQuery.returnDivisionId(state.getValue().toString()));
        int Id = currDivision.getDivision_id();

        currentCustomer.setCustomer_id(Integer.parseInt(idTxt1.getText()));
        currentCustomer.setCustomer_name(custNameTxt1.getText());
        currentCustomer.setAddress(addressTxt1.getText());
        currentCustomer.setPostal(postalTxt1.getText());
        currentCustomer.setPhone(phoneTxt1.getText());
        currentCustomer.setLast_updated_by(currUser.getUser_name());
        currentCustomer.setDivId(Id);
        currentCustomer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));

        customersQuery.updateCustomers(currentCustomer.getCustomer_id(), currentCustomer.getCustomer_name(), currentCustomer.getAddress(), currentCustomer.getPostal(), currentCustomer.getPhone(), currentCustomer.getDivId(),currentCustomer.getLast_update(), currentCustomer.getLast_updated_by());

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene;
        try {
            scene = FXMLLoader.load(Main.class.getResource("addCustScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    public void sendCustomers(customers customer, firstLevelDivisions divisions, users user) throws SQLException {
        currentCustomer = customer;
        int divid = currentCustomer.getDivId();
        currUser = user;
        currUser.setUser_name(user.getUser_name());
        currDivision = divisions;
        idTxt1.setText(String.valueOf(currentCustomer.getCustomer_id()));
        custNameTxt1.setText(currentCustomer.getCustomer_name());
        addressTxt1.setText(currentCustomer.getAddress());
        postalTxt1.setText(currentCustomer.getPostal());
        phoneTxt1.setText(currentCustomer.getPhone());

        countries selectedCountry = countries.getCountry(divid);
        for (countries c : country.getItems()) {
            if (c.getCountry_ID()==selectedCountry.getCountry_ID()) {
                country.setValue(c);
            }
        }
        String countryName = country.getValue().toString();
        countryName = countryName.replaceAll("[\\[\\](){}]","");

        state.setItems(divisionsQuery.countryName(countryName));
        firstLevelDivisions selectedState = divisionsQuery.selectCountry(divid);
        for (firstLevelDivisions f : state.getItems()) {
            if (f.getDivision_id()== selectedState.getDivision_id()){
                state.setValue(f);
            }
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
/*
        for (firstLevelDivisions newDiv : divs) {
            currDivision.setDivision_id(newDiv.getDivision_id());
            currDivision.setDivision(newDiv.getDivision());
            currDivision.setCountry_id(newDiv.getCountry_id());
            currDivision.setCreate_date(newDiv.getCreate_date());
            currDivision.setCreated_by(newDiv.getCreated_by());
            currDivision.setLast_update(newDiv.getLast_update());
            currDivision.setLast_updated_by(newDiv.getLast_updated_by());

 */
    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene;
        try {
            scene = FXMLLoader.load(Main.class.getResource("addCustScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    public void countryCB(ActionEvent actionEvent) throws SQLException, NullPointerException {
        if (country.getValue()!= null) {
            int Id = country.getValue().getCountry_ID();
            state.setItems(divisionsQuery.selectDivision(Id));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        country.setItems(countries.getAllCountries());
    }
}
