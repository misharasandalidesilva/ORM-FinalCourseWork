package lk.ijse.dao.custom.impl;

import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.custom.RegistretionDAO;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;
import lk.ijse.tdm.RegistrationTM;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistretionDAOImpl implements RegistretionDAO {

    @Override
    public boolean save(Registration entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Registration entity) {
        return false;
    }

    @Override
    public List<Registration> getAll() {
        return null;
    }

    @Override
    public Student search(String id) {
        return null;
    }

    @Override
    public String generateNewId() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        try {
            Query<Integer> query = session.createQuery("SELECT r.regId FROM Registration r ORDER BY r.regId DESC", Integer.class);
            query.setMaxResults(1);

            Integer lastId = query.uniqueResult();

            if (lastId == null) {
                return String.valueOf(1);
            } else {
                return String.valueOf(lastId + 1);
            }
        } finally {
            session.close();
        }
    }

    @Override
    public boolean saveRegistration(Registration registration, Session session) {
        session.save(registration);

        return true;
    }

    @Override
    public boolean updateAmount(Registration registration, Session session) {
        try {
            // HQL update query to set paidAmount where regId matches
            String hql = "UPDATE Registration r SET r.paidAmount = :paidAmount WHERE r.regId = :regId";
            Query query = session.createQuery(hql);
            query.setParameter("paidAmount", registration.getPaidAmount());
            query.setParameter("regId", registration.getRegId());

            // Execute update and check the number of affected rows
            int affectedRows = query.executeUpdate();
            return affectedRows > 0;  // Returns true if at least one row was updated

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Object[]> loadAllRegistrationDetails() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<Object[]> results = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            String hql = "SELECT r.regId, s.id, s.name, p.p_id, p.p_name,r.paidAmount FROM Registration r JOIN r.student s JOIN r.program p";

            Query<Object[]> query = session.createQuery(hql, Object[].class);

            // Execute the query and get the result list
            results = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception closeException) {
                closeException.printStackTrace();
            }
        }
        return results;
    }
}
