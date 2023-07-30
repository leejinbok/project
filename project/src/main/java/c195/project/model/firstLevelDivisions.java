package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * firstLevelDivisions class - declares default constructor and other members of the class.
 */
public class firstLevelDivisions {
    private int division_id;
    private String division;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private int country_id;
    private static ObservableList<firstLevelDivisions> allDivisions = FXCollections.observableArrayList();

    /**
     * default constructor for firstLevelDivisions
     * @param division_id - division ID
     * @param division - division Name
     * @param create_date - created date
     * @param created_by - user created by
     * @param last_update - last update date
     * @param last_updated_by - last updated by
     * @param country_id - country ID
     */
    public firstLevelDivisions(int division_id, String division, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by, int country_id) {
        this.division_id = division_id;
        this.division = division;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country_id = country_id;
    }

    /**
     * getter for Division ID
     * @return int division ID
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * setter for Division ID
     * @param division_id - int division ID
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }
    /**
     * getter for Division name
     * @return String division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * setter for division name
     * @param division - String division name
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * getter for Division create date
     * @return LocalDateTime division create date
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * setter for create date
     * @param create_date - LocalDateTime for created date
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
    /**
     * getter for Division created user
     * @return String division created user
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * setter for created by user
     * @param created_by - String created by
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    /**
     * getter for Division last update timestamp
     * @return Timestamp division last update
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * setter for last updated time
     * @param last_update - Timestamp last updated time
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
    /**
     * getter for Division last updated by
     * @return String division last updated by
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * setter for last updated by
     * @param last_updated_by - String user last updated by
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
    /**
     * getter for country ID
     * @return int country ID
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * setter for country ID
     * @param country_id - int country ID
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    /**
     * method to return all divisions in database
     * @return - returns an ObservableList of firstLevelDivisions of all divisions in the database
     */
    public static ObservableList<firstLevelDivisions> getAllDivisions() {
        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, division, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                allDivisions.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allDivisions;
    }

    /**
     * Override method to return all division values as string
     * @return - String division name
     */
    @Override
    public String toString() {
        return division;
    }
}
