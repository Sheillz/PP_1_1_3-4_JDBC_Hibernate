package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import static jm.task.core.jdbc.util.Util.getConnection;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            userService.removeUserById(1);
            currentSession.getTransaction().commit();
        }
    }
}
