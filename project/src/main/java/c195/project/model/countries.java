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
 * countries class - declares default constructor and other members of the class.
 */
public class countries {
    private int country_ID;
    private String country;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private static ObservableList<countries> allCountries = FXCollections.observableArrayList();

    /**
     * countries object default constructor
     * @param country_ID - country ID
     * @param country - country name
     * @param create_date - created date
     * @param created_by - user created country
     * @param last_update - last update date
     * @param last_updated_by - user last updated country
     */
    public countries(int country_ID, String country, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by) {
        this.country_ID = country_ID;
        this.country = country;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }

    /**
     * getter for country ID
     * @return - int country ID
     */
    public int getCountry_ID() {
        return country_ID;
    }

    /**
     * setter for country ID
     * @param country_ID - int country ID
     */
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    /**
     * getter for country name
     * @return String country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * setter for country name
     * @param country - String country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getter for country created date
     * @return - LocalDateTime created date
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * setter for country created date
     * @param create_date - LocalDateTime created date
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    /**
     * getter for user who created country object
     * @return - String user who created country object
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * setter for user who created country object
     * @param created_by - String user who created country object
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * getter for Timestamp of when last update was
     * @return - Timestamp of last update
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * setter for last updated time
     * @param last_update - Timestamp of last update
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * getter for user of last update
     * @return - String of user last updated
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * setter for user of last update
     * @param last_updated_by - String of user last updated
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * function to return all countries in database
     * @return - ObservableList of country objects of all countries in the database
     */
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

    /**
     * function to return countries object from comparing countries and divisions database
     * @param divid - parameter takes integer division ID
     * @return - countries object that matches countries country ID with divisions country ID
     */

    public static countries getCountry(int divid) {
        try {
            String sql = "SELECT countries.Country_ID, countries.Country, countries.Create_Date, countries.Created_By, countries.Last_Update, countries.Last_Updated_By  FROM countries, first_level_divisions WHERE first_level_divisions.Division_ID = ? AND countries.Country_ID = first_level_divisions.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,divid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                countries country = new countries(countryId, countryName, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy);
                return country;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * overrides countries list output format to return country name
     * @return - String country name
     */
    @Override
    public String toString() {
        return country;
    }


}