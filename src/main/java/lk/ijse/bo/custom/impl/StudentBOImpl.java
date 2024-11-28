package lk.ijse.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);


    @Override
    public boolean addStudent(StudentDTO se, UserDTO ue) throws SQLException, ClassNotFoundException {
//         studentDAO.save(new Student(se.getId(), se.getName(), se.getAddress(), se.getTel(), se.getRegDate(), new User(ue.getUserId(), ue.getPassword(), ue.getRole(), ue.getUsername())));
        Student student = new Student(se.getId(), se.getName(), se.getAddress(), se.getTel(), se.getRegDate());
        User user = new User(ue.getUserId(), ue.getPassword(), ue.getRole(), ue.getUsername());

        student.setUser(user);
        return studentDAO.save(student);
    }

    @Override
    public boolean deleteStudent(String id) {
        return studentDAO.delete(id);
    }

    @Override
    public ObservableList<StudentDTO> getAllStudent() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> student = studentDAO.getAll();
        for (Student s : student) {
            studentDTOS.add(new StudentDTO(s.getId(),s.getName(),s.getAddress(),s.getTel(),s.getRegDate()));
        }
        System.out.println("BOimpl awa");
        return FXCollections.observableArrayList(studentDTOS);
    }

    @Override
    public Student searchStudent(String id) {
        return studentDAO.search(id);
    }

    @Override
    public List<Object[]> getPaymentDetails(String id) {
        return studentDAO.getPaymentDetails(id);
    }

    @Override
    public boolean updateStudent(StudentDTO s) {
        return studentDAO.update(new Student(s.getId(),s.getName(),s.getAddress(),s.getTel(),s.getRegDate()));
    }

}
