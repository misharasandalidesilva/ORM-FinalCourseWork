package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.UserDTO;

import java.io.IOException;

public class MainDashBoardController {

    @FXML
    private JFXButton btnProgram;

    @FXML
    private JFXButton btnUser;

    @FXML
    private AnchorPane nodePane;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton btnLogOut;

    String role = LoginPageController.liveUserRole;
    UserDTO userDTO = LoginPageController.getActiveUser();


    public void initialize() {
        checkLoggedUser();
//        System.out.println(role);


        PauseTransition delay = new PauseTransition(Duration.seconds(0.0001));
        delay.setOnFinished(event -> loadMainDashboard());
        delay.play();
    }

    private void loadMainDashboard() {
        try {
            // Load mainDashboard_form.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
            AnchorPane mainDashboard = loader.load();

            // Replace the children of rootNode with the loaded content
            rootNode.getChildren().setAll(mainDashboard);
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    void checkLoggedUser() {
        if (userDTO.getRole().equals("admissions coordinator")){
            btnUser.setVisible(false);
            btnProgram.setVisible(false);
        }
    }


    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent loginForm = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        Scene scene = new Scene(loginForm);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("The Culinary Academy");
    }



    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        try {
            FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
            Parent customerRoot = customerLoader.load();
            rootNode.getChildren().clear();
            rootNode.getChildren().add(customerRoot);

            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setTitle("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnProgramsOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/ManageProgram.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/Register_Course.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void btnStudentsOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/ManageStudent.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        try {
            FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/view/ManageUsers.fxml"));
            Parent customerRoot = customerLoader.load();
            rootNode.getChildren().clear();
            rootNode.getChildren().add(customerRoot);

            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setTitle("UserForm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/Payment.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }
}

