package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Registration;
import org.hibernate.Session;

import java.util.List;

public interface RegistretionDAO extends CrudDAO<Registration> {
    String generateNewId();

    boolean saveRegistration(Registration registration, Session session);

    boolean updateAmount(Registration registration, Session session);

    List<Object[]> loadAllRegistrationDetails();
}
