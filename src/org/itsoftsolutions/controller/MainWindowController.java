/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.itsoftsolutions.controller.Services.FolderUpdaterService;
import org.itsoftsolutions.controller.Services.MessageRendererService;
import org.itsoftsolutions.controller.Services.RegisterMailAccountService;
import org.itsoftsolutions.controller.Services.SaveAttachmentsService;
import org.itsoftsolutions.controller.table.BoldableRowFactory;
import org.itsoftsolutions.controller.table.formatedSize;
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

//  Manubar related components
    private MenuItem showDetails = new MenuItem("Show Details");
    @FXML
    private MenuItem menuConfig;

//  Main Anchorpane related components    
    @FXML
    private Button loadData;
    //  Download progress related components    
    @FXML
    private VBox downProgPanel;
    @FXML
    private Label downAttchLbl;
    @FXML
    private ProgressBar downAttchProg;
    @FXML
    private JFXButton downAttachBtn;
    @FXML
    private SplitPane folderMailViewSplit, tableAndMsgViewSplit;

//  Folder TreeView related components    
    @FXML
    private TreeView<String> mailFolderTreeView;

//  Email TableView related components    
    @FXML
    private TableView<EmailMessageBean> emailTableView;
    @FXML
    private TableColumn<EmailMessageBean, Integer> subjectCol;
    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;
    @FXML
    private TableColumn<EmailMessageBean, formatedSize> sizeCol;
    @FXML
    private TableColumn<EmailMessageBean, Date> dateCol;

//  WebView related components    
    @FXML
    private WebView messageRenderer;

//  Services and Classes related components    
    private SaveAttachmentsService saveAttachmentsService;
    private MessageRendererService messageRendererService;

//  Action in Main Window
    @FXML
    void menuConfigAction(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = viewFactory.getConfigurationWindowScene();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void composeMailAction(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = viewFactory.getComposeWindowScene();
        stage.setScene(scene);
        stage.show();
    }

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

    @FXML
    void downAttachBtnAction(ActionEvent event) {
        EmailMessageBean msg = emailTableView.getSelectionModel().getSelectedItem();
        if (msg != null && msg.hasAttachment()) {
            saveAttachmentsService.setMessageToDownload(msg);
            saveAttachmentsService.restart();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        downAttchLbl.setVisible(false);
//        downAttchProg.setVisible(false);
//        boolean showSplitView = true;
//        if (showSplitView) {
//            tableAndMsgViewSplit.setDividerPositions(1);
//        }
        saveAttachmentsService = new SaveAttachmentsService(downProgPanel);
        messageRendererService = new MessageRendererService(messageRenderer.getEngine());
        downAttchProg.progressProperty().bind(saveAttachmentsService.progressProperty());

        FolderUpdaterService folderUpdaterService
                = new FolderUpdaterService(getModelAccess().getFoldersList());
        folderUpdaterService.start();

        emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        senderCol.setCellValueFactory(new PropertyValueFactory<>("sender"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // BUG: size column doesn't get it's default comparator overriden
        // so we have to do it manually 
        sizeCol.setComparator(new formatedSize(0));

        EmailFolderBean<String> rootItem = new EmailFolderBean<>("");
        mailFolderTreeView.setRoot(rootItem);
        mailFolderTreeView.setShowRoot(false);

        RegisterMailAccountService registerMailAccountService1
                = new RegisterMailAccountService("inzi.javamailtest@gmail.com",
                        "******",
                        rootItem,
                        getModelAccess());
        registerMailAccountService1.start();

        RegisterMailAccountService registerMailAccountService2
                = new RegisterMailAccountService("inzi.programmer@gmail.com",
                        "*****",
                        rootItem,
                        getModelAccess());
//        registerMailAccountService2.start();
        emailTableView.setContextMenu(new ContextMenu(showDetails));

        mailFolderTreeView.setOnMouseClicked(e -> {
            @SuppressWarnings("unchecked")
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
//            tableAndMsgViewSplit.setDividerPositions(0);
            EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
            if (message != null) {
                getModelAccess().setSelectedMessage(message);
                messageRendererService.setMsgToRender(message);
                messageRendererService.restart();
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
