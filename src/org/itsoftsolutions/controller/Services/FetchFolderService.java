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
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.swing.JOptionPane;
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
    private VBox progPanel;
    private Label downProgLbl;
    private ProgressBar pb;

    public FetchFolderService(EmailAccountBean accountBean, EmailFolderBean<String> foldersRoot,
            ModelAccess modelAccess, VBox progPanel, Label downProgLbl, ProgressBar pb) {
        this.emailAccountBean = accountBean;
        this.foldersRoot = foldersRoot;
        this.modelAccess = modelAccess;
        this.progPanel = progPanel;
        this.pb = pb;
        this.downProgLbl = downProgLbl;

        this.setOnRunning(e -> {
            downProgLbl.setText("Loading Emails Folder");
            showProgressBar(true);
        });
        this.setOnSucceeded(e -> {
            NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE--;
            showProgressBar(false);
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
                                = new FetchMessagesService(item, folder, progPanel,downProgLbl,pb);
                    pb.progressProperty().bind(fetchMessagesService.progressProperty());
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
                                    = new FetchMessagesService(subItem, subFolder, progPanel,downProgLbl,pb);
                    pb.progressProperty().bind(fetchMessagesService.progressProperty());
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
        folder.addMessageCountListener(new MessageCountAdapter() {
            @Override
            public void messagesAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {
                    try {
                        System.out.println("Add message listener point 2");
                        Message currentMsg = folder.getMessage(folder.getMessageCount() - i);
                        item.addEmail(0, currentMsg);
                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(null, "Error Getting new Emails!");
                    }
                }
            }
        });
    }

    public static boolean noServiceActive() {
        return NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE == 0;
    }

    private void showProgressBar(boolean show) {
        progPanel.setVisible(show);
    }
}
