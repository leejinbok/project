package c195.project.helper;

import c195.project.model.contacts;
import c195.project.model.firstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * abstract clas of divisions queries that returns data from the database
 */
public abstract class divisionsQuery {
    private static ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> divList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> returnDivList = FXCollections.observableArrayList();

    /**
     * returns a division object from taking integer division id as a parameter
     * @param divisionId - parameter takes integer value of division id
     * @return - returns a matching firstleveldivisions object for division ID value
     */
    public static firstLevelDivisions selectDivisions(int divisionId) {
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, division, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                return d;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * returns a divisions observableList from taking integer country id as a parameter
     * @param countryId - parameter takes integer value of country id
     * @return - returns an observableList of firstleveldivisions objects for matching country ID value
     */
    public static ObservableList<firstLevelDivisions> selectDivision(int countryId) {
        dList.clear();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, division, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                dList.add(d);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return dList;
    }

    /**
     * a method to return an observable list of divisions by searching for matching division name
     * @param division parameter takes string value of division name
     * @return returns an observable list of divisions
     */
    public static ObservableList<firstLevelDivisions> returnDivisionId(String division) {
        ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, div, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                dList.add(d);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return dList;
    }

    /**
     * returns a division observable List from taking integer division id as a parameter
     * @param divisionID - parameter takes integer value of division id
     * @return - returns a matching observable list for division ID value
     */
    public static ObservableList<firstLevelDivisions> returnDivisionName(int divisionID){
        returnDivList.clear();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, div, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                returnDivList.add(d);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return returnDivList;
    }

    /**
     * method to return an observableList of divisions with matching country name. Compares values in divisions and countries databases.
     * @param country_name - parameter takes string country value
     * @return - observableList of divisions that match country name
     */
    public static ObservableList<firstLevelDivisions> countryName(String country_name) {
        divList.clear();
        try {
            String sql = "SELECT first_level_divisions.Division_ID, first_level_divisions.Division, first_level_divisions.Create_Date, first_level_divisions.Created_By, first_level_divisions.Last_Update, first_level_divisions.Last_Updated_By, first_level_divisions.Country_ID FROM countries, first_level_divisions WHERE countries.Country = ? AND countries.Country_ID = first_level_divisions.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,country_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime ldtcreateDate = createDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions d = new firstLevelDivisions(divId, div, ldtcreateDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                divList.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return divList;
    }

}
