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

public abstract class customersQuery {
    private static ObservableList<customers> customerNames = FXCollections.observableArrayList();
    private static ObservableList<customers> customerIdList = FXCollections.observableArrayList();

    public static ObservableList<customers> returnCustomerName(int customerId) {
        customerNames.clear();
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,customerId);
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
                customerNames.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerNames;
    }
    public static ObservableList<customers> returnCustomerId(String customerName) {
        customerIdList.clear();
        try {
            String sql = "SELECT * FROM customers WHERE Customer_Name = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,customerName);
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
                customerIdList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerIdList;
    }
    public static void deleteCustomers(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        ps.executeUpdate();
    }

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

    public static int insert(String customer_name, String address, String postal, String phone, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int divId) throws SQLException {
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
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
