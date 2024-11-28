package lk.ijse.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.bo.SuperBo;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.tdm.RegistrationTM;

import java.util.List;

public interface RegistrationBO extends SuperBo {
    String genarateNewId();

    boolean saveRegistration(RegistrationDTO registrationDTO, PaymentDTO paymentDTO);


    List<Object[]> loadAllRegistrationDetails();
}
