package lk.ijse.dao.custom.impl;

import javafx.event.ActionEvent;
import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public List<Payment> getAll() {
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
            Query<Integer> query = session.createQuery("SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC", Integer.class);
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
    public boolean SavePayment(Payment payment, Session session) {
        session.save(payment);

        return true;
    }

    @Override
    public ArrayList<Payment> getDataUsingRegId(int regid) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        try {
            // HQL query to fetch Payment records with the specified regId in Registration
            Query<Payment> query = session.createQuery("FROM Payment p WHERE p.registration.regId = :regId", Payment.class);
            query.setParameter("regId", regid);  // Set the regId parameter

            // Fetch result list from query
            List<Payment> paymentList = query.list();

            // Convert List to ArrayList (if needed)
            return new ArrayList<>(paymentList);

        } finally {
            session.close();
        }
    }

    @Override
    public boolean savePayment(Payment payment, Session session) {
        session.save(payment);

        return true;
    }

}
