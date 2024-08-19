package jm.task.core.jdbc.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private Util() {}

    public static Connection getConnection() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/jdbci.properties")) {
            properties.load(fileInputStream);
            String URL = properties.getProperty("URL");
            String NAME = properties.getProperty("NAME");
            String PASSWORD = properties.getProperty("PASSWORD");
            return DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (IOException | SQLException e) {
            LOGGER.info("Connection not created - " + e);
            throw new RuntimeException(e);
        }

    }
}