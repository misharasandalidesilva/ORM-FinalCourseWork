package lk.ijse.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public boolean saveprogram(ProgramDTO e) throws SQLException, ClassNotFoundException {
        return programDAO.save(new Program(e.getP_id(), e.getP_name(), e.getDuration(), e.getFee()));
    }

    @Override
    public boolean deleteProgram(String pid) {
        return programDAO.delete(pid);
    }

    @Override
    public boolean updateprogram(ProgramDTO e) {
        return programDAO.update(new Program(e.getP_id(), e.getP_name(), e.getDuration(), e.getFee()));
    }

    @Override
    public ObservableList<ProgramDTO> getAllProgram() {
        List<ProgramDTO> programDtos = new ArrayList<>();
        List<Program> program = programDAO.getAll();
        for (Program p : program) {
            programDtos.add(new ProgramDTO(p.getP_id(), p.getDuration(), p.getFee(), p.getP_name()));
        }
        System.out.println("BOimpl awa");
        return FXCollections.observableArrayList(programDtos);
    }

    @Override
    public List<String> getProgramNames() {
        return programDAO.getProgramNames();
    }

    @Override
    public Program searchByName(String name) {
        return programDAO.searchByName(name);
    }

}
