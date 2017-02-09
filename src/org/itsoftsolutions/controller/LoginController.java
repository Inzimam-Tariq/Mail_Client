/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.helpingClasses.FetchDBData;
import org.itsoftsolutions.view.ViewFactory;

/**
 *
 * @author Inzimam
 */
public class LoginController implements Initializable {

    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField re_typePass;

    @FXML
    private JFXButton login;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXButton signup;

    FetchDBData dBData;

    @FXML
    private WebView webLoader;

    @FXML
    private Button webBtn;

    @FXML
    void makeLogin(ActionEvent event) throws IOException {
        final String TABLE_NAME = "login_table";
        String username = user.getText().trim();
        String pass = password.getText().trim();
        String re_pass = re_typePass.getText().trim();

        if (dBData.getRowCount(TABLE_NAME) > 0) {
            ResultSet rs = dBData.getResultSet(TABLE_NAME);
            String dbUName = null, dbUPass = null;
            try {
                while (rs.next()) {
                    dbUName = rs.getString("uname");
                    dbUPass = rs.getString("upass");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Table OR Column Reference");
            }
            ViewFactory viewFactory = ViewFactory.defaultFactory;

            if (username.equals(dbUName) && pass.equals(dbUPass)) {
                Scene scene = viewFactory.getMainWindowScene();
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage mainStage = new Stage();
                mainStage.setMaximized(true);
                mainStage.setScene(scene);
                mainStage.setTitle("Mail Client Main Window");

                mainStage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username OR Password!!");
            }
        } else {
            if (username.length() > 0 && pass.length() > 0 && re_pass.length() > 0) {
//                Alert al = new Alert(Alert.AlertType.INFORMATION);
//                    al.setTitle("Simple Alert!");
//                    al.setHeaderText("Alert Heading");
//                    al.setContentText("Message Text showing Message detail ");
//                    al.showAndWait();
//                    Notifications notifications = Notifications.create()
//                            .title("System Alert!")
//                            .text("Detail message text")
//                            .hideAfter(Duration.seconds(5))
//                            .position(Pos.BASELINE_RIGHT)
//                            .onAction((ActionEvent event1) -> {
//                                al.show();
//                });
//                    notifications.show();

                if (pass.equals(re_pass)) {
                    boolean isSignupSuccess = dBData.StoreSignUpInfo("login_table", username, pass);
                    if (isSignupSuccess) {
                        JOptionPane.showMessageDialog(null, "SignUP Successful!!!\n"
                                + "Enter your credentials again to login");
                        signup.toBack();
                        re_typePass.setVisible(false);
                        password.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "SignUp failure contact your software vender");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "your Re-type password does not match!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username and password is essential");
            }
        }
    }

    @FXML
    void loadWebPage(ActionEvent event) {
        WebEngine webEngine = webLoader.getEngine();
        webEngine.load("http://javafx.com");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dBData = new FetchDBData();
        if (dBData.getRowCount("login_table") > 0) {
            signup.toBack();
            re_typePass.setVisible(false);
        }
//        JOptionPane.showMessageDialog(null, dBData.getLastId("login_table", "cust_id"));
    }

}
