/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javax.mail.Folder;
import javax.mail.MessagingException;

/**
 *
 * @author Inzimam
 */
public class FolderUpdaterService extends Service<Void> {

    private List<Folder> folderList;
    private VBox progPanel;
    private Label downProgLbl;

    public FolderUpdaterService(List<Folder> folderList,VBox progPanel,Label downProgLbl) {
        this.folderList = folderList;
        this.progPanel = progPanel;
        this.downProgLbl = downProgLbl;
        
        this.setOnRunning(e -> {
            downProgLbl.setText("Loading Emails");
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
                for (;;) {
                    try {
                        Thread.sleep(10000);
                        if (FetchFolderService.noServiceActive()) {
                            System.out.println("Checking for new Emails!!!!");
                            for (Folder folder : folderList) {
                                if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                    folder.getMessageCount();
//                                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<>(folder.getName(), folder.getFullName());
//                                    FetchFolderService fetchFolderService = new FetchFolderService();
//                                    fetchFolderService.addMessageListenerToFolder(folder, emailFolderBean);
                                }
                            }
                        }
                    } catch (InterruptedException | MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void showProgressBar(boolean show) {
        progPanel.setVisible(show);
    }
}
