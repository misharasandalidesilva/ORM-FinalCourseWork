package lk.ijse.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.RegistretionDAO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import lk.ijse.tdm.RegistrationTM;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {
    RegistretionDAO registretionDAO = (RegistretionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REGISTRATION);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public String genarateNewId() {
        return registretionDAO.generateNewId();
    }

    @Override
    public boolean saveRegistration(RegistrationDTO registrationDTO, PaymentDTO paymentDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            boolean isregister = registretionDAO.saveRegistration(
                    new Registration(
                            registrationDTO.getRegId(),
                            registrationDTO.getStudent(),
                            registrationDTO.getRegistrationDate(),
                            registrationDTO.getProgram(),
                            registrationDTO.getPaidAmount()
                    ),
                    session
            );
            if (!isregister) {
                transaction.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save the registration").show();
                return false;
            }

            boolean issavepayment = paymentDAO.SavePayment(
                    new Payment(
                            paymentDTO.getPaymentId(),
                            paymentDTO.getPaymentDate(),
                            paymentDTO.getAmount(),
                            paymentDTO.getPaymentMethod(),
                            new Registration(paymentDTO.getRegistration().getRegId())
                    ),
                    session
            );
            if (issavepayment) {
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save the payment").show();
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail transaction").show();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Object[]> loadAllRegistrationDetails() {
        return registretionDAO.loadAllRegistrationDetails();
    }
}


