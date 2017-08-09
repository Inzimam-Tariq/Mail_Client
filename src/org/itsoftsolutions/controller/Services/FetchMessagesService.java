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
import javafx.scene.layout.VBox;
import javax.mail.Folder;
import javax.mail.Message;
import org.itsoftsolutions.model.folder.EmailFolderBean;

/**
 *
 * @author Inzimam
 */
public class FetchMessagesService extends Service<Void> {

    private EmailFolderBean<String> mailFolder;
    private Folder folder;
    private VBox progPanel;
    private Label downProgLbl;
    private ProgressBar pb;

    public FetchMessagesService(EmailFolderBean<String> mailFolder, Folder folder
    , VBox progPanel, Label downProgLbl, ProgressBar pb) {
        this.mailFolder = mailFolder;
        this.folder = folder;
        this.progPanel = progPanel;
        this.pb = pb;
        this.downProgLbl = downProgLbl;
        
        this.setOnRunning(e -> {
            downProgLbl.setText("Loading Emails Data");
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
                if (folder.getType() != Folder.HOLDS_FOLDERS) {
                    folder.open(Folder.READ_WRITE);
                }
                int folderSize = folder.getMessageCount();
                for (int i = folderSize; i > 0; i--) {
                    Message currentMsg = folder.getMessage(i);
                    mailFolder.addEmail(-1, currentMsg);
                }
                return null;
            }
        };
    }
    
    private void showProgressBar(boolean show) {
        progPanel.setVisible(show);
    }
}
