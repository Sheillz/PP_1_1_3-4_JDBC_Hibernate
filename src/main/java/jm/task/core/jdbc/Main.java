package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Ivan", "Ivanov", (byte) 25);
        user.saveUser("Petr", "Petrov", (byte) 35);
        user.saveUser("Alexey", "Alexeev", (byte) 45);
        user.saveUser("Sergey", "Sergeev", (byte) 55);
        user.getAllUsers();
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
