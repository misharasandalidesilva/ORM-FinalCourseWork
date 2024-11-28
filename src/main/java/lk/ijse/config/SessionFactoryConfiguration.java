package lk.ijse.config;

import lk.ijse.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {

    private static SessionFactoryConfiguration sessionFactoryConfiguration;

    private SessionFactory sessionFactory;

    // Private constructor to initialize the SessionFactory with all entity classes
    private SessionFactoryConfiguration() {
        Configuration configuration = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Program.class)
                .addAnnotatedClass(Registration.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Payment.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    // Singleton pattern to ensure only one instance of SessionFactoryConfiguration
    public static SessionFactoryConfiguration getInstance() {
        if (sessionFactoryConfiguration == null) {
            sessionFactoryConfiguration = new SessionFactoryConfiguration();
        }
        return sessionFactoryConfiguration;
    }

    // Method to open a new Hibernate session
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
