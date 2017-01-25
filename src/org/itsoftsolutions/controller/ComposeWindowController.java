/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import org.itsoftsolutions.controller.Services.SendEmailService;
import org.itsoftsolutions.model.EmailConstants;

/**
 * FXML Controller class
 *
 * @author Inzimam
 */
public class ComposeWindowController extends AbstractController implements Initializable {

    private List<File> attachments = new ArrayList<>();

    @FXML
    private ChoiceBox<String> senderChoice;

    @FXML
    private JFXTextField recepientField;

    @FXML
    private JFXTextField subjectField;

    @FXML
    private TextField pathField;

    @FXML
    private HTMLEditor msgArea;

    @FXML
    void selectRecepientsFileAction() {

    }

    @FXML
    void AttachAction() {

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
        String from = senderChoice.getSelectionModel().getSelectedItem();
        String toMail = recepientField.getText().toLowerCase().trim();
//        String ccMail = cc.getText().toLowerCase().trim();
        String subject = subjectField.getText().trim();
        String msgContent = msgArea.getHtmlText();
        if (toMail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No recepient specified");
        } else {
            SendEmailService sendEmailService = new SendEmailService(null,
                    subject,
                    toMail,
                    msgContent,
                    attachments);
            sendEmailService.restart();
            sendEmailService.setOnSucceeded((WorkerStateEvent e) -> {
                if (sendEmailService.getValue() == EmailConstants.MESSAGE_SEND_OK) {
                    JOptionPane.showMessageDialog(null, "Mail Sent Successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error Sending Mail!");
                }
            });
        }
    }

    @FXML
    void sendBtnClicked() {

    }

    public ComposeWindowController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachments.add(selectedFile);
            pathField.setText(pathField.getText() + selectedFile.getName()+"; ");
            pathField.setVisible(true);
        }
    }

}
