package lk.ijse.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Student;

import java.sql.SQLException;
import java.time.LocalDate;

public class ManageStudentController {

    static UserDTO activeUser;
    @FXML
    private TableColumn<?, ?> ColAddress;

    @FXML
    private TableColumn<?, ?> Colreg_Date;

    @FXML
    private TableColumn<?, ?> ColId;

    @FXML
    private TableColumn<?, ?> ColName;

    @FXML
    private TableColumn<?, ?> ColNumber;

    @FXML
    private DatePicker registerDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<StudentDTO> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;


StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.STUDENT);


    public void initialize() {
//        registerDate.setValue(LocalDate.parse(LocalDate.now().toString()));
        activeUser = LoginPageController.getActiveUser();
        setcellvaluefactory();
        getallStudent();
    }

    private void setcellvaluefactory() {
        ColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        ColNumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
        Colreg_Date.setCellValueFactory(new PropertyValueFactory<>("regDate"));


    }

    private void getallStudent() {
        ObservableList<StudentDTO> student = studentBO.getAllStudent();
        tblStudent.setItems(student);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        try {
            boolean isDeleted = studentBO.deleteStudent(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Student deleted!").show();
                getallStudent();
                clearfields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtNumber.getText();
        String regDate = String.valueOf(registerDate.getValue());

        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || tel.isEmpty() || regDate.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }else {

            StudentDTO studentDto = new StudentDTO(id, name, address, tel, regDate);
            boolean result = studentBO.addStudent(studentDto, activeUser);
            if (result) {
                new Alert(Alert.AlertType.CONFIRMATION, "Add Successful").show();
                getallStudent();
                clearfields();
            }
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (!txtId.getText().isEmpty()) {
            Student student = studentBO.searchStudent(id);
            if (student != null) {
                txtId.setText(String.valueOf(student.getId()));
                txtName.setText(student.getName());
                txtAddress.setText(student.getAddress());
                txtNumber.setText(student.getTel());
                registerDate.setValue(LocalDate.parse(student.getRegDate()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Student not found!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtNumber.getText();
        String regDate = String.valueOf(registerDate.getValue());

        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || tel.isEmpty() || regDate.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }else {
            StudentDTO studentDTO = new StudentDTO(id,name,address,tel,regDate);

            try {
                boolean isSaved = studentBO.updateStudent(studentDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "student Updated!").show();
                    getallStudent();
                    clearfields();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    void clearfields(){
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtNumber.setText(null);
        registerDate.setValue(null);
    }

    public void StudenttblClicked(MouseEvent mouseEvent) {
        StudentDTO studentDTO = tblStudent.getSelectionModel().getSelectedItem();
        if (studentDTO != null) {
            txtId.setText(String.valueOf(studentDTO.getId()));
            txtName.setText(studentDTO.getName());
            txtAddress.setText(studentDTO.getAddress());
            txtNumber.setText(studentDTO.getTel());
//            registerDate.setValue(LocalDate.parse(studentDTO.getRegDate()));
        }
    }
}
