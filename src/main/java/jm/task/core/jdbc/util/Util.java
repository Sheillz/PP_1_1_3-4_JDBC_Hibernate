package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

    private Util() {
    }

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

    public static SessionFactory buildSessionFactory() {
        return new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}