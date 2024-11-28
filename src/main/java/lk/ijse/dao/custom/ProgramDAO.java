package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Program;

import java.util.List;

public interface ProgramDAO extends CrudDAO<Program> {
    List<String> getProgramNames();

    Program searchByName(String name);
}
