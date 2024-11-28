package lk.ijse.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.bo.SuperBo;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;

public interface UserBO extends SuperBo {
    boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    UserDTO getdata(String uName);

    ObservableList<UserDTO> getallUser();

    boolean deleteUser(String id);
}
