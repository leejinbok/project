package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * contacts class - declares default constructor and other members of the class.
 */
public class contacts {
    private int contact_ID;
    private String contact_Name;
    private String email;
    private static ObservableList<contacts> cList = FXCollections.observableArrayList();

    /**
     * default constructor for contacts class
     * @param contact_ID - contact ID
     * @param contact_Name - contact Name
     * @param email - contact Email
     */
    public contacts(int contact_ID, String contact_Name, String email) {
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
    }

    /**
     * getter for contact ID
     * @return int contact ID
     */
    public int getContact_ID() {
        return contact_ID;
    }

    /**
     * setter for contact ID
     * @param contact_ID - parameter takes int contact ID
     */
    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }
    /**
     * getter for contact name
     * @return String contact name
     */
    public String getContact_Name() {
        return contact_Name;
    }
    /**
     * setter for contact name
     * @param contact_Name - parameter takes String contact name
     */
    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }
    /**
     * getter for contact email
     * @return String contact email
     */
    public String getEmail() {
        return email;
    }
    /**
     * setter for contact email
     * @param email - parameter takes String contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * method to return observableList of all contacts
     * @return - observable list of all contacts in the database
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

    /**
     * Override method for string to return contact name in the list of observable list in getAllContacts
     * @return - String contact name
     */
    @Override
    public String toString() {
        return contact_Name;
    }
}