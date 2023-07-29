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

public abstract class divisionsQuery {


    private static ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> divList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> returnDivList = FXCollections.observableArrayList();
    public static firstLevelDivisions selectCountry(int divisionId) throws SQLException {
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

    public static ObservableList<firstLevelDivisions> selectDivision(int countryId) throws SQLException {
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

    public static ObservableList<firstLevelDivisions> returnDivisionId(String division) throws SQLException {
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

    public static ObservableList<firstLevelDivisions> returnDivisionName(int divisionID) throws SQLException {
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
