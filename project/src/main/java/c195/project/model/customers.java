package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * customers class - declares default constructor and other members of the class.
 */
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

    /**
     * default constructor for customers object
     * @param customer_id - customer ID
     * @param customer_name - customer name
     * @param address - customer address
     * @param postal - customer postal zip code
     * @param phone - customer phone number
     * @param create_date - customer object create date
     * @param created_by - customer object created by
     * @param last_update - customer last update date
     * @param last_updated_by - user who last updated customers
     * @param divId - customer division ID
     */
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

    /**
     * getter for customer ID
     * @return - int customer ID
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * setter for customer ID
     * @param customer_id - int customer ID
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    /**
     * getter for customer name
     * @return - String customer name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * setter for customer name
     * @param customer_name - String customer name
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    /**
     * getter for customer address
     * @return - String customer address
     */
    public String getAddress() {
        return address;
    }
    /**
     * setter for customer address
     * @param address - String customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * getter for customer postal code
     * @return - String customer postal code
     */
    public String getPostal() {
        return postal;
    }

    /**
     * setter for customer postal address
     * @param postal - String postal address
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    /**
     * getter for customer phone number
     * @return - String customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter for customer phone number
     * @param phone - String phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * getter for customer created date
     * @return - LocalDateTime customer created date
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * setter for customer create date
     * @param create_date - LocalDateTime customer created date
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
    /**
     * getter for user created customer
     * @return - String user created customer
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * setter for user who created customer
     * @param created_by - String user who created customer
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    /**
     * getter for customer last updated time
     * @return - Timestamp for customer last update
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * setter for customer last updated time
     * @param last_update - Timestamp for customer last update
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
    /**
     * getter for customer last updated user
     * @return - String customer last updated user
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * setter for user last updated customer
     * @param last_updated_by - String username who last updated customer
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
    /**
     * getter for customer division ID
     * @return - int customer division ID
     */
    public int getDivId() {
        return divId;
    }

    /**
     * setter for customer division ID
     * @param divId - int customer division ID
     */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /**
     * function to return ObservableList of all customers in the database
     * @return - ObservableList of all customers in the database
     */
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

    /**
     * method to format output of customer ObservableList to return String values of customer names
     * @return - String values of customer names
     */
    public String toString() {
        return customer_name;
    }

}
