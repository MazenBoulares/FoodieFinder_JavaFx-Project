package com.example.javefx_project_v3.Controllers.Authentification;

import com.example.javefx_project_v3.Entitys.TypeUSer;
import com.example.javefx_project_v3.Entitys.Users;
import com.example.javefx_project_v3.MainApplication;
import com.example.javefx_project_v3.Services.ServiceUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SigninController implements Initializable {
    @FXML
    public TextField nom_fid;
    @FXML
    public TextField prenom_fid;
    @FXML
    public TextField email_sign;
    @FXML
    public TextField password_sign;
    @FXML
    public Button login_btn;
    @FXML
    public Text error_lbl;
    public Button signup_btn;
    ServiceUsers us = new ServiceUsers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(actionEvent -> {
            // Open the new page (admin or client)
            Parent root;
            Stage stage1 = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/demojava/Loginn.fxml"));
                Scene scene = new Scene(root);
                stage1.setMinWidth(600);
                stage1.setMinHeight(400);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Close the current login page
            Stage stage = (Stage) email_sign.getScene().getWindow();
            stage.close();
        });
    }



     public void goToLogin() throws IOException {
         MainApplication.showLoginPage();

     }





    public void signup(){
        System.out.println(nom_fid.getText());
        System.out.println(prenom_fid.getText());
        System.out.println(password_sign.getText());
        System.out.println(email_sign.getText());
        Users u = new Users();
        u.setNom(nom_fid.getText());
        u.setEmail(email_sign.getText());
        u.setPrenom(prenom_fid.getText());
        u.setMotDePasse(password_sign.getText());
        u.setTypeUtilisateur(TypeUSer.Client);
        try {
            us.ajouter(u);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Parent root;
        Stage stage1 = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/javefx_project_v3/Loginn.fxml"));
            Scene scene = new Scene(root);
//            stage1.setMinWidth(1100);
//            stage1.setMinHeight(600);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage2 = (Stage) email_sign.getScene().getWindow();
        stage2.close();
    }
}
