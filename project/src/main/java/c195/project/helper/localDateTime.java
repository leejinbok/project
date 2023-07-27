package c195.project.helper;

import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.*;

public class localDateTime {
    public static LocalDateTime timeNow() {
        Instant instant = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return ldt;
    }

    public static void updatePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Your changes have been saved");
        alert.showAndWait();
    }

    public static void zoneDate() {
        //date
        //time
        //offset
        //time zone
    }

    public static void zoneIds() {
        ZoneId.getAvailableZoneIds().stream().filter(z->z.contains("America")).sorted().forEach(System.out::println);
    }
}
