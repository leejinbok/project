package c195.project.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * users class - declares default constructor and other members of the class.
 */
public class users {
    private int user_ID;
    private String user_name;
    private String password;
    private LocalDateTime create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;

    /**
     * default constructor for users
     * @param user_ID - user ID
     * @param user_name - user Name
     * @param password - user password
     * @param create_date - user created date
     * @param created_by - user created by
     * @param last_update - user last update time
     * @param last_updated_by - user last updated by
     */
    public users(int user_ID, String user_name, String password, LocalDateTime create_date, String created_by, Timestamp last_update, String last_updated_by) {
        this.user_ID = user_ID;
        this.user_name = user_name;
        this.password = password;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }

    /**
     * getter for user ID
     * @return - int user ID
     */
    public int getUser_ID() {
        return user_ID;
    }

    /**
     * setter for user ID
     * @param user_ID - int user ID
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    /**
     * getter for user name
     * @return - String user name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * setter for user name
     * @param user_name - String user name
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * getter for user password
     * @return - String user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for user password
     * @param password - String user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for user created date
     * @return - LocalDateTime for user created date
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * setter for user created date
     * @param create_date - LocalDateTime for user created date
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    /**
     * getter for user created by
     * @return - String user created by
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * setter for user created by
     * @param created_by - String user created by
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * getter for user last update
     * @return - Timestamp user last update
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * setter for user last update
     * @param last_update - Timestamp user last update
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * getter for user last updated by
     * @return - String last updated by
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * setter for user last updated by
     * @param last_updated_by - String last updated by
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }


}
