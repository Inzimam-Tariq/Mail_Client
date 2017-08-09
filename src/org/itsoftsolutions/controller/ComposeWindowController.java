/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.Services.SendEmailService1;
import org.itsoftsolutions.controller.helpingClasses.FetchDBData;
import org.itsoftsolutions.controller.helpingClasses.ShowDialog;
import org.itsoftsolutions.controller.helpingClasses.WorkingWithExcel;
import org.itsoftsolutions.model.EmailConstants;

/**
 * FXML Controller class
 *
 * @author Inzimam
 */
public class ComposeWindowController extends AbstractController implements Initializable {

    private List<File> attachments = new ArrayList<>();
    private ObservableList<String> data = FXCollections.observableArrayList();
    private FetchDBData fetchDBData;
    private ShowDialog dialog;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private MenuItem configMenu;

    @FXML
    private MenuItem helpMenu;

    @FXML
    private JFXTextField senderMailField;

    @FXML
    private JFXTextField ccMailField;

    @FXML
    private JFXTextField recepientField;

    @FXML
    private JFXTextField subjectField;

    @FXML
    private TextField pathField;

    @FXML
    private HTMLEditor msgArea;

    public ComposeWindowController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    void closeMenuAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void configMenuAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("configureAccounts.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ComposeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void helpMenuAction(ActionEvent event) {
        dialog.show(Alert.AlertType.INFORMATION,
                    "Developer Info",
                    "Developer Information",
                    "In case of any query you can contact me at inzi769@gmail.com");
    }

    @FXML
    void AttachRecepientAction() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String file = selectedFile.getAbsolutePath();
            System.out.println(file);
            WorkingWithExcel wwe = new WorkingWithExcel();
            String recepients = wwe.ReadFromExcel(file);
            recepientField.setText(recepients);
        }
        dialog.show(Alert.AlertType.INFORMATION,
                    "Info",
                    "Software Limitation",
                    "This Feature is not Available in this Version");
    }

    @FXML
    void AttachFileAction() {
        showFileChooser();
    }

    @FXML
    void AttachImgAction() {
        showFileChooser();
    }

    @FXML
    void sendBtnAction() {
        String from = senderMailField.getText().toLowerCase().trim();
        String toMail = recepientField.getText().toLowerCase().trim();
        String ccMail = ccMailField.getText().toLowerCase().trim();
        String subject = subjectField.getText().trim();
        String msgContent = msgArea.getHtmlText();
        if (toMail.isEmpty()) {
            dialog.show(Alert.AlertType.INFORMATION,
                    "Info",
                    "Please Specify Recepient",
                    "No recepient specified");
        } else {
            SendEmailService1 sendEmailService = new SendEmailService1(from,
                    subject,
                    toMail,
                    ccMail,
                    msgContent,
                    attachments);
            sendEmailService.restart();
            sendEmailService.setOnSucceeded((WorkerStateEvent e) -> {
                if (sendEmailService.getValue() == EmailConstants.MESSAGE_SEND_OK) {                    
                    dialog.show(Alert.AlertType.INFORMATION,
                    "Info",
                    "Send Mail Result",
                    "Mail Sent Successfully!");
                } else {                    
                    dialog.show(Alert.AlertType.ERROR,
                    "Error Message",
                    "Send Mail Result",
                    "Error Sending Mail!");
                }
            });
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JOptionPane.showMessageDialog(null, "row count = " + 0000);
        dialog = new ShowDialog();
        fetchDBData = new FetchDBData();
        int rowCount = fetchDBData.getRowCount(fetchDBData.getMailAccTableName());
        if (rowCount == 1) {
            ResultSet rs = fetchDBData.getResultSet(fetchDBData.getMailAccTableName());
            try {
                while (rs.next()) {
                    senderMailField.setText(rs.getString("mail_id"));
                }
            } catch (SQLException ex) {                
                dialog.show(Alert.AlertType.ERROR,
                    "Error",
                    "Exception Getting Data",
                    "Invalid Table OR Column Reference");
            }
        }

    }

    private void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachments.add(selectedFile);
            pathField.setText(pathField.getText() + selectedFile.getName() + "; ");
            pathField.setVisible(true);
        }
    }

}
