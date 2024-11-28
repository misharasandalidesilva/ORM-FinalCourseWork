package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBo;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;

import java.util.ArrayList;

public interface PaymentBO extends SuperBo {
    String generateNewID();

    ArrayList<PaymentDTO> getPaymentDetails(int parseInt);

    boolean savePayment(PaymentDTO paymentDTO, RegistrationDTO registrationDTO);

}
