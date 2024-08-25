package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final Connection CONNECTION = Util.getConnection();


    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS users
            ( 
            id BIGINT  AUTO_INCREMENT PRIMARY KEY, 
            name VARCHAR(45) NOT NULL, 
            lastName VARCHAR(45) NOT NULL, 
            age SMALLINT NOT NULL
            )
            """;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String INSERT = "INSERT INTO  users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM  users WHERE id = ?";
    private static final String QUERY = "SELECT * FROM users";
    private static final String DELETE_FOR_CLEAN = "DELETE FROM users ";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement(CREATE_TABLE)) {
            statement.executeUpdate();
            LOGGER.info("Table users created + "    + CREATE_TABLE);
        } catch (SQLException e) {
            LOGGER.info("Table users not created - " + e);

        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement(DROP_TABLE)) {
            statement.executeUpdate();
            LOGGER.info("Table users deleted + " + DROP_TABLE);
        } catch (SQLException e) {
            LOGGER.info("Table users not deleted - " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(INSERT)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            LOGGER.info("User saved " + name + " " + lastName + " " + age);
        } catch (SQLException e) {
            LOGGER.info("User not saved - " + e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("User remove id + " + id);
        } catch (SQLException e) {
            LOGGER.info("User not remove id - " + e);
        }
    }

    public List<User> getAllUsers() {
        try (PreparedStatement statement = CONNECTION.prepareStatement(QUERY)) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            LOGGER.info("Get all users" + users);
            return users;

        } catch (SQLException e) {
            LOGGER.info("Get all users" + e);
        }
        return null;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement(DELETE_FOR_CLEAN)) {
            statement.execute();
            LOGGER.info("Table users deleted + "    + DELETE_FOR_CLEAN);
        } catch (SQLException e) {
            LOGGER.info("Table users not deleted - " + e);
        }
    }


}



