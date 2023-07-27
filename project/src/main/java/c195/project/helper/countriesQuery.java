package c195.project.helper;

import c195.project.model.contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.project.model.countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class countriesQuery {
    public static void select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Country_ID");
            String contactName = rs.getString("Country");
            System.out.println(contactID);
            System.out.println(contactName);
        }
    }
    public static void select(int countryId) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int ID = rs.getInt("Country_ID");
            String Name = rs.getString("Country");
            System.out.println(Name);
        }
    }
}
