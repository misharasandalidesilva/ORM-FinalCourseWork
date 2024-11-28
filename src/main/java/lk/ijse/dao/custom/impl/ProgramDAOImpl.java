package lk.ijse.dao.custom.impl;

import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {

    @Override
    public boolean save(Program program) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(program);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String pid) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("delete from Program where p_id = ?1");
        query.setParameter(1, pid);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Program program) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(program);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Program> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        String hql = "from Program";

        System.out.println("Dao Awa");
        return session.createQuery(hql, Program.class).list();
    }

    @Override
    public Student search(String id) {
        return null;
    }

    @Override
    public List<String> getProgramNames() {
        List<String> programList = new ArrayList<>();

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            session.beginTransaction();

            // Use a Hibernate HQL query to get distinct program names
            Query<String> query = session.createQuery("SELECT DISTINCT p_name FROM Program p", String.class);
            programList = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return programList;
    }

    @Override
    public Program searchByName(String name) {
        Program program = null;

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            session.beginTransaction();

            // Use an HQL query to search for the program by name
            Query<Program> query = session.createQuery(
                    "FROM Program p WHERE p_name = :name", Program.class);
            query.setParameter("name", name);

            // Retrieve a single result if available
            program = query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return program;
    }
}
