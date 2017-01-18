/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

    public FetchMessagesService(EmailFolderBean<String> mailFolder, Folder folder) {
        this.mailFolder = mailFolder;
        this.folder = folder;
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
}
