package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;
import lk.ijse.tdm.RegistrationTM;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterCourseController {
    ProgramBO programBO = (ProgramBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.PROGRAM);
    RegistrationBO registrationBo = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.REGISTRATION);
    StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.STUDENT);
    PaymentBO paymentBO = (PaymentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.PAYMENT);

    @FXML
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private JFXComboBox<String> cmbProgramName;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colPaidAmount;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colRegId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblFee;

    @FXML
    private Label lblProgramDuration;

    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblRegistrationId;

    @FXML
    private Label lblStudentName;

    @FXML
    private TableView<RegistrationTM> tblRegistration;

    @FXML
    private TextField txtFirstPayment;

    @FXML
    private TextField txtStudentId;

    public void initialize() {
        lblDate.setText(LocalDate.now().toString());
        setCMBName();
        generateNewID();
        paymentType();
        getAllRegistrations();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("regId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
    }

    void setCMBName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.addAll(programBO.getProgramNames());
        cmbProgramName.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    private void getAllRegistrations(){
        ObservableList<RegistrationTM> getAllRegistraytion = FXCollections.observableArrayList();

        List<Object[]> allReg = registrationBo.loadAllRegistrationDetails();

        for (Object[] row : allReg) {
            int regId = (Integer) row[0];
            String studentId = (String) row[1];
            String studentName = (String) row[2];
            String programId = (String) row[3];
            String programName = (String) row[4];
            double paidAmount = (Double) row[5];

            RegistrationTM details = new RegistrationTM(regId, studentId,studentName,programId,programName,paidAmount);
            getAllRegistraytion.add(details);
        }

        tblRegistration.setItems(getAllRegistraytion);
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        int regId = Integer.parseInt(lblRegistrationId.getText());
        Date regDate = Date.valueOf(lblDate.getText());
        String payMethod = cmbPaymentMethod.getValue();
        double paidAmount = Double.parseDouble(txtFirstPayment.getText());
        String studentId = (txtStudentId.getText());
        String programId = lblProgramId.getText();

        Student student = new Student(studentId);
        Program program = new Program(programId);

        int paymentId = Integer.parseInt(paymentBO.generateNewID());
        RegistrationDTO registrationDTO = new RegistrationDTO(regId, student, program, regDate, paidAmount);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId, registrationDTO, paidAmount, regDate, payMethod);

        boolean isSaved = registrationBo.saveRegistration(registrationDTO, paymentDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful!").show();
            getAllRegistrations();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the registration").show();
        }

}

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtStudentId.getText();
        if(!txtStudentId.getText().isEmpty()){
            Student student = studentBO.searchStudent(id);
            if (student !=null){
                lblStudentName.setText(student.getName());
            }else {
                new Alert(Alert.AlertType.ERROR, "Student Not Found").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please enter a Student ID!").show();
        }
    }

    public void paymentType() {
        ObservableList<String> pType = FXCollections.observableArrayList();
        cmbPaymentMethod.setValue("Cash");
        pType.add("Cash");
        pType.add("Card");
        cmbPaymentMethod.setItems(pType);
    }


    @FXML
    void cmbProgramNameOnAction(ActionEvent event) {
        String name = cmbProgramName.getValue();
        Program program = programBO.searchByName(name);

        if (program != null) {
            lblProgramId.setText(program.getP_id());
            lblFee.setText(String.valueOf(program.getFee()));
            lblProgramDuration.setText(program.getDuration());
            txtFirstPayment.requestFocus();
        }
    }

    @FXML
    void idKeyReleaseAction(KeyEvent event) {

    }

    @FXML
    void paymentOnKeyReleaseOnAction(KeyEvent event) {

    }

    private void generateNewID(){
        String nextRegId = registrationBo.genarateNewId();
        lblRegistrationId.setText(String.valueOf(nextRegId));
    }


}
