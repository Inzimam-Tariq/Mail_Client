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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Inzimam
 */
public class LoginController implements Initializable {

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXButton signup;

    @FXML
    private WebView webLoader;

    @FXML
    private Button webBtn;

    @FXML
    void makeLogin(ActionEvent event) throws IOException {
        String username = user.getText().trim();
        String pass = password.getText().trim();
        if (username.equals("Inzimam") && pass.equals("inzi")) {
            System.out.println("Welcome Inzi to JavaFx");
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene scene = new Scene(root);
            Stage MainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            MainStage.setScene(scene);
            MainStage.setTitle("IMail Main Window");

            MainStage.show();
            JOptionPane.showMessageDialog(null, pass);
        } else {
            System.out.println("Wrong Input");
            JOptionPane.showMessageDialog(null, "Oh no");
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
    }

}
