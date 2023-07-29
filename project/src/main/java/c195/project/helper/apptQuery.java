package c195.project.helper;

import c195.project.model.appointments;
import c195.project.model.contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public abstract class apptQuery {
    private static ObservableList<appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<appointments> apptCheck = FXCollections.observableArrayList();
    private static ObservableList<appointments> contactAppointments = FXCollections.observableArrayList();
    private static ObservableList<appointments> weeklyAppointments = FXCollections.observableArrayList();

    public static ObservableList<LocalDateTime> bookedStartTimes(int customer) {
        ObservableList<LocalDateTime> bookedAppts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Start FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,customer);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp st = rs.getTimestamp("Start");
                LocalDateTime start = st.toLocalDateTime();
                bookedAppts.add(start);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookedAppts;
    }
    public static ObservableList<LocalDateTime> bookedEndTimes(int customer) {
        ObservableList<LocalDateTime> bookedAppts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT End FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,customer);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp st = rs.getTimestamp("End");
                LocalDateTime start = st.toLocalDateTime();
                bookedAppts.add(start);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookedAppts;
    }

    //month ofstart ofnow
    //yearweek of start of now
    //
    public static int apptCount() throws SQLException {
        try {
            String sql = "SELECT COUNT(*) as total FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
                return rs.getInt("total");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public static ObservableList<String> appType() throws SQLException {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT Type FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                typeList.add(type);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeList;
    }
    public static ObservableList<appointments> getContactAppointments(contacts contact) throws SQLException {
        contactAppointments.clear();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,contact.getContact_ID());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp st = rs.getTimestamp("Start");
            LocalDateTime start = st.toLocalDateTime();
            Timestamp e = rs.getTimestamp("End");
            LocalDateTime end = e.toLocalDateTime();
            Timestamp c= rs.getTimestamp("Create_Date");
            LocalDateTime create_date = c.toLocalDateTime();
            String createdBy =  rs.getString("Created_By");
            Timestamp last = rs.getTimestamp("Last_Update");
            LocalDateTime last_update = last.toLocalDateTime();
            String last_updated_by = rs.getString("Last_Updated_By");
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            appointments a = new appointments(apptId,title,description,location,type,start,end,create_date,createdBy,last,last_updated_by,custId,userId,contactId);
            contactAppointments.add(a);
        }
        return contactAppointments;
    }
    public static ObservableList<appointments> getAllAppointments() {
        allAppointments.clear();
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp st = rs.getTimestamp("Start");
                LocalDateTime start = st.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                Timestamp c= rs.getTimestamp("Create_Date");
                LocalDateTime create_date = c.toLocalDateTime();
                String createdBy =  rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("Last_Update");
                LocalDateTime last_update = last.toLocalDateTime();
                String last_updated_by = rs.getString("Last_Updated_By");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                appointments a = new appointments(apptId,title,description,location,type,start,end,create_date,createdBy,last,last_updated_by,custId,userId,contactId);
                allAppointments.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allAppointments;
    }
    public static void deleteAppts(int apptId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,apptId);
        ps.executeUpdate();
    }
    public static void update(String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_id, int user_id, int contact_id, int ID) throws SQLException {
        try {
            String sql = "Update appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setTimestamp(7, create_date);
            ps.setString(8, created_by);
            ps.setTimestamp(9, last_update);
            ps.setString(10, last_updated_by);
            ps.setInt(11, customer_id);
            ps.setInt(12, user_id);
            ps.setInt(13, contact_id);
            ps.setInt(14, ID);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int insert(String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_id, int user_id, int contact_id) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5,startTime);
        ps.setTimestamp(6,endTime);
        ps.setTimestamp(7,create_date);
        ps.setString(8,created_by);
        ps.setTimestamp(9,last_update);
        ps.setString(10,last_updated_by);
        ps.setInt(11,customer_id);
        ps.setInt(12,user_id);
        ps.setInt(13,contact_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Month> month () {
        ObservableList<Month> mo = FXCollections.observableArrayList();
        for (int i = 1; i <=12; i++) {
            mo.add(Month.of(i));
        }
        return mo;
    }

    public static ObservableList<LocalTime> getAppointmentStart(int startHour) {
        ObservableList<LocalTime> apptStart = FXCollections.observableArrayList();
        for (int i = startHour; i < 24; i++) {
            for (int j = 0; j < 60; j+=15) {
                apptStart.add(LocalTime.of(i,j));
            }
        }
        return apptStart;
    }
    public static ObservableList<LocalTime> getAppointmentEnd(int startHour) {
        ObservableList<LocalTime> apptEnd = FXCollections.observableArrayList();
        for (int i = startHour; i < 24; i++) {
            for (int j = 0; j < 60; j+=15) {
                apptEnd.add(LocalTime.of(i,j));
            }
        }
        return apptEnd;
    }
}
