/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import java.io.IOException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;
import org.itsoftsolutions.model.EmailMessageBean;

/**
 *
 * @author Inzimam
 */
public class SaveAttachmentsService extends Service<Void> {

    private String DOWNLOAD_LOCATION = System.getProperty("user.home") + "/Downloads/Mail Client Test/";
    private EmailMessageBean msgToDownload;
    private VBox progPanel;

    public SaveAttachmentsService( VBox progPanel) {
        this.progPanel = progPanel;
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
            protected Void call()  {
                for (MimeBodyPart mbp : msgToDownload.getAttachmentList()) {
                    try {
                        updateProgress(msgToDownload.getAttachmentList().indexOf(mbp),
                                msgToDownload.getAttachmentList().size());
                        mbp.saveFile(DOWNLOAD_LOCATION + mbp.getFileName());
                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(null, "unable to get attachments");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Bad Download Path");
                    }
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
        progPanel.setVisible(show);
    }

}
