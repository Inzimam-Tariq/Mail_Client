/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import org.itsoftsolutions.controller.ModelAccess;
import org.itsoftsolutions.model.EmailAccountBean;
import org.itsoftsolutions.model.folder.EmailFolderBean;

/**
 *
 * @author Inzimam
 */
public class FetchFolderService extends Service<Void> {

    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccountBean;
    private ModelAccess modelAccess;
    private static int NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE = 0;

    public FetchFolderService(EmailAccountBean accountBean, EmailFolderBean<String> foldersRoot,
            ModelAccess modelAccess) {
        this.emailAccountBean = accountBean;
        this.foldersRoot = foldersRoot;
        this.modelAccess = modelAccess;

        this.setOnSucceeded(e -> {
            NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE--;
        });
    }
//    public FetchFolderService(){
//        
//    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE++;
                if (emailAccountBean != null) {
                    Folder folders[] = emailAccountBean.getStore().getDefaultFolder().list();

                    for (Folder folder : folders) {
                        modelAccess.addFolder(folder);
                        EmailFolderBean<String> item = new EmailFolderBean<>(folder.getName(),
                                folder.getFullName());
                        foldersRoot.getChildren().add(item);
                        item.setExpanded(true);
                        addMessageListenerToFolder(folder, item);
                        FetchMessagesService fetchMessagesService
                                = new FetchMessagesService(item, folder);
                        fetchMessagesService.start();
                        System.out.println("Folder: " + folder.getName());
                        Folder subFolders[] = folder.list();
                        for (Folder subFolder : subFolders) {
                            modelAccess.addFolder(subFolder);
                            EmailFolderBean<String> subItem = new EmailFolderBean<>(subFolder.getName(),
                                    subFolder.getFullName());
                            item.getChildren().add(subItem);
                            addMessageListenerToFolder(subFolder, subItem);
                            FetchMessagesService fetchSubFolderMessagesService
                                    = new FetchMessagesService(subItem, subFolder);
                            fetchSubFolderMessagesService.start();
                            System.out.println("subFolder: " + subFolder.getName());
                        }
                    }
                }
                return null;
            }
        };
    }

    public void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item) {
//        System.out.println("Add message listener point 1");
        folder.addMessageCountListener(new MessageCountAdapter() {
            
            public void messageAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {
                    try {
                        System.out.println("Add message listener point 2");
                        Message currentMsg = folder.getMessage(folder.getMessageCount() - i);
                        item.addEmail(0, currentMsg);
                    } catch (MessagingException ex) {
                        Logger.getLogger(FetchFolderService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public static boolean noServiceActive() {
        return NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE == 0;
    }
}
