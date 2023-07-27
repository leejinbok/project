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

public class firstLevelDivisions {
    private int division_id;
    private String division;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private int country_id;

    private static ObservableList<firstLevelDivisions> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> divList = FXCollections.observableArrayList();
    private static ObservableList<firstLevelDivisions> returnDivList = FXCollections.observableArrayList();


    public firstLevelDivisions(int division_id, String division, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by, int country_id) {
        this.division_id = division_id;
        this.division = division;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country_id = country_id;
    }

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

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

    public static ObservableList<firstLevelDivisions> returnDivision(int divisionId) throws SQLException {
        ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
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
    public static int returnCountryId(int division_id) {
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, division_id);
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
        return getAllDivisions().indexOf(division_id);
    }


    @Override
    public String toString() {
        return division;
    }
}
