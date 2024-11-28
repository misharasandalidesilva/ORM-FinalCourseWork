package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Object[]> getPaymentDetails(String id);
}
