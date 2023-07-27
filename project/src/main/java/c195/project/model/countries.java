package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class countries {
    private int country_ID;
    private String country;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private static ObservableList<countries> allCountries = FXCollections.observableArrayList();

    public countries(int country_ID, String country, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by) {
        this.country_ID = country_ID;
        this.country = country;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }
    public int getCountry_ID() {
        return country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
    public static ObservableList<countries> getAllCountries() {
        allCountries.clear();
        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                countries c = new countries(countryId, country, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy);
                allCountries.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCountries;
    }
    public static ObservableList<countries> getCountryNames(String country_name) {
        ObservableList<countries> cList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM countries WHERE Country = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, country_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                countries c = new countries(countryId, country, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy);
                cList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cList;
    }

    public static ObservableList<countries> countryName(int divid) {
        ObservableList<countries> countryList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country_ID, countries.Country, countries.Create_Date, countries.Created_By, countries.Last_Update, countries.Last_Updated_By  FROM countries, first_level_divisions WHERE first_level_divisions.Division_ID = ? AND countries.Country_ID = first_level_divisions.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,divid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                countries country = new countries(countryId, countryName, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy);
                countryList.add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countryList;
    }
    @Override
    public String toString() {
        return country;
    }


}