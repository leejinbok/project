package c195.project.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.project.model.contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * abstract class of contacts queries to find information. Originally intended to CRUD for contacts as well, but was redacted.
 */
public abstract class contactsQuery {
    private static ObservableList<contacts> cList = FXCollections.observableArrayList();

    /**
     * select method to return all contacts by passing in contact ID as parameter
     * @param id - parameter takes integer contact ID
     * @return - returns a contacts object with data found in the database
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public static contacts select(int id) throws SQLException {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("Contact_ID");
                String Name = rs.getString("Contact_Name");
                String Email = rs.getString("Email");
                contacts newContact = new contacts(ID, Name, Email);
                return newContact;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * an overloaded select method that also searches for strings of contact names.
     * @param contactName - parameter takes string contact name value
     * @return - returns an observableList of contacts
     * @throws SQLException - throws exceptions for when SQL DB cannot find entries / null entries exist
     */
    public static ObservableList<contacts> select(String contactName) throws SQLException {
        ObservableList<contacts> contacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("Contact_ID");
                String Name = rs.getString("Contact_Name");
                String Email = rs.getString("Email");
                contacts newContact = new contacts(ID, Name, Email);
                contacts.add(newContact);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacts;
    }

    /**
     * a function to return all contacts
     * @return - returns an observableList of all contacts in the databse
     */
    public static ObservableList<contacts> getAllContacts() {
        cList.clear();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contacts c = new contacts(contactId,contactName,email);
                cList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cList;
    }

}
