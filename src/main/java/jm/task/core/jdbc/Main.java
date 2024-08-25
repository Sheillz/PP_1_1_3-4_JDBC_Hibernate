package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import static jm.task.core.jdbc.util.Util.getConnection;

public class  Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Petr", "Petrov", (byte) 35);
        userService.saveUser("Sidor", "Sidorov", (byte) 45);
        userService.saveUser("Vasiliy", "Vasilev", (byte) 55);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        }
    }

