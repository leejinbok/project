package c195.project.helper;

import c195.project.model.users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public abstract class userQuery {

    private static ObservableList<users> allUsers = FXCollections.observableArrayList();
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

    public static void errorMessage(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }


    public static void infoMessage(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
    public static void updatePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Your changes have been saved");
        alert.showAndWait();
    }
}