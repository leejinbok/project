package c195.project.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.project.model.contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class contactsQuery {
    private static ObservableList<contacts> contactIDs = FXCollections.observableArrayList();

    public static void createContact(int id, String contactName, String email) {
        try {
            String sqlc = "INSERT INTO contacts VALUES(NULL, ?, ?)";
            PreparedStatement psc = JDBC.connection.prepareStatement(sqlc, Statement.RETURN_GENERATED_KEYS);
            psc.setString(1,contactName);
            psc.setString(2,email);
            psc.execute();
            ResultSet rs = psc.getGeneratedKeys();
            rs.next();
            int contactID = rs.getInt(1);
            String sqlct = "INSERT INTO appointments VALUES(NULL, ?)";
            PreparedStatement psa = JDBC.connection.prepareStatement(sqlct);
            psa.setInt(1,contactID);
            psa.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int insert(String name, String email) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,email);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int contactID, String name, String eMail) throws SQLException {
        String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,eMail);
        ps.setInt(3,contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    //need an update email method
    public static int updateEmail(int contactID, String email) throws SQLException {
        String sql = "UPDATE contacts SET Email = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,email);
        ps.setInt(2,contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void deleteContact(int contactID) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,contactID);
        ps.executeUpdate();
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
        }
    }
    public static ObservableList<contacts> select(int id) throws SQLException {
        ObservableList<contacts> contacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);
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
    public static ObservableList<contacts> returnContactID(String contactName) throws SQLException {
       contactIDs.clear();
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
                contactIDs.add(newContact);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contactIDs;
    }

    public static ObservableList<contacts> getApptContacts() {
        ObservableList<contacts> cList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT contacts.Contact_ID, Contact_Name, Email FROM contacts, appointments WHERE contacts.Contact_ID = appointments.Contact_ID";
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
    }    public static ObservableList<contacts> getAllContacts() {
        ObservableList<contacts> cList = FXCollections.observableArrayList();
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
