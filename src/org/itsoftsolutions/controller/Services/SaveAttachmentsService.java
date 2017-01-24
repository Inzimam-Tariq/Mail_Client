/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javax.mail.internet.MimeBodyPart;
import org.itsoftsolutions.model.EmailMessageBean;

/**
 *
 * @author Inzimam
 */
public class SaveAttachmentsService extends Service<Void> {

    private String DOWNLOAD_LOCATION = System.getProperty("user.home") + "/Downloads/Mail Client Test/";
    private EmailMessageBean msgToDownload;
    private ProgressBar progressBar;
    private Label label;

    public SaveAttachmentsService(ProgressBar progressBar, Label label) {        
        this.progressBar = progressBar;
        this.label = label;
        this.setOnRunning(e -> {
            showProgressBar(true);
        });
        this.setOnSucceeded(e -> {
            showProgressBar(false);
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                for (MimeBodyPart mbp : msgToDownload.getAttachmentList()) {
                    mbp.saveFile(DOWNLOAD_LOCATION + mbp.getFileName());
                }

                return null;
            }
        };
    }
// always call before starting

    public void setMessageToDownload(EmailMessageBean messageToDownload) {
        this.msgToDownload = messageToDownload;
    }

    private void showProgressBar(boolean show) {
        progressBar.setVisible(show);
        label.setVisible(show);
    }

}
