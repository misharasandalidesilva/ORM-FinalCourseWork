package lk.ijse.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO e) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(e.getUserId(),e.getPassword(),e.getRole(),e.getUsername()));
    }

    @Override
    public UserDTO getdata(String uName) {
        User user = userDAO.getdata(uName);
        if (user == null) {
            return null;

        } else {
            return new UserDTO(user.getU_id(), user.getPassword(), user.getRole(), user.getU_name());
        }
    }

    @Override
    public ObservableList<UserDTO> getallUser() {
        List<UserDTO> userDTO = new ArrayList<>();
        List<User> users = userDAO.getAll();
        for (User u : users) {
            userDTO.add(new UserDTO(u.getU_id(), u.getU_name(), u.getRole()));
        }
        return FXCollections.observableArrayList(userDTO);
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }
}
