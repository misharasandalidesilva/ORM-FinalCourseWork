package lk.ijse.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.tdm.ProgramTM;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageProgramController {

    public TableColumn ColId;
    public TableView tblStudent;
    public TableColumn ColName;
    public TableColumn ColAddress;
    public TableColumn ColNumber;
    @FXML
    private TableColumn<?, ?> ColDuration;

    @FXML
    private TableColumn<?, ?> ColFee;

    @FXML
    private TableColumn<?, ?> ColPId;

    @FXML
    private TableColumn<?, ?> ColPName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ProgramDTO> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPId;

    @FXML
    private TextField txtPName;

    @FXML
    private TextField txtfee;

    ProgramBO programBO = (ProgramBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.PROGRAM);

    public void initialize() {
        setcellvaluefactory();
        getallProgram();
    }

    private void setcellvaluefactory() {
        ColPId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        ColDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        ColFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        ColPName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

    }

    private void getallProgram() {
        ObservableList<ProgramDTO> programe = programBO.getAllProgram();
        tblProgram.setItems(programe);
    }

    void clearfields(){
        txtPId.setText(null);
        txtDuration.setText(null);
        txtfee.setText(null);
        txtPName.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String pid = txtPId.getText();

            try {
                boolean isDeleted = programBO.deleteProgram(pid);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User deleted!").show();
                    getallProgram();
                    clearfields();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String pid = txtPId.getText();
        String pName = txtPName.getText();
        String duration = txtDuration.getText();
        Double fee = Double.valueOf(txtfee.getText());

        if (pid.isEmpty() || pName.isEmpty() || duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }else {
            if (isValied()) {
                ProgramDTO programDTO = new ProgramDTO(pid,pName,duration,fee);
                try {
                    boolean isSaved = programBO.saveprogram(programDTO);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                        getallProgram();
                        clearfields();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isValied() {
        boolean nameValid = Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtPName);
        boolean duration = Regex.setTextColor(lk.ijse.Util.TextField.DURATION, txtDuration);
        boolean fee = Regex.setTextColor(lk.ijse.Util.TextField.PRICEDOT, txtfee);

        return nameValid && duration && fee;
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String pid = txtPId.getText();
        String pName = txtPName.getText();
        String duration = txtDuration.getText();
        Double fee = Double.valueOf(txtfee.getText());

        if (pid.isEmpty() || pName.isEmpty() || duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }else {
            ProgramDTO programDTO = new ProgramDTO(pid,pName,duration,fee);

            try {
                boolean isSaved = programBO.updateprogram(programDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Program Updated!").show();
                    getallProgram();
                    clearfields();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void programtblClicked(MouseEvent mouseEvent) {
        ProgramDTO userDto = tblProgram.getSelectionModel().getSelectedItem();
        if (userDto != null) {
            txtPId.setText(String.valueOf(userDto.getP_id()));
            txtDuration.setText(userDto.getDuration());
            txtfee.setText(String.valueOf(userDto.getFee()));
            txtPName.setText(userDto.getP_name());
        }
    }






    public void programNameOnKeyReleaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtPName);
    }
}
