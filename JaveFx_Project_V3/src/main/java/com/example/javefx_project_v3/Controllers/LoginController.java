package com.example.javefx_project_v3.Controllers;

import com.example.javefx_project_v3.Entitys.TypeUSer;
import com.example.javefx_project_v3.Entitys.Users;
import com.example.javefx_project_v3.MainApplication;
import com.example.javefx_project_v3.Services.ServiceUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField email_fid;
    @FXML
    public TextField password_fid;
    @FXML
    public Button login_btn;
    @FXML
    public Text error_lbl;
    public Button signup_btn;
    ServiceUsers us = new ServiceUsers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //login_btn.setOnAction(event -> onLogin());
        signup_btn.setOnAction(event -> {
            // Open the new page (admin or client)
            Parent root;
            Stage stage1 = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/demojava/Signin.fxml"));
                Scene scene = new Scene(root);
                stage1.setMinWidth(600);
                stage1.setMinHeight(400);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Close the current login page
            Stage stage = (Stage) email_fid.getScene().getWindow();
            stage.close();
        });
    }
//    private void onLogin () {
//        Stage stage = (Stage) email_fid.getScene().getWindow();
//        Model.getInstance().evaluateUserCred(email_fid.getText(),password_fid.getText());
//        Model.getInstance().getAdminLoginSuccessFlag();
//            email_fid.setText("");
//            password_fid.setText("");
//            error_lbl.setText("No such Login credentials");
//
//
//    }


    public void goTosignUp() throws IOException{
        MainApplication.showSignUpPage();

    }
    public void loginBtn(){
        System.out.println(email_fid.getText());
        System.out.println(password_fid.getText());
        try {
           Users u =  us.getUserByEmail(email_fid.getText());
            System.out.println(u);
            Parent root;
            Stage stage = new Stage();
            Alert alert;
            if (u == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Login");
                alert.setHeaderText(null);
                alert.setContentText("Password or email invalid please chack them again !!");
                alert.showAndWait();
            }else {
                if(u.getTypeUtilisateur() == TypeUSer.Admin){
                    //redirection vers admin
                    stage.setTitle("Admin");
                    root = FXMLLoader.load(getClass().getResource("/com/example/demojava/hello-view.fxml"));
                    Stage stage2 = (Stage) email_fid.getScene().getWindow();
                    stage2.close();
                }else
                //redirection vers client
                {
                    Stage stage2 = (Stage) email_fid.getScene().getWindow();
                    stage2.close();
                    stage.setTitle("Client");
                    root = FXMLLoader.load(getClass().getResource("/com/example/demojava/hello-view.fxml"));
                }

                Scene scene = new Scene(root);
                stage.setMinWidth(1100);
                stage.setMinHeight(600);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
