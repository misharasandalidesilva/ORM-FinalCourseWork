package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BoFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.event.MouseEvent;

public class ManageUsersController {

    @FXML
    private TableColumn<?, ?> ColRole;

    @FXML
    private TableColumn<?, ?> ColUId;

    @FXML
    private TableColumn<?, ?> ColUName;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TextField txtUid;

    @FXML
    private TableView<UserDTO> tblUsers;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.USER);

    public void initialize() {
        setValuesCmb();
        setcellvaluefactory();
        getallUser();
    }

    private void setcellvaluefactory() {
        ColUId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ColUName.setCellValueFactory(new PropertyValueFactory<>("username"));
        ColRole.setCellValueFactory(new PropertyValueFactory<>("role"));

    }

    private void getallUser() {
        ObservableList<UserDTO> users = userBO.getallUser();
        tblUsers.setItems(users);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtUid.getText();

        try {
            boolean isDeleted = userBO.deleteUser(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Student deleted!").show();
                getallUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = String.valueOf(Long.parseLong(txtUid.getText()));
        String uName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        String encryptpw = null;
        try {
            encryptpw = BCrypt.hashpw(password, BCrypt.gensalt());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while encrypting password").show();
            return;
        }

        UserDTO userDTO = new UserDTO(id,encryptpw,role, uName);
        try {
            boolean isSaved = userBO.saveUser(userDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                initialize();
                clearfields();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    void setValuesCmb() {
        ObservableList<String> role = FXCollections.observableArrayList();
        role.add("admin");
        role.add("admissions coordinator");
        cmbRole.setItems(role);
    }

    void clearfields(){
        txtUid.setText(null);
        txtPassword.setText(null);
        txtUserName.setText(null);
        cmbRole.setValue(null);
    }

    public void UsertblClicked(javafx.scene.input.MouseEvent mouseEvent) {
        UserDTO userDto = tblUsers.getSelectionModel().getSelectedItem();
        if (userDto != null) {
            txtUid.setText(String.valueOf(userDto.getUserId()));
            txtPassword.setText(userDto.getPassword());
            txtUserName.setText(String.valueOf(userDto.getUsername()));
            cmbRole.setValue(userDto.getRole());
        }
    }
}
