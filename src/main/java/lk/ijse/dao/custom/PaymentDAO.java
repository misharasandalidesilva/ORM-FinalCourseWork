package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Payment;
import org.hibernate.Session;

import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
    String generateNewId();

    boolean SavePayment(Payment payment, Session session);

    ArrayList<Payment> getDataUsingRegId(int regid);

    boolean savePayment(Payment payment, Session session);
}
