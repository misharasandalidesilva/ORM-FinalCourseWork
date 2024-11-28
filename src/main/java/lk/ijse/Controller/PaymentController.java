package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Student;
import lk.ijse.tdm.PaymentTM;
import lk.ijse.tdm.RegPaymentTM;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    double oldpayment = 0;
    double fee = 0;

    StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.STUDENT);
    PaymentBO paymentBO = (PaymentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.PAYMENT);
    RegistrationBO registrationBo = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.REGISTRATION);



    @FXML
    private TableView<PaymentTM> Paymentdetailstbl;

    @FXML
    private TableView<RegPaymentTM> ProgramDetailstbl;

    @FXML
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPaidAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPMethod;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colRegId;

    @FXML
    private TableColumn<?, ?> colfee;

    @FXML
    private TableColumn<?, ?> colpId;

    @FXML
    private TableColumn<?, ?> colregId2;

    @FXML
    private TableColumn<?, ?> colstName;

    @FXML
    private Label lblregid;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblProgramName;

    @FXML
    private Label lblProgramfee;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblpaymentID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtStID;

    public void initialize(){
        lbldate.setText(LocalDate.now().toString());
        generateNewId();
        setCellValueFactory();
        showSelectedRow();
        setPaymentType();
    }

    private void setCellValueFactory() {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("regId"));
        colstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("p_name"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));


        colpId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colregId2.setCellValueFactory(new PropertyValueFactory<>("regId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    void btnPayOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        int payId = Integer.parseInt(lblpaymentID.getText());
        double newPayment = Double.parseDouble(txtAmount.getText());
        Date payDate = Date.valueOf(lbldate.getText());
        String payMethod = cmbPaymentMethod.getValue();
        int regId = Integer.parseInt(lblregid.getText());

        double amount = oldpayment + newPayment;

        RegistrationDTO registrationDTO = new RegistrationDTO(regId, amount);
        PaymentDTO paymentDTO = new PaymentDTO(payId, new RegistrationDTO(regId), newPayment, payDate, payMethod);

        boolean isSaved = paymentBO.savePayment(paymentDTO, registrationDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment Successful!").show();
           refreshdetails(amount);
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the payment").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtStID.getText();

        if(!id.isEmpty()){
           List<Object[]> regDetails = studentBO.getPaymentDetails(id);

           if(regDetails != null){
               for (Object[] row : regDetails) {
                   int regId = (Integer) row[0];
                   String studentName = (String) row[1];
                   String programName = (String) row[2];
                   double programFee = (Double) row[3];
                   double paidAmount = (Double) row[4];

                   ProgramDetailstbl.getItems().clear();
                   RegPaymentTM details = new RegPaymentTM(regId,studentName,programName, programFee, paidAmount);
                   ProgramDetailstbl.getItems().add(details);
               }
           }else {
               new Alert (Alert.AlertType.ERROR," Student Not Found").show();
           }


        }else {
           new Alert (Alert.AlertType.ERROR,"Enter Student Id").show();
        }
    }

    void generateNewId(){
        String nextPaymentId = paymentBO.generateNewID();
        lblpaymentID.setText(String.valueOf(nextPaymentId));
    }


    private void showSelectedRow(){
        RegPaymentTM selectRow =ProgramDetailstbl.getSelectionModel().getSelectedItem();
        ProgramDetailstbl.setOnMouseClicked(event -> {
            showSelectedRow();
            });

        if (selectRow != null) {
            double balance = selectRow.getFee() - selectRow.getPaidAmount();
            lblregid.setText(String.valueOf(selectRow.getRegId()));
            lblBalance.setText(String.format("%.2f", balance));

            oldpayment = selectRow.getPaidAmount();
            fee = selectRow.getFee();


            txtAmount.requestFocus();

            loadPaymentDetails();
        }
    }
    void loadPaymentDetails() {
//        ProgramDetailstbl.getItems().clear();
        Paymentdetailstbl.getItems().clear();
        ArrayList<PaymentDTO> pList = paymentBO.getPaymentDetails(Integer.parseInt(lblregid.getText()));

        for (PaymentDTO paymentDTO : pList) {
            Paymentdetailstbl.getItems().add(new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getRegistration().getRegId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate()
            ));
        }
    }

    private void setPaymentType() {
        ObservableList<String> paymentType = FXCollections.observableArrayList();
        cmbPaymentMethod.setValue("Cash");

        paymentType.add("Cash");
        paymentType.add("Card");

        cmbPaymentMethod.setItems(paymentType);
    }

    void refreshdetails(double amount) throws SQLException, IOException, ClassNotFoundException {
        loadPaymentDetails();
        btnSearchOnAction(new ActionEvent());
        generateNewId();

//        lblPaidAmount.setText(String.valueOf(amount));

        double balance = fee - amount;

        lblBalance.setText(String.valueOf(balance));;

        txtAmount.setText(null);
        txtAmount.setStyle(null);
    }



























}

