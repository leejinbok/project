package c195.project.helper;

import c195.project.model.users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * abstract class user queries that provides different useful functions for user-related information
 */
public abstract class userQuery {
    /**
     * a method to select all rows that match with username
     * @param userName - parameter takes string value to match
     * @return - returns a users object
     */
    public static users select(String userName) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("User_ID");
                String user_name = rs.getString("User_Name");
                String userPw = rs.getString("Password");
                Timestamp ts = rs.getTimestamp("Create_Date");
                LocalDateTime create_date = ts.toLocalDateTime();
                String created_by = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String last_updated_by = rs.getString("Last_Updated_By");
                users currUser = new users(userID, user_name, userPw, create_date, created_by, last_update, last_updated_by);
                return currUser;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * a method to get matching passwords to the username provided in the parameter
     * @param userName - parameter takes string value username
     * @return - returns observableList of strings that contain matching password with username
     */
    public static ObservableList<String> getPassword(String userName) {
        ObservableList<String> pw = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Password FROM users WHERE User_Name = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userPw = rs.getString("Password");
                pw.add(userPw);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pw;
    }

    /**
     * generates a popup message with alert type error. Allows user to pass in string parameter to customize message
     * @param errorMsg - parameter of string type and custom context message
     */
    public static void errorMessage(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
    /**
     * generates a popup message with alert type information. Allows user to pass in string parameter to customize message
     * @param infoMsg - parameter of string type and custom context message
     */
    public static void infoMessage(String infoMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMsg);
        alert.showAndWait();
    }

    /**
     * generates a popup message with alert type information. Serves the purpose of confirmation.
     */
    public static void updatePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Your changes have been saved");
        alert.showAndWait();
    }
}