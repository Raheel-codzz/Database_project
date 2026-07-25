package util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoggerUtil {

    public static void logError(String msg) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO error_logs(message) VALUES (?)"
            );
            pst.setString(1, msg);
            pst.executeUpdate();
        } catch (Exception ignored) {}
    }
}
