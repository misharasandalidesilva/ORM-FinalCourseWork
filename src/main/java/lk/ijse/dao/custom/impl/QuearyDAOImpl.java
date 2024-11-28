package lk.ijse.dao.custom.impl;

import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class QuearyDAOImpl implements QueryDAO {

    @Override
    public List<Object[]> getPaymentDetails(String id) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Object[]> results = new ArrayList<>();

        try {
            String hql = "SELECT r.regId, s.name, p.p_name, p.fee, r.paidAmount FROM Registration r JOIN r.student s JOIN r.program p WHERE s.id = :studentId";


            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("studentId", id);

            // Execute the query
            results = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

}
