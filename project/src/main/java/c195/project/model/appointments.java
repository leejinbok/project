package c195.project.model;

import c195.project.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * appointments class - declares default constructor and other members of the class.
 */
public class appointments {
    private int appointmentID; //auto-generated from database
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private int customer_id;
    private int user_id;
    private int contact_id;

    /**
     * appointments object default constructor
     * @param appointmentID - appointment ID
     * @param title - appointment title
     * @param description - appointment description
     * @param location - appointment location
     * @param type - appointment type
     * @param startTime - appointment start time
     * @param endTime - appointment end time
     * @param create_date - created date for appointment
     * @param created_by - user who created the appointment
     * @param last_update - appointment last updated time
     * @param last_updated_by - user who last updated the appointment
     * @param customer_id - customer ID for appointment
     * @param user_id - user ID for appointment
     * @param contact_id - contact ID for appointment
     */
    public appointments(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_id, int user_id, int contact_id) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }

    /**
     * getter for appointment ID
     * @return - int appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * setter for appointment ID
     * @param appointmentID - parameter takes integer appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * getter for appointment title
     * @return - string appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for appointment title
     * @param title - parameter takes string appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for appointment description
     * @return - string appointment description
     */
    public String getDescription() {
        return description;
    }
    /**
     * setter for appointment description
     * @param description - parameter takes string appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * getter for appointment location
     * @return - string appointment location
     */
    public String getLocation() {
        return location;
    }
    /**
     * setter for appointment location
     * @param location - parameter takes string appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * getter for appointment type
     * @return - string appointment type
     */
    public String getType() {
        return type;
    }
    /**
     * setter for appointment type
     * @param type - parameter takes string appointment type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * getter for appointment start time
     * @return - localDateTime appointment start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }
    /**
     * setter for appointment start time
     * @param startTime - parameter takes LocalDateTime appointment start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /**
     * getter for appointment end time
     * @return - localDateTime appointment end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    /**
     * setter for appointment end time
     * @param endTime - parameter takes LocalDateTime appointment end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    /**
     * getter for appointment create time
     * @return - localDateTime appointment create date
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }
    /**
     * setter for appointment created time
     * @param create_date - parameter takes LocalDateTime appointment created time
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
    /**
     * getter for appointment creator
     * @return - string appointment creator
     */
    public String getCreated_by() {
        return created_by;
    }
    /**
     * setter for appointment creator
     * @param created_by - parameter takes string for user name
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    /**
     * getter for appointment last update
     * @return - timestamp of appointment last update
     */
    public Timestamp getLast_update() {
        return last_update;
    }
    /**
     * setter for appointment last update timestamp
     * @param last_update - parameter takes timestamp appointment last update time
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
    /**
     * getter for user who last updated appointment
     * @return - string appointment user
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }
    /**
     * setter for user who last updated appointment
     * @param last_updated_by - parameter takes string value of user who last updated appointment
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
    /**
     * getter for appointment customer ID
     * @return - integer appointment customer ID
     */
    public int getCustomer_id() {
        return customer_id;
    }
    /**
     * setter for appointment customer ID
     * @param customer_id - parameter takes integer appointment customer ID
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    /**
     * getter for appointment user ID
     * @return - integer appointment user ID
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * setter for appointment user ID
     * @param user_id - parameter takes integer appointment user ID
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /**
     * getter for appointment contact ID
     * @return - integer appointment contact ID
     */
    public int getContact_id() {
        return contact_id;
    }
    /**
     * setter for appointment contact ID
     * @param contact_id - parameter takes integer appointment contact ID
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }
}