/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.itsoftsolutions.controller.Services.FolderUpdaterService;
import org.itsoftsolutions.controller.Services.RegisterMailAccountService;
import org.itsoftsolutions.controller.table.BoldableRowFactory;
import org.itsoftsolutions.model.EmailMessageBean;
import org.itsoftsolutions.model.folder.EmailFolderBean;
import org.itsoftsolutions.view.ViewFactory;

/**
 *
 * @author Inzimam
 */
public class MainWindowController extends AbstractController implements Initializable {

    public MainWindowController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    ViewFactory viewFactory = ViewFactory.defaultFactory;

    @FXML
    private TreeView<String> mailFolderTreeView;

    private MenuItem showDetails = new MenuItem("Show Details");

    @FXML
    private Button loadData;

    @FXML
    private TableView<EmailMessageBean> emailTableView;

    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;

    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;

    @FXML
    private TableColumn<EmailMessageBean, Integer> subjectCol;
    @FXML
    private WebView messageRenderer;

    @FXML
    void loadEmailData(ActionEvent event) {

    }

    @FXML
    void changeReadAction() {
        EmailMessageBean message = getModelAccess().getSelectedMessage();
        if (message != null) {
            boolean value = message.isRead();
            message.setRead(!value);
            EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
            if (selectedFolder != null) {
                if (value) {
                    selectedFolder.incrementUnreadMsgs(1);
                } else {
                    selectedFolder.decrementUnreadMsgs(1);
                }
            }
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void initialize(URL location, ResourceBundle resources) {

        FolderUpdaterService folderUpdaterService
                = new FolderUpdaterService(getModelAccess().getFoldersList());
        folderUpdaterService.start();

        emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        senderCol.setCellValueFactory(new PropertyValueFactory<>("sender"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        sizeCol.setComparator(new Comparator<String>() {

            Integer int1, int2;

            @Override
            public int compare(String str1, String str2) {
                int1 = EmailMessageBean.formattedSize.get(str1);
                int2 = EmailMessageBean.formattedSize.get(str2);
                return int1.compareTo(int2);
            }
        });

        EmailFolderBean<String> rootItem = new EmailFolderBean("");
        mailFolderTreeView.setRoot(rootItem);
        mailFolderTreeView.setShowRoot(false);

        RegisterMailAccountService registerMailAccountService1
                = new RegisterMailAccountService("inzi.javamailtest@gmail.com",
                        "Chand-977",
                        rootItem,
                        getModelAccess());
        registerMailAccountService1.start();

        RegisterMailAccountService registerMailAccountService2
                = new RegisterMailAccountService("inzi.programmer@gmail.com",
                        "769inzimam-9771",
                        rootItem,
                        getModelAccess());
//        registerMailAccountService2.start();
        emailTableView.setContextMenu(new ContextMenu(showDetails));

        mailFolderTreeView.setOnMouseClicked(e -> {
            EmailFolderBean<String> item
                    = (EmailFolderBean) mailFolderTreeView.getSelectionModel().getSelectedItem();
            if (item != null && !item.isTopElement()) {
                emailTableView.setItems(item.getData());
                getModelAccess().setSelectedFolder(item);
                // clear selected message
                getModelAccess().setSelectedMessage(null);
            }
        });
        emailTableView.setOnMouseClicked(e -> {
            EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
            if (message != null) {
                getModelAccess().setSelectedMessage(message);
                messageRenderer.getEngine().loadContent(message.getMsgContent());
            }
        });
        showDetails.setOnAction(e -> {
            Stage stage = new Stage();
            Scene scene = viewFactory.getEmailDetailScene();
            stage.setScene(scene);
            stage.show();
        });
    }

}
