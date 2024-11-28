package lk.ijse.dao;

import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T entity) throws SQLException, ClassNotFoundException;;

    boolean delete(String id);

    boolean update(T entity);

    List<T> getAll();

    Student search(String id);
}
