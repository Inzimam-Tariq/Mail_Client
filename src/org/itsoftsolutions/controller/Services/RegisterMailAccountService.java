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
import org.itsoftsolutions.controller.ModelAccess;
import org.itsoftsolutions.model.EmailAccountBean;
import org.itsoftsolutions.model.EmailConstants;
import org.itsoftsolutions.model.folder.EmailFolderBean;

/**
 *
 * @author Inzimam
 */
public class RegisterMailAccountService extends Service<Integer> {

    private String mailAddress;
    private String password;
    private EmailFolderBean<String> folderRoot;
    private ModelAccess modelAccess;
    private VBox progPanel;
    private Label downProgLbl;
    private ProgressBar pb;

    public RegisterMailAccountService(String mail, String pass,
            EmailFolderBean<String> foldRoot, ModelAccess modelAccess
            , VBox progPanel, Label downProgLbl, ProgressBar pb) {
        this.mailAddress = mail;
        this.password = pass;
        this.folderRoot = foldRoot;
        this.modelAccess = modelAccess;
        this.progPanel = progPanel;
        this.downProgLbl = downProgLbl;
        this.pb = pb;

        this.setOnRunning(e -> {
            downProgLbl.setText("Getting Started!");
            showProgressBar(true);
        });
        this.setOnSucceeded(e -> {
            showProgressBar(false);
        });
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {

            @Override
            protected Integer call() throws Exception {
                EmailAccountBean emailAccount = new EmailAccountBean(mailAddress, password);
                if (emailAccount.getLoginState() == EmailConstants.LOGIN_STATE_SUCCEEDED) {
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<>(mailAddress);
                    folderRoot.getChildren().add(emailFolderBean);
                    FetchFolderService fetchFolderService = new FetchFolderService(emailAccount,
                            emailFolderBean, modelAccess,
                    progPanel,downProgLbl,pb);
                    pb.progressProperty().bind(fetchFolderService.progressProperty());
                    fetchFolderService.start();
                }
                return emailAccount.getLoginState();
            }
        };
    }

    private void showProgressBar(boolean show) {
        progPanel.setVisible(show);
//        pb.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }

}
