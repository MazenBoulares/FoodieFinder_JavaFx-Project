package com.example.javefx_project_v3.Entitys;
import com.example.javefx_project_v3.Utils.DataSource;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Model {
    private static Model model;
    private Users user;
    private boolean adminLoginSuccessFlag;

    private Model() {

        // admin data section
        this.adminLoginSuccessFlag = false;
        this.user = new Users();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }



    public boolean getAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public Users getUser() {
        return user;
    }

    public void setAdmin(Users admin) {
        this.user = user;
    }
    public void evaluateUserCred(String email ,String password ){

        try {
            Connection conn = DataSource.getInstance().getCon();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * From users where Email = ? ");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in database ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect ! ");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedRole = resultSet.getString("role");
                    System.out.println(retrievedPassword);
                    System.out.println(retrievedRole);
                    if (retrievedPassword.equals(password)) {
                        this.user.setUserID(resultSet.getInt("id_user"));
                        this.user.setEmail(resultSet.getString("email"));
                        this.user.setMotDePasse(resultSet.getString("MotDePasse"));
                    } else {
                        System.out.println("Password does not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect ! ");
                        alert.show();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
