package lk.ijse.dao.custom.impl;

import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);


    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("delete from Student where id = ?1");
        query.setParameter(1, id);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }


    @Override
    public boolean update(Student student) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Student> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        String hql = "from Student" + "";

        System.out.println("Dao Awa");
        return session.createQuery(hql, Student.class).list();
    }

    @Override
    public Student search(String id) {
            Session session = SessionFactoryConfiguration.getInstance().getSession();
            Student student = null;

            try {
                Query<Student> query = session.createQuery(
                        "FROM Student s WHERE s.id = :id",
                        Student.class
                );
                query.setParameter("id", id);
                student = query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }

            return student;
        }


    @Override
    public List<Object[]> getPaymentDetails(String id) {
        return queryDAO.getPaymentDetails(id);
    }
}
