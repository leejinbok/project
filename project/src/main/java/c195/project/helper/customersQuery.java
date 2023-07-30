package c195.project.helper;

import c195.project.model.contacts;
import c195.project.model.customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * abstract class of customer queries to return information
 */
public abstract class customersQuery {
    /**
     * returns all customers from table with matching customer ID
     * @param customerId - parameter takes integer customer ID value
     * @return - returns customers object
     */
    public static customers select(int customerId) {
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
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
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * method to delete customer with primary key customer ID
     * @param customerId - parameter takes integer customer ID value
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public static void deleteCustomers(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        ps.executeUpdate();
    }

    /**
     * method to update customer information
     * @param id - customer ID
     * @param name - customer Name
     * @param address - customer address
     * @param postal - customer postal
     * @param phone - customer phone number
     * @param divId - customer's division ID number
     * @param ldt - customer's information last updated time
     * @param username - username making changes to customer information
     */
    public static void updateCustomers(int id, String name, String address, String postal, String phone, int divId, Timestamp ldt, String username) {
        String sql = "Update customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4,phone);
            ps.setTimestamp(5, ldt);
            ps.setString(6, username);
            ps.setInt(7, divId);
            ps.setInt(8, id);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } userQuery.updatePopup();
    }

    /**
     * method to insert/create new row of customer information in the database
     * @param customer_name - customer Name
     * @param address - customer address
     * @param postal - customer postal
     * @param phone - customer phone number
     * @param create_date - customer information created date
     * @param created_by - user who created customer information
     * @param last_update - time customer information was last updated
     * @param last_updated_by - user who last updated customer information
     * @param divId - customer's recorded division ID
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public static void insert(String customer_name, String address, String postal, String phone, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int divId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_by, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customer_name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phone);
        ps.setTimestamp(5,create_date);
        ps.setString(6,created_by);
        ps.setTimestamp(7,last_update);
        ps.setString(8,last_updated_by);
        ps.setInt(9,divId);
    }
}
