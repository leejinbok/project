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
    public static ObservableList<firstLevelDivisions> selectCountry(int divisionId) throws SQLException {
        ObservableList<firstLevelDivisions> dList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
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
}
