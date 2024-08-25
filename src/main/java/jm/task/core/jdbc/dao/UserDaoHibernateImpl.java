package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createNativeQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL," +
                    "age SMALLINT NOT NULL)").executeUpdate();
            System.out.println("Таблица создана" + "users");
            currentSession.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Таблица уже существует" + "users");
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            System.out.println("Таблица удалена" +  " " + "users");
            currentSession.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Таблица не существует" + " " + "users"); ;
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (name == null || lastName == null || age <= 0) {
            throw new IllegalArgumentException("Некорректные данные" + " " + name + " " + lastName + " " + age);
        }
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.save(new User(name, lastName, age));
            System.out.println("Пользователь добавлен" + " " + name + " " + lastName + " " + age);
            currentSession.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Пользователь уже существует" + " " + name + " " + lastName + " " + age);
            throw e;
        }
    }


    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            try {
                if (id <= 0) {
                    System.out.println("Id должен быть больше нуля" + id);
                }
                User user = currentSession.get(User.class, id);
                Optional<User> mayBeUser = Optional.ofNullable(user);
                mayBeUser.ifPresentOrElse(el -> {
                            currentSession.delete(el);
                            System.out.printf("Пользователь с id: %d удален", id);
                        },
                        () -> {
                            System.out.printf("Пользователь с таким id: %d не существует", id);
                            currentSession.getTransaction().rollback();
                        });
                currentSession.getTransaction().commit();
            } catch (Exception e) {
                currentSession.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            List<User> list = currentSession.createQuery("select u from User u", User.class).list();
            System.out.println("Все пользователи: " + "  " + list);
            currentSession.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session currentSession = sessionFactory.getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            System.out.println("Таблица очищена" + "  " +  "users");
            currentSession.getTransaction().commit();
        }
    }
}
