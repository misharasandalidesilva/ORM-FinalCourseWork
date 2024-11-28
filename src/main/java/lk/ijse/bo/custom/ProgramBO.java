package lk.ijse.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.bo.SuperBo;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProgramBO extends SuperBo {

    boolean saveprogram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException;

    boolean deleteProgram(String pid);

    boolean updateprogram(ProgramDTO programDTO);

    ObservableList<ProgramDTO> getAllProgram();

    List<String> getProgramNames();

    Program searchByName(String name);
}
