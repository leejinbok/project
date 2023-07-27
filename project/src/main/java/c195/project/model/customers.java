package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class customers {
    private int customer_id;
    private String customer_name;
    private String address;
    private String postal;
    private String phone;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private int divId;

    private static ObservableList<customers> allCustomers = FXCollections.observableArrayList();

    public customers(int customer_id, String customer_name, String address, String postal, String phone, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by, int divId) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.divId = divId;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public static ObservableList<customers> getAllCustomers() {
        allCustomers.clear();
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int contactId = rs.getInt("Customer_ID");
                String contactName = rs.getString("Customer_Name");
                String email = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime localCreate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divId = rs.getInt("Division_ID");

                customers c = new customers(contactId,contactName,email,postal,phone,localCreate,createdBy,lastUpdate,lastUpdatedBy,divId);
                allCustomers.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCustomers;
    }

    public String toString() {
        return customer_name;
    }

}
